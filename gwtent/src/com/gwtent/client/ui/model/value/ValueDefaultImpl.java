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
