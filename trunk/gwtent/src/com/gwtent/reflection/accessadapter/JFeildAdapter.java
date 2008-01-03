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
package com.gwtent.reflection.accessadapter;

import com.google.gwt.core.ext.typeinfo.JField;
import com.gwtent.client.reflection.AccessDef;

public class JFeildAdapter implements AccessDef {

	private JField field;
	
	public JFeildAdapter(JField field){
		this.field = field;
	}

	public boolean isFinal() {
		return field.isFinal();
	}

	public boolean isPrivate() {
		return field.isPrivate();
	}

	public boolean isProtected() {
		return field.isProtected();
	}

	public boolean isPublic() {
		return field.isPublic();
	}

	public boolean isStatic() {
		return field.isStatic();
	}
	
}