package com.gwtent.client.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface HTMLWidget {

  /**
   * The DOM ID of this widget put to
   */
  String value() default "";
  
  /**
   * The css will apply to this widget
   */
  String css() default "";
}
