package com.gwtent.client.reflection;

import com.gwtent.client.reflection.impl.ConstructorImpl;

public interface AbstractMethod extends HasAnnotations, HasMetaData  {

	public Parameter findParameter(String name);

	public ClassType getEnclosingType();

	public String getName();

	public Parameter[] getParameters();

	public String getReadableDeclaration();

	public Type[] getThrows();

	public ConstructorImpl isConstructor();

	public boolean isVarArgs();

	public void setVarArgs();

}