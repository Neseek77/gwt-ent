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


package com.gwtent.ui.client.model.value;

import com.gwtent.ui.client.model.Value;
import com.gwtent.ui.client.validate.Validate;

/**
 * the advance interface of Value
 * 
 * @author James Luo
 * 2008-1-2 上午09:00:50
 *
 */
public interface AdvValue extends Value{

	public abstract Object getOriginalValue();

	public abstract void setOriginalValue(Object originalValue);

	public abstract Getter getValueGetter();

	public abstract void setValueGetter(Getter valueGetter);

	public abstract Setter getValueSetter();

	public abstract void setValueSetter(Setter valueSetter);

	public abstract void setValidate(Validate validate);

}