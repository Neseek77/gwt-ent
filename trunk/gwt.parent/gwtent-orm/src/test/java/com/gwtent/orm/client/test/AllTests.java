/**
 * FileName: AllTests.java
 * Date:			04/09/2008 3:47:02 PM
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.orm.client.test;

import junit.framework.Test;

import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.gwtent.client.orm.test");
    //$JUnit-BEGIN$
    suite.addTestSuite(GearsTestCase.class);
    //suite.addTestSuite(ReflectionTestCase.class);
    //$JUnit-END$
    return suite;
  }

}
