/**
 * FileName: TestAnnotation.java
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.showcase.client.reflection;

public @interface TestAnnotation {
  public enum Color {RED, GREEN, BLUE};
  String name();
  String value()  default "[ThisIsValue]";   
  Color fontColor() default Color.BLUE;
}
