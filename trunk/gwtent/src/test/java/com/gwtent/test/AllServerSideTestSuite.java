/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


/**
 * FileName: AllTests.java
 * Date:			04/09/2008 3:47:02 PM
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.test;

import javax.validation.spi.ValidationProvider;

import com.gwtent.client.test.validate.tck.util.TestUtil;
import com.gwtent.client.test.validate.tck.util.TestUtil.ValidationProviderHandler;
import com.gwtent.test.validate.bootstrap.ValidationTest;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class AllServerSideTestSuite extends TestCase{

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.gwtent.client");
    //$JUnit-BEGIN$
    TestUtil.setValidationProviderHandler(new ValidationProviderHandler(){

			public ValidationProvider<?> instantiateValidationProviderUnderTest() {
				
				String validatorProviderClassName = "org.hibernate.validator.HibernateValidator";//System.getProperty( VALIDATION_PROVIDER_TEST_CLASS );
				if ( validatorProviderClassName == null ) {
					throw new RuntimeException(
							"The test harness must specify the class name of the validation provider under test. Set system property org.hibernate.validator.HibernateValidator"
					);
				}

				Class<?> providerClass;
				try {
					@SuppressWarnings("unchecked")
					Class<?> tmpClazz = ( Class<?> ) TestUtil.class.getClassLoader().loadClass( validatorProviderClassName );
					providerClass = tmpClazz;
				}
				catch ( ClassNotFoundException e ) {
					throw new RuntimeException( "Unable to load " + validatorProviderClassName );
				}

				try {
					Object validationProviderUnderTest = providerClass.newInstance();
					return (ValidationProvider<?>) validationProviderUnderTest;
				}
				catch ( Exception e ) {
					throw new RuntimeException( "Unable to instantiate " + validatorProviderClassName );
				}
			}});
    
    suite.addTestSuite(com.gwtent.test.validate.constraints.application.ValidationRequirementTest.class);
    
    suite.addTestSuite(ValidationTest.class);
    suite.addTestSuite(com.gwtent.test.validate.constraints.ClassValidatorWithTypeVariableTest.class);
    suite.addTestSuite(com.gwtent.test.validate.constraints.ConstraintValidatorContextTest.class);
    suite.addTestSuite(com.gwtent.test.validate.constraints.ValidatorResolutionTest.class);
    
    //$JUnit-END$
    return suite;
  }

}
