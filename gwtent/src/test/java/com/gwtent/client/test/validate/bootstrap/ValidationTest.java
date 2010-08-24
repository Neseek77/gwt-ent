// $Id: ValidationTest.java 19640 2010-06-01 12:16:12Z hardy.ferentschik $
/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.gwtent.client.test.validate.bootstrap;

import java.util.Set;
import javax.validation.Configuration;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ConstraintValidatorFactory;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.Test;

import com.gwtent.client.test.validate.VTestCase;
import com.gwtent.client.test.validate.constraints.Interval;
import com.gwtent.client.test.validate.constraints.StartLessThanEndImpl;
import com.gwtent.validate.client.GWTEntValidatorConfiguration;
import com.gwtent.validate.client.GWTEntValidatorProvider;
import com.gwtent.validate.client.constraints.impl.NotNullValidator;
import com.gwtent.validate.client.impl.ConfigurationImpl;
import com.gwtent.validate.client.impl.ConstraintValidatorFactoryImpl;
import com.gwtent.validate.client.impl.ValidatorFactoryImpl;

/**
 * Tests the Bean Validation bootstrapping.
 *
 * @author Hardy Ferentschik
 */
public class ValidationTest extends VTestCase{

	@Test
	public void testBootstrapAsServiceWithBuilder() {
		GWTEntValidatorConfiguration configuration = Validation
				.byProvider( GWTEntValidatorProvider.class )
				.configure();
		assertDefaultBuilderAndFactory( configuration );
		
		StartLessThanEndImpl a = new StartLessThanEndImpl();
		a.isValid(new Interval(), null);
	}

	@Test
	public void testBootstrapAsServiceDefault() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		assertDefaultFactory( factory );
	}

	@Test
	public void testCustomConstraintValidatorFactory() {

		Configuration<?> configuration = Validation.byDefaultProvider().configure();
		assertDefaultBuilderAndFactory( configuration );

		ValidatorFactory factory = configuration.buildValidatorFactory();
		Validator validator = factory.getValidator();

		Customer customer = new Customer();
		customer.setFirstName( "John" );

		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate( customer );
		assertEquals( constraintViolations.size(), 1, "Wrong number of constraints" );
		ConstraintViolation<Customer> constraintViolation = constraintViolations.iterator().next();
		assertEquals( "may not be empty", constraintViolation.getMessage(), "Wrong message" );

		// get a new factory using a custom configuration
		configuration = Validation.byDefaultProvider().configure();
		configuration.constraintValidatorFactory(
				new ConstraintValidatorFactory() {

					public <T extends ConstraintValidator<?, ?>> T getInstance(Class<T> key) {
						if ( key == NotNullValidator.class ) {
							return ( T ) new BadlyBehavedNotNullConstraintValidator();
						}
						return new ConstraintValidatorFactoryImpl().getInstance( key );
					}
				}
		);
		factory = configuration.buildValidatorFactory();
		validator = factory.getValidator();
		constraintViolations = validator.validate( customer );

		assertEquals( constraintViolations.size(), 0, "Wrong number of constraints" );
	}

	/**
	 * HV-328
	 * GWT not support input stream, skip this test case
	 */
//	@Test(expected = IllegalArgumentException.class)
//	public void testNullInputStream() {
//		Configuration<?> configuration = Validation.byDefaultProvider().configure();
//		//configuration.addMapping( null );
//		configuration.buildValidatorFactory();
//	}

	protected void assertDefaultBuilderAndFactory(Configuration configuration) {
		assertNotNull( configuration );
		assertTrue( configuration instanceof ConfigurationImpl );

		ValidatorFactory factory = configuration.buildValidatorFactory();
		assertDefaultFactory( factory );
	}

	protected void assertDefaultFactory(ValidatorFactory factory) {
		assertNotNull( factory );
		assertTrue( factory instanceof ValidatorFactoryImpl );
	}

	class BadlyBehavedNotNullConstraintValidator extends NotNullValidator {
		@Override
		public boolean isValid(Object object, ConstraintValidatorContext constraintValidatorContext) {
			return true;
		}
	}
}
