// $Id: ValidationProviderTest.java 20225 2010-08-23 14:02:20Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.bootstrap;

import static org.testng.Assert.assertTrue;
import static org.testng.FileAssert.fail;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.ValidationException;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;
import javax.validation.spi.ValidationProvider;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.SpecAssertions;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.common.TCKValidationProvider;
import com.gwtent.client.test.validate.tck.util.TestUtil;

/**
 * @author Hardy Ferentschik
 */

public class ValidationProviderTest extends AbstractTest {

	@Test
	@SpecAssertion(section = "4.4.4.2", id = "b")
	public void testFirstMatchingValidationProviderResolverIsReturned() {
		ValidationProviderResolver resolver = new ValidationProviderResolver() {

			public List<ValidationProvider<?>> getValidationProviders() {
				List<ValidationProvider<?>> list = new ArrayList<ValidationProvider<?>>();
				list.add( TestUtil.getValidationProviderUnderTest() );
				list.add( new com.gwtent.client.test.validate.tck.common.TCKValidationProvider() );
				return list;
			}
		};

		Configuration<?> configuration = Validation
				.byProvider( TCKValidationProvider.class )
				.providerResolver( resolver )
				.configure();

		ValidatorFactory factory = configuration.buildValidatorFactory();
		assertTrue( factory instanceof TCKValidationProvider.DummyValidatorFactory );
	}

	@Test
	@SpecAssertion(section = "4.4.4.2", id = "c")
	public void testByDefaultProviderUsesTheFirstProviderReturnedByValidationProviderResolver() {
		ValidationProviderResolver resolver = new ValidationProviderResolver() {

			public List<ValidationProvider<?>> getValidationProviders() {
				List<ValidationProvider<?>> list = new ArrayList<ValidationProvider<?>>();
				list.add( new TCKValidationProvider() );
				list.add( TestUtil.getValidationProviderUnderTest() );
				return list;
			}
		};

		Configuration<?> configuration = Validation
				.byDefaultProvider()
				.providerResolver( resolver )
				.configure();
		ValidatorFactory factory = configuration.buildValidatorFactory();
		assertTrue( factory instanceof TCKValidationProvider.DummyValidatorFactory );
	}

	@Test
	@SpecAssertion(section = "4.4.4.2", id = "d")
	public void testValidationProviderContainsNoArgConstructor() {
		ValidationProvider<?> validationProviderUnderTest = TestUtil.getValidationProviderUnderTest();
		try {
			Constructor<?> constructor = validationProviderUnderTest.getClass().getConstructor();
			assertTrue( Modifier.isPublic( constructor.getModifiers() ) );
		}
		catch ( Exception e ) {
			fail( "The validation provider must have a public no arg constructor" );
		}
	}

	@Test(expectedExceptions = ValidationException.class)
	@SpecAssertions({
			@SpecAssertion(section = "4.4.5", id = "f"),
			@SpecAssertion(section = "4.4.4.2", id = "e")
	})
	public void testValidationExceptionIsThrownInCaseValidatorFactoryCreationFails() {
		ValidationProviderResolver resolver = new ValidationProviderResolver() {

			public List<ValidationProvider<?>> getValidationProviders() {
				throw new RuntimeException( "ValidationProviderResolver failed!" );
			}
		};

		Validation.byDefaultProvider().providerResolver( resolver ).configure();
	}
}
