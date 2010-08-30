// $Id: CustomMessageInterpolatorTest.java 20225 2010-08-23 14:02:20Z hardy.ferentschik $
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

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintViolationMessages;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;

import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.MessageInterpolator;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.SpecAssertions;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.reflection.client.annotations.Reflect_Domain;

/**
 * @author Hardy Ferentschik
 */

public class CustomMessageInterpolatorTest extends AbstractTest {

	@Test
	@SpecAssertion(section = "4.3.2", id = "b")
	public void testCustomMessageInterpolatorViaConfiguration() {
		Configuration config = Validation.byDefaultProvider().configure();
		config = config.messageInterpolator( new DummyMessageInterpolator() );
		Validator validator = config.buildValidatorFactory().getValidator();

		assertCustomMessageInterpolatorUsed( validator );
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "4.4.2", id = "a"),
			@SpecAssertion(section = "4.4.2", id = "b"),
			@SpecAssertion(section = "4.3.2", id = "b")
	})
	public void testCustomMessageInterpolatorViaValidatorContext() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		DummyMessageInterpolator dummyMessageInterpolator = new DummyMessageInterpolator();
		Validator validator = factory.usingContext().messageInterpolator( dummyMessageInterpolator ).getValidator();
		assertCustomMessageInterpolatorUsed( validator );
		assertFalse(
				factory.getMessageInterpolator().equals( dummyMessageInterpolator ),
				"getMessageInterpolator() should return the default message interpolator."
		);
	}

	private void assertCustomMessageInterpolatorUsed(Validator validator) {
		Person person = new Person();
		person.setFirstName( "John" );
		person.setPersonalNumber( 1234567890l );

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate( person );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
		assertCorrectConstraintViolationMessages( constraintViolations, "my custom message" );
	}

	private static class DummyMessageInterpolator implements MessageInterpolator {
		public String interpolate(String message, Context context) {
			return "my custom message";
		}

//		public String interpolate(String message, Context context, Locale locale) {
//			throw new UnsupportedOperationException( "No specific locale is possible" );
//		}
	}

	@Reflect_Domain
	public class Person {
		@NotNull
		private String firstName;

		@NotNull
		private String lastName;

		@Digits(integer = 10, fraction = 0)
		private long personalNumber;


		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public long getPersonalNumber() {
			return personalNumber;
		}

		public void setPersonalNumber(long personalNumber) {
			this.personalNumber = personalNumber;
		}
	}
}
