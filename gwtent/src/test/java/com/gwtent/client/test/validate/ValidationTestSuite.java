package com.gwtent.client.test.validate;

import junit.framework.Test;

import com.google.gwt.junit.tools.GWTTestSuite;
import com.gwtent.client.test.validate.bootstrap.BootstrappingTestCase;
import com.gwtent.client.test.validate.constraints.ClassValidatorWithTypeVariableTest;
import com.gwtent.client.test.validate.constraints.ConstraintTest;
import com.gwtent.client.test.validate.constraints.ConstraintValidatorContextTest;
import com.gwtent.client.test.validate.constraints.ValidatorResolutionTest;
import com.gwtent.client.test.validate.tck.TCKTestSuite;

/**
 * 
 * @author James Luo
 *
 * 12/08/2010 3:04:56 PM
 */
public class ValidationTestSuite extends GWTTestSuite {
	
	public static Test suite() {
		GWTTestSuite suite = new GWTTestSuite("Test for com.gwtent.client.validate");
    //$JUnit-BEGIN$
    suite.addTest(TCKTestSuite.suite());
    
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
