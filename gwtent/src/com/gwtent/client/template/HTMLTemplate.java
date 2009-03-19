package com.gwtent.client.template;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indentify a class as a HTMLTemplate.
 * This annotation can be a meta annotation of other annotations
 * please see HTMLTemplateTest annotation for more information
 * 
 * Know issues:
 * If you want using inner class, Just support Static inner class, this 
 * is Java language spec.
 * Can't using ID Selectors for CSS, HTMLTemplatePanel will change the id 
 * automatically after attach widget to HTML. using css function in HTML
 * page instead for now. Must change id, otherwise, if you have more then two
 * instance of HTMLTempaltePanel with same HTML page, the id will duplicate
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HTMLTemplate {
  
  /**
   * The URL of HTML File
   * The final URL of HTML file equal basePath + value
   */
  String value() default "";

  /**
   * The basePath of HTMLFile or ReportFile
   * The final URL of HTML(Report) file equal basePath + value(reportFileName)
   * If this is ""(default), use the root of ClassPath
   * It's a good idea to override this property for your project
   */
  String basePath() default "";
  
  /**
   * The content of HTML File
   */
  String html() default "";
  
  /**
   * The custom variable of this template. 
   * Using "=" to indent key and value
   * "name=GWTENT"
   * name is the key
   * GWTENT is the value
   * 
   * In HTML, you can write like this:
   * Hello, you name is ${name}
   * 
   * Please see TemplateTestCase.TestHTMLTemplateVariables
   * for more information
   * @return
   */
  String[] variables() default {};
  
  /**
   * Set up the report file name.
   * The report file contains HTMLFileName, Widget, CSS, EventHandler, Variables
   * And A demo page 
   * By default this feature is disabled.
   */
  String reportFileName() default "";
  
  /**
   * Dose this HTML will compile to source?
   * if HTML is set by URL, you can setup this option
   * to identify if you want compile the HTML to source code
   * 
   *  Not implement yet
   */
  boolean compileToSource() default true;
  
  boolean autoAddWidget() default true;
  
}
