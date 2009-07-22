package com.gwtent.client.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Bind a field to a DOM element
 * PS: The annotation field must be accessible from Child class,
 * i.e, don't using this annotation to a private field
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface HTMLWidget {

  /**
   * The DOM ID of this widget put to
   * By default it's using the field name, so just leave it if your widget name equals the DOM ID 
   */
  String value() default ""; 
  
  /**
   * The css will apply to this widget
   */
  String css() default "";
}
