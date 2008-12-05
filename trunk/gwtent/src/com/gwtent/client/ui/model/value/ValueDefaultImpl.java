/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.client.ui.model.value;

import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.Validate;

/**
 * this class improve fllowing functions:
 * 1, validate and validate chain
 * 2, use Getter and Setter to obtain value
 * 
 * @author James Luo
 * 2007-12-27 下午07:48:07
 *
 */
public class ValueDefaultImpl implements Value, AdvValue {
	
	public static class ValueDefaultImplCreator implements ValueCreator{

		public AdvValue createValue() {
			return new ValueDefaultImpl();
		}
	}
	
	private String typeName;
	
	private Object originalValue;
	private Getter valueGetter;
	private Setter valueSetter;
	
	private Validate validate = null;
	private boolean readOnly = true;
	
	public ValueDefaultImpl(){
		
	}
	
	public ValueDefaultImpl(Object originalValue, boolean readOnly, Validate validate,
			Getter valueGetter, Setter valueSetter){
		this.originalValue = originalValue;
		
		this.readOnly = readOnly;
		this.validate = validate;
		this.valueGetter = valueGetter;
		this.valueSetter = valueSetter;
	}

	
	public String getAsString() {
		Object value = getValue();
		if (value == null){
			return null;
		}
		
		return value.toString();
	}

	public boolean getReadOnly() {
		return readOnly;
	}
	
	public void setReadOnly(boolean readOnly){
		this.readOnly = readOnly;
	}

	public Validate getValidate() {
		return this.validate;
	}

	public Object getValue() {
		if (valueGetter != null)
			return valueGetter.getValue();
		else
			return null;
	}

	public void setValue(Object value) {
		if (valueSetter != null)
			valueSetter.setValue(value);
	}
	
	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.model.value.AdvValue#getOriginalValue()
	 */
	public Object getOriginalValue(){
		return originalValue;
	}

	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.model.value.AdvValue#setOriginalValue(java.lang.Object)
	 */
	public void setOriginalValue(Object originalValue) {
		this.originalValue = originalValue;
	}

	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.model.value.AdvValue#getValueGetter()
	 */
	public Getter getValueGetter() {
		return valueGetter;
	}

	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.model.value.AdvValue#setValueGetter(com.coceler.gwt.client.ui.model.value.Getter)
	 */
	public void setValueGetter(Getter valueGetter) {
		this.valueGetter = valueGetter;
	}

	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.model.value.AdvValue#getValueSetter()
	 */
	public Setter getValueSetter() {
		return valueSetter;
	}

	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.model.value.AdvValue#setValueSetter(com.coceler.gwt.client.ui.model.value.Setter)
	 */
	public void setValueSetter(Setter valueSetter) {
		this.valueSetter = valueSetter;
	}

	/* (non-Javadoc)
	 * @see com.coceler.gwt.client.ui.model.value.AdvValue#setValidate(com.coceler.gwt.client.ui.validate.Validate)
	 */
	public void setValidate(Validate validate) {
		this.validate = validate;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

}
