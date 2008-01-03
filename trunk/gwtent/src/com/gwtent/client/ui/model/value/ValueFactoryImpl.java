/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.client.ui.model.value;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.ui.ClassTypeHelper;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.ExpressionProcessor;
import com.gwtent.client.ui.validate.ExpressionProcessorList;
import com.gwtent.client.ui.validate.ExpressionProcessorRegister;
import com.gwtent.client.ui.validate.ValidateChain;
import com.gwtent.client.ui.validate.ValidateChainImpl;
import com.gwtent.client.ui.validate.ValidateReflectionImpl;

/**
 * the type of field and return type of method
 * to value type map
 * 
 * use can override this map to controll which value type 
 * can process which type
 * 
 * this implement depand with ValueDefaultImpl
 * 
 * @author James Luo
 * 2007-12-28 下午02:26:33
 *
 */
public class ValueFactoryImpl implements ValueFactory {

	private ValueFactoryImpl(){
		
	}
	
	private static ValueFactory valueFactory = null;
	
	public static ValueFactory getInstance(){
		if (valueFactory == null){
			valueFactory = new ValueFactoryImpl();
		}
		
		return valueFactory;
	}
	
	private Object pojo;
	private ClassType classType;

	public ClassType getClassType() {
		return classType;
	}

	public void setClassType(ClassType classType) {
		this.classType = classType;
	}

	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.transition.ValueFactory#factory(java.lang.String)
	 */
	public Value factory(Object pojo, ClassType classType, String fieldName, String typeName) {
		AdvValue value = null;
		
		this.pojo = pojo;
		this.classType = classType;
		
		value = doCreateValue(typeName);
		
		if (value != null){
			setGetterAndSetter(value, classType, fieldName, typeName);
		}
		
		setValidate(value, classType, fieldName);
		
		return value;
	}
	
	/**
	 * subclass should override this function to create own value
	 * 
	 * @param typeName
	 * @return
	 */
	protected AdvValue doCreateValue(String typeName){
		AdvValue result = null;
		ValueCreator creator = ValueRegister.getInstance().getValueCreator(typeName);
		if (creator != null)
			result = creator.createValue();
		else
			result = new ValueDefaultImpl();
		result.setTypeName(typeName);
		
		return result;
	}
	
	
	protected void setGetterAndSetter(AdvValue value, final ClassType classType, String fieldName, String typeName){
		if (fieldName.startsWith("set")){
			fieldName = fieldName.replaceFirst("set", "");
		}
		
		
		//Character.valueOf(fieldName.charAt(0)).toUpperCase(ch)
		final String setterName = ClassTypeHelper.getSetterName(fieldName);
		final String getterName = ClassTypeHelper.getGetterName(fieldName);
		
		Method getterMethod = classType.findMethod(getterName, new String[]{});
		if (getterMethod != null){
			value.setValueGetter(new Getter(){

				public Object getValue() {
					return classType.invoke(pojo, getterName, new Object[]{});
				}
				
			});
		}
		
		Method setterMethod = classType.findMethod(setterName, new String[]{typeName});
		if (setterMethod != null){
			value.setReadOnly(false);
			value.setValueSetter(new Setter(){

				public void setValue(Object value) {
					classType.invoke(pojo, setterName, new Object[]{value});
					
				}
				
			});
		}
	}
	
	
	protected void setValidate(AdvValue value, 
			final ClassType classType, String fieldName){
		ValidateChain chain = new ValidateChainImpl();
		
		Field field = classType.findField(fieldName);
		String[] validateNames = ClassTypeHelper.getValidateNames(field);

		ExpressionProcessorList processorList = new ExpressionProcessorList();
		for (int i = 0; i < validateNames.length; i++){
			String validateName = validateNames[i];
			if (
					(ClassTypeHelper.findSyncValidateMethod(classType, validateName) != null)
					||
					(ClassTypeHelper.findAsyncValidateMethod(classType, validateName) != null)
				){
				// have validate function
				chain.addValidate(new ValidateReflectionImpl(classType, pojo, validateName));
			}else{
				//process all validate register
				ExpressionProcessor processor = ExpressionProcessorRegister.getInstance().findProcessor(validateName);
				if (processor != null){
					processorList.addProcessor(validateName, processor);
				}
			}
		}
		chain.addValidate(processorList);
		
		value.setValidate(chain);
	}

	
	public Object getPojo() {
		return pojo;
	}

	public void setPojo(Object pojo) {
		this.pojo = pojo;
	}
	
	

}
