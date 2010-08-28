package com.gwtent.client.test.validate;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.gwtent.client.test.validate.bootstrap.BootstrappingTestCase;
import com.gwtent.client.test.validate.constraints.ClassValidatorWithTypeVariableTest;
import com.gwtent.client.test.validate.constraints.ConstraintTest;
import com.gwtent.client.test.validate.constraints.ConstraintValidatorContextTest;
import com.gwtent.client.test.validate.constraints.ValidatorResolutionTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * 
 * @author James Luo
 *
 * 12/08/2010 3:04:56 PM
 */
public class ValidationTestSuite extends GWTTestSuite {
	
	public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.gwtent.client.validate");
    //$JUnit-BEGIN$
    suite.addTestSuite(com.gwtent.client.test.validate.bootstrap.ValidationTest.class);
    suite.addTestSuite(BootstrappingTestCase.class);
    
    suite.addTestSuite(ConstraintTest.class);
    suite.addTestSuite(ConstraintValidatorContextTest.class);
    suite.addTestSuite(ClassValidatorWithTypeVariableTest.class);
    suite.addTestSuite(ValidatorResolutionTest.class);
    
    //suite.addTestSuite(ValidateTestCase.class);
    
    
    //$JUnit-END$
    return suite;
  }
}
