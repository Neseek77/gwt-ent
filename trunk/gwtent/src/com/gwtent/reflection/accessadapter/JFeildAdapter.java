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