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

import com.gwtent.client.ui.validate.Validate;

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
