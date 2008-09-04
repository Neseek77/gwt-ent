/**
 * FileName: TestCase.java
 * Date:			04/09/2008 3:03:01 PM
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.client.orm.test;

import com.google.gwt.junit.client.GWTTestCase;

public class TestCase extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.gwtent.Application";
  }
  
  public void testStuff() {
    assertTrue(2 + 2 == 4);
  }

}
