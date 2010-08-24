package com.gwtent.client.test.reflection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.reflection.client.ClassType;

/**
 * 
 * @author James Luo
 *
 * 24/08/2010 9:56:13 AM
 */
public class ClassTypeGenTestCase extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.gwtent.client.test.reflection.Reflection";
  }
  
  public static class ClassA{
  	private String str;

		public void setStr(String str) {
			this.str = str;
		}

		public String getStr() {
			return str;
		}
  }
  
  /**
   * At least package visible
   *
   */
  interface ClassTypeOfA extends ClassType<ClassA>{
  	
  }
  
  public void testGen(){
  	ClassTypeOfA type = GWT.create(ClassTypeOfA.class);
  	assert type != null;
  	
  	assert type.getMethods().length == 2;
  }

}
