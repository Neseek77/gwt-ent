package com.gwtent.reflection.accessadapter;

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
