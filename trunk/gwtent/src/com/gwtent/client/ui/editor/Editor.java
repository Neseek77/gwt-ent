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


package com.gwtent.client.ui.editor;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.ValidateCallBack;

public interface Editor {
	public Value getValue();
	public void setValue(Value value);
	
	public ValidateCallBack getValidateCallBack();
	public void setValidateCallBack(ValidateCallBack callBack);
	
	public Widget getWidget();
}
