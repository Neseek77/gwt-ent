package com.gwtent.client.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HTMLTemplate {
  
  /**
   * The URL of HTML File
   */
  String value() default "";
  
  /**
   * The content of HTML File
   */
  String html() default "";
  
  
  /**
   * Dose this HTML will compile to source?
   * if HTML is set by URL, you can setup this option
   * to identify if you want compile the html to source code 
   */
  boolean compileToSource() default true;
  
  boolean autoAddWidget() default true;
  
}
