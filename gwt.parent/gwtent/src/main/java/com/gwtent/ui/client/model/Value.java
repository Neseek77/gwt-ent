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

import com.gwtent.ui.client.validate.Validate;

/**
 * every type have their own edit type
 * we have a registry record relation between type and edit type
 * registry can override that other can control the edit type 
 * 
 * @author James Luo
 * 2007-12-25 下午08:30:11
 *
 */
public interface Value {
	public boolean getReadOnly();
	public void setReadOnly(boolean readOnly);
	
	public String getAsString();
	
	public Validate getValidate();
	
	public Object getValue();
	public void setValue(Object value);
	
	public String getTypeName();
	public void setTypeName(String typeName);

}
