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


package com.gwtent.reflection.client;


public interface Field extends AccessDef, HasAnnotations, Member {

	public ClassType getEnclosingType();

	public String getName();

	public Type getType();
	
	public String getTypeName();
	
	/**
	 * Get field value from instance
	 * This function suppose the Getter exists and can be accessed be subclass
	 * 
	 * @param instance
	 * @return the field value of instance
	 */
	public Object getFieldValue(Object instance);
	
	/**
	 * Set field value of instance
	 * This function supposed the Setter exists and can be accessed by subclass
	 * 
	 * @param instance
	 * @param value
	 */
	public void setFieldValue(Object instance, Object value);
	
	public EnumConstant isEnumConstant();

}