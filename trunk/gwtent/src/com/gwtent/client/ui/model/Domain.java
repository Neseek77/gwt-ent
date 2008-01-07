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
package com.gwtent.client.ui.model;

import java.util.Iterator;


public interface Domain {
	//not necessary for ui model
//	public void addFieldInfo(String fieldName, FieldInfo fieldInfo);
//	public FieldInfo addFieldInfo(String fieldName, String caption, boolean require, String desc);
//	public FieldInfo addFieldInfo(String fieldName, String caption, boolean require, String desc, Validate validate);
	
//	public Field getField(String fieldName);
//	public Set getFieldNames();
	
	public void addField(Field field);
	public void addAction(Action action);
	
	/**
	 * iterator of field
	 * @return
	 */
	public Iterator fieldIterator();
	public Iterator actionIterator();
	
	public String getCaption();
	public void setCaption(String caption);
	
	public Object getInstnace();
	public void setInstance(Object instance);
}
