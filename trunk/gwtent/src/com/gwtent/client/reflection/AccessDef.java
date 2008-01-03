package com.gwtent.client.reflection;

/**
 * Access info for Class, Field and Method
 * 
 * @author James Luo 2007-12-24 下午05:21:39
 * 
 */
public interface AccessDef {
	public boolean isFinal();

	public boolean isPrivate();

	public boolean isProtected();

	public boolean isPublic();

	public boolean isStatic();

//	public boolean isTransient();
//
//	public boolean isVolatile();

}
