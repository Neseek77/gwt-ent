package com.gwtent.reflection.client;

import java.lang.annotation.Annotation;

/**
 * Try to simulate the most of functions in java.lang.Class
 * 
 * @author James Luo
 * 
 */
public class ClassHelper<T> implements AnnotatedElement{
	
	private final ClassType classType;
	
	private ClassHelper(ClassType classType){
		this.classType = classType;
	}
	
	public static <T> ClassHelper<T> AsClass(Class<T> clazz){
		ClassType classType = TypeOracle.Instance.getClassType(clazz);
		
		return new ClassHelper<T>(classType);
	}
	
	/**
   * Returns the <code>Class</code> representing the superclass of the entity
   * (class, interface, primitive type or void) represented by this
   * <code>Class</code>.  If this <code>Class</code> represents either the
   * <code>Object</code> class, an interface, a primitive type, or void, then
   * null is returned.  If this object represents an array class then the
   * <code>Class</code> object representing the <code>Object</code> class is
   * returned.
   *
   * @return the superclass of the class represented by this object.
   */
	public Class<? super T> getSuperclass(){
		return (Class<? super T>) classType.getSuperclass().getDeclaringClass();
	}
	
	/**
   * Determines the interfaces implemented by the class or interface
   * represented by this object.
   *
   * <p> If this object represents a class, the return value is an array
   * containing objects representing all interfaces implemented by the
   * class. The order of the interface objects in the array corresponds to
   * the order of the interface names in the <code>implements</code> clause
   * of the declaration of the class represented by this object. For 
   * example, given the declaration:
   * <blockquote><pre>
   * class Shimmer implements FloorWax, DessertTopping { ... }
   * </pre></blockquote>
   * suppose the value of <code>s</code> is an instance of 
   * <code>Shimmer</code>; the value of the expression:
   * <blockquote><pre>
   * s.getClass().getInterfaces()[0]
   * </pre></blockquote>
   * is the <code>Class</code> object that represents interface 
   * <code>FloorWax</code>; and the value of:
   * <blockquote><pre>
   * s.getClass().getInterfaces()[1]
   * </pre></blockquote>
   * is the <code>Class</code> object that represents interface 
   * <code>DessertTopping</code>.
   *
   * <p> If this object represents an interface, the array contains objects
   * representing all interfaces extended by the interface. The order of the
   * interface objects in the array corresponds to the order of the interface
   * names in the <code>extends</code> clause of the declaration of the
   * interface represented by this object.
   *
   * <p> If this object represents a class or interface that implements no
   * interfaces, the method returns an array of length 0.
   *
   * <p> If this object represents a primitive type or void, the method
   * returns an array of length 0.
   *
   * @return an array of interfaces implemented by this class.
   */
	public Class<?>[] getInterfaces(){
		ClassType[] types = classType.getImplementedInterfaces();
		Class<?>[] result = new Class<?>[types.length];
		for (int i = 0; i < types.length; i++){
			result[i] = types[i].getDeclaringClass();
		}
		return result;
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		return classType.getAnnotation(annotationClass);
	}

	public Annotation[] getAnnotations() {
		return classType.getAnnotations();
	}

	public Annotation[] getDeclaredAnnotations() {
		return classType.getDeclaredAnnotations();
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return classType.isAnnotationPresent(annotationClass);
	}
}
