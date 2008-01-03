package com.gwtent.client.reflection;

public class Parameter {
	private final String name;

	private Type type;

	private String typeName;

	private final Method enclosingMethod;


	public Parameter(Method enclosingMethod, String typeName, String name) {
		this.enclosingMethod = enclosingMethod;
		this.typeName = typeName;
		this.name = name;

		enclosingMethod.addParameter(this);
	}
	
	public Parameter(Method enclosingMethod, Type type, String name) {
		this(enclosingMethod, getTypeName(type), name);
		
		this.type = type;
	}
	
	public static String getTypeName(Type type){
		if (type != null)
			return type.getQualifiedSourceName();
		else
			return "";
	}


	public Method getEnclosingMethod() {
		return enclosingMethod;
	}

	public String getName() {
		return name;
	}

	public Type getType() {
		return type;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append(type.getQualifiedSourceName());
		sb.append(" ");
		sb.append(getName());
		return sb.toString();
	}

	// Called when parameter types are found to be parameterized
	void setType(Type type) {
		this.type = type;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
}
