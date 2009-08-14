package com.gwtent.client.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used for Class, to pointer out what types relation to this class need reflection informations
 * 
 * To got more control of Field or Method, please use @(Not Finished yet)
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Reflectable {

	
	/**
	 * True if you want generate annotation information
	 */
	public boolean annotation() default true;
	
  /**
   * If relationTypes is true, 
   * All the types of field, return types of Methods, parameter types
   * of Methods will generate reflection information
   * 
   * @return
   */
  public boolean relationTypes() default false;
  
  
  /**
   * If this is true, All super classes to TObject will generate reflection information
   * default false
   */
  public boolean superClasses() default false;
  
  
  /**
   * if this is true, for different targets this annotation apply to:
   * 
   * if annotate to a class, this is mean All subClasses will generate reflection information
   * if annotate to a interface, then all implement classes will generate reflection information,
   * if annotate to an annotation, then all types annotated by this annotation will generate reflection information
   * 
   * default false
   */
  public boolean assignableClasses() default false;
  
}
