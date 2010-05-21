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


package com.gwtent.ui.client.model.impl;

import com.gwtent.ui.client.model.Field;
import com.gwtent.ui.client.model.Value;
import com.gwtent.ui.client.validate.Validate;
import com.gwtent.ui.client.validate.ValidateException;

/**
 * interface Field default adapter
 * @author James Luo
 * 2007-12-21 下午02:01:52
 *
 */
public class FieldImpl implements Field {
	
	private String caption;
	private boolean require;
	private String desc;
	private Value value;
	
	public FieldImpl(){
		
	}
	
	public FieldImpl(String caption, boolean require, String desc){
		this.caption = caption;
		this.require = require;
		this.desc = desc;
	}
	
	public FieldImpl(String caption, boolean require, String desc, Value value){
		this(caption, require, desc);
		this.value = value;
	}
	
	public String getCaption() {
		return caption;
	}

	public boolean getRequire() {
		return require;
	}


	public String getDesc() {
		return desc;
	}

	public Value getValue() {
		return value;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setRequire(boolean require) {
		this.require = require;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
