/**
 * FileName: AllTests.java
 * Date:			04/09/2008 3:47:02 PM
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.client.test;

import com.gwtent.client.test.aop.AOPTestCase;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.gwtent.client");
    //$JUnit-BEGIN$
    suite.addTestSuite(StudyTestCase.class);
    suite.addTestSuite(ReflectionTestCase.class);
    suite.addTestSuite(AOPTestCase.class);
    //$JUnit-END$
    return suite;
  }

}
