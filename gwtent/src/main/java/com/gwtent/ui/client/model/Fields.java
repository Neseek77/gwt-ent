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


package com.gwtent.ui.client.model;

import java.util.Iterator;


public interface Fields {
	//not necessary for ui model
//	public void addFieldInfo(String fieldName, FieldInfo fieldInfo);
//	public FieldInfo addFieldInfo(String fieldName, String caption, boolean require, String desc);
//	public FieldInfo addFieldInfo(String fieldName, String caption, boolean require, String desc, Validate validate);
	
//	public Field getField(String fieldName);
//	public Set getFieldNames();
	
	public void addField(Field field);
	
	/**
	 * iterator of field
	 * @return
	 */
	public Iterator<Field> iterator();
	
	public String getCaption();
	public void setCaption(String caption);
}
