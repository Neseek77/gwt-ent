package com.gwtent.reflection.client;

/**
 * 
 * @author James Luo
 * 
 */
public interface AnnotationType<T> extends ClassType<T> {
	public T createAnnotation(Object[] params);
}
