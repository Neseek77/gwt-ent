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


package com.gwtent.client.ui.model.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.gwtent.client.ui.model.Action;
import com.gwtent.client.ui.model.Field;
import com.gwtent.client.ui.model.Domain;
import com.gwtent.client.ui.model.Value;

public class DomainImpl implements Domain {
	
	private Object instance;
	private String caption;
	private List fields;
	private List actions;
	
	public DomainImpl(){
		fields = new ArrayList();
		actions = new ArrayList();
	}

	public Field addFieldInfo(String fieldName, String caption, boolean require, String desc, Value value) {
		Field info = new FieldImpl(caption, require, desc, value);
		addField(info);
		
		return info;
	}

	public Iterator fieldIterator() {
		return fields.iterator();
	}

	public void addField(Field field) {
		fields.add(field);
	}
	
	public String getCaption(){
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public Iterator actionIterator() {
		return actions.iterator();
	}

	public void addAction(Action action) {
		actions.add(action);
	}

	public Object getInstnace() {
		return this.instance;
	}

	public void setInstance(Object instance) {
		this.instance = instance;
	}

}
