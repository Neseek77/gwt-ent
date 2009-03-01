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
   * The final URL of HTML file equal basePath + value
   */
  String value() default "";

  /**
   * The basePath of HTML file
   * The final URL of HTML file equal basePath + value
   * If this is "", use the root of ClassPath
   * Sometimes sub annotation can override this
   */
  String basePath() default "";
  
  /**
   * The content of HTML File
   */
  String html() default "";
  
  /**
   * The custom variable of this template
   * Using "=" to indent key and value
   * "ABC=DEF"
   * ABC is the key
   * DEF is the value
   * @return
   */
  String[] variables() default {};
  
  
  /**
   * Dose this HTML will compile to source?
   * if HTML is set by URL, you can setup this option
   * to identify if you want compile the html to source code 
   */
  boolean compileToSource() default true;
  
  boolean autoAddWidget() default true;
  
}
