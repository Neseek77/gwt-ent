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
