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
package com.gwtent.gen.reflection.accessadapter;

import com.google.gwt.core.ext.typeinfo.JMethod;
import com.gwtent.client.reflection.AccessDef;

public class JMethodAdapter implements AccessDef{

	private JMethod method;
	
	public JMethodAdapter(JMethod method){
		this.method = method;
	}
	
	public boolean isFinal() {
		return method.isFinal();
	}

	public boolean isPrivate() {
		return method.isPrivate();
	}

	public boolean isProtected() {
		return method.isProtected();
	}

	public boolean isPublic() {
		return method.isPublic();
	}

	public boolean isStatic() {
		return method.isStatic();
	}

}
