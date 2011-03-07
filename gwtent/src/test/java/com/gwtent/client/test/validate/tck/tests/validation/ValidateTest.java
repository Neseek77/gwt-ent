// $Id: ValidateTest.java 20225 2010-08-23 14:02:20Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.validation;

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertConstraintViolation;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintTypes;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectPropertyPaths;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;
import javax.validation.Validator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.SpecAssertions;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;

/**
 * Tests for the implementation of <code>Validator</code>.
 *
 * @author Hardy Ferentschik
 */

public class ValidateTest extends AbstractTest {

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.1", id = "a"),
			@SpecAssertion(section = "3.1.2", id = "f"),
			@SpecAssertion(section = "5.1", id = "a")
	})
	public void testValidatedPropertyDoesNotFollowJavaBeansConvention() {
		try {
			Boy boy = new Boy();
			TestUtil.getValidatorUnderTest().validate( boy );
			fail();
		}
		catch ( ValidationException e ) {
			// success
		}
	}

	@Test
	@SpecAssertion(section = "5.1", id = "b")
	public void testConstraintDescriptorWithoutExplicitGroup() {
		Validator validator = TestUtil.getValidatorUnderTest();

		BeanDescriptor beanDescriptor = validator.getConstraintsForClass( Order.class );
		PropertyDescriptor propertyDescriptor = beanDescriptor.getConstraintsForProperty( "orderNumber" );
		Set<ConstraintDescriptor<?>> descriptors = propertyDescriptor.getConstraintDescriptors();

		assertEquals( descriptors.size(), 1, "There should be only one constraint descriptor" );
		ConstraintDescriptor<?> descriptor = descriptors.iterator().next();
		Set<Class<?>> groups = descriptor.getGroups();
		assertTrue( groups.size() == 1, "There should be only one group" );
		assertEquals(
				groups.iterator().next(),
				Default.class,
				"The declared constraint does not explicitly define a group, hence Default is expected"
		);
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SpecAssertion(section = "5.1", id = "c")
	public void testNullParameterToGetConstraintsForClass() {
		TestUtil.getValidatorUnderTest().getConstraintsForClass( null );
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SpecAssertion(section = "4.1.1", id = "b")
	public void testValidateWithNullValue() {
		Validator validator = TestUtil.getValidatorUnderTest();
		validator.validate( null );
	}

	@Test(expectedExceptions = IllegalArgumentException.class)
	@SpecAssertion(section = "4.1.1", id = "b")
	@SuppressWarnings("NullArgumentToVariableArgMethod")
	public void testValidateWithNullGroup() {
		Validator validator = TestUtil.getValidatorUnderTest();
		validator.validate( new Boy(), null );
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "4.1.1", id = "a"),
			@SpecAssertion(section = "4.1.1", id = "c")
	})

	public void testMultipleViolationOfTheSameType() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Engine engine = new Engine();
		engine.setSerialNumber( "mail@foobar.com" );
		Set<ConstraintViolation<Engine>> constraintViolations = validator.validate( engine );
		assertCorrectNumberOfViolations( constraintViolations, 2 );

		engine.setSerialNumber( "ABCDEFGH1234" );
		constraintViolations = validator.validate( engine );
		assertCorrectNumberOfViolations( constraintViolations, 1 );

		engine.setSerialNumber( "ABCD-EFGH-1234" );
		constraintViolations = validator.validate( engine );
		assertCorrectNumberOfViolations( constraintViolations, 0 );
	}

	@Test
	@SpecAssertion(section = "4.1.1", id = "c")
	public void testMultipleConstraintViolationOfDifferentTypes() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Address address = new Address();
		address.setStreet( null );
		address.setZipCode( null );
		address.setCity( "Llanfairpwllgwyngyllgogerychwyrndrobwyll-llantysiliogogogoch" ); //town in North Wales

		Set<ConstraintViolation<Address>> constraintViolations = validator.validate( address );
		assertCorrectNumberOfViolations( constraintViolations, 2 );
		assertCorrectConstraintTypes( constraintViolations, Size.class, NotEmpty.class );
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.1", id = "a"),
			@SpecAssertion(section = "4.2", id = "a"),
			@SpecAssertion(section = "4.2", id = "b"),
			@SpecAssertion(section = "4.2", id = "c"),
			@SpecAssertion(section = "4.2", id = "d"),
			@SpecAssertion(section = "4.2", id = "e")
	})
	public void testConstraintViolation() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Engine engine = new Engine();
		engine.setSerialNumber( "ABCDEFGH1234" );
		Set<ConstraintViolation<Engine>> constraintViolations = validator.validate( engine );
		assertCorrectNumberOfViolations( constraintViolations, 1 );

		ConstraintViolation<Engine> violation = constraintViolations.iterator().next();

		assertEquals( violation.getMessage(), "must match ^....-....-....$", "Wrong message" );
		assertEquals( violation.getMessageTemplate(), "must match {regexp}", "Wrong message template" );
		assertEquals( violation.getRootBean(), engine, "Wrong root entity." );
		assertEquals( violation.getInvalidValue(), "ABCDEFGH1234", "Wrong validated value" );
		assertNotNull( violation.getConstraintDescriptor(), "Constraint descriptor should not be null" );
		// cast is required for JDK 5 - at least on Mac OS X
		Annotation ann = ( Annotation ) violation.getConstraintDescriptor().getAnnotation();
		assertEquals( ann.annotationType(), Pattern.class, "Wrong annotation type" );
		assertCorrectPropertyPaths( constraintViolations, "serialNumber" );

		engine.setSerialNumber( "ABCD-EFGH-1234" );
		constraintViolations = validator.validate( engine );
		assertCorrectNumberOfViolations( constraintViolations, 0 );
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "2.4", id = "o")
	})
	public void testGraphValidationWithList() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Actor clint = new ActorListBased( "Clint", "Eastwood" );
		Actor morgan = new ActorListBased( "Morgan", null );
		Actor charlie = new ActorListBased( "Charlie", "Sheen" );

		clint.addPlayedWith( charlie );
		charlie.addPlayedWith( clint );
		charlie.addPlayedWith( morgan );
		morgan.addPlayedWith( charlie );
		morgan.addPlayedWith( clint );
		clint.addPlayedWith( morgan );


		Set<ConstraintViolation<Actor>> constraintViolations = validator.validate( clint );
		assertCorrectNumberOfViolations( constraintViolations, 2 );

		ConstraintViolation constraintViolation = constraintViolations.iterator().next();
		assertEquals( constraintViolation.getMessage(), "Everyone has a last name.", "Wrong message" );
		assertEquals( constraintViolation.getRootBean(), clint, "Wrong root entity" );
		assertEquals( constraintViolation.getInvalidValue(), morgan.getLastName(), "Wrong value" );
		assertCorrectPropertyPaths(
				constraintViolations,
				"playedWith[0].playedWith[1].lastName",
				"playedWith[1].lastName"
		);
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "2.4", id = "o"),
			@SpecAssertion(section = "3.1.3", id = "c")
	})
	public void testGraphValidationWithArray() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Actor clint = new ActorArrayBased( "Clint", "Eastwood" );
		Actor morgan = new ActorArrayBased( "Morgan", null );
		Actor charlie = new ActorArrayBased( "Charlie", "Sheen" );

		clint.addPlayedWith( charlie );
		charlie.addPlayedWith( clint );
		charlie.addPlayedWith( morgan );
		morgan.addPlayedWith( charlie );
		morgan.addPlayedWith( clint );
		clint.addPlayedWith( morgan );

		Set<ConstraintViolation<Actor>> constraintViolations = validator.validate( clint );
		assertCorrectNumberOfViolations( constraintViolations, 2 );
		ConstraintViolation constraintViolation = constraintViolations.iterator().next();
		assertEquals( constraintViolation.getMessage(), "Everyone has a last name.", "Wrong message" );
		assertEquals( constraintViolation.getRootBean(), clint, "Wrong root entity" );
		assertEquals( constraintViolation.getInvalidValue(), morgan.getLastName(), "Wrong value" );
		assertCorrectPropertyPaths(
				constraintViolations,
				"playedWith[0].playedWith[1].lastName",
				"playedWith[1].lastName"
		);
	}

	@Test
	@SpecAssertion(section = "4.1.1", id = "b")
	@SuppressWarnings("NullArgumentToVariableArgMethod")
	public void testPassingNullAsGroup() {
		Validator validator = TestUtil.getValidatorUnderTest();
		Customer customer = new Customer();
		try {
			validator.validate( customer, null );
		}
		catch ( IllegalArgumentException e ) {
			// success
		}
	}

	@Test
	@SpecAssertion(section = "3.5", id = "b")
	public void testOnlyFirstGroupInSequenceGetEvaluated() {
		Validator validator = TestUtil.getValidatorUnderTest();
		Car car = new Car( "USd-298" );

		Set<ConstraintViolation<Car>> violations = validator.validateProperty(
				car, "licensePlateNumber", First.class, Second.class
		);
		assertCorrectNumberOfViolations( violations, 1 );

		car.setLicensePlateNumber( "USD-298" );
		violations = validator.validateProperty(
				car, "licensePlateNumber", First.class, Second.class
		);
		assertCorrectNumberOfViolations( violations, 0 );
	}

	@Test(expectedExceptions = ValidationException.class)
	@SpecAssertion(section = "4.1.1", id = "k")
	public void testUnexpectedExceptionsInValidateGetWrappedInValidationExceptions() {
		Validator validator = TestUtil.getValidatorUnderTest();
		validator.validate( new BadlyBehavedEntity() );
	}

	// TODO - map or remove
	@Test
	public void testValidationIsPolymorphic() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Customer customer = new Customer();
		customer.setFirstName( "Foo" );
		customer.setLastName( "Bar" );

		Order order = new Order();
		customer.addOrder( order );

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate( ( Person ) customer );
		assertCorrectNumberOfViolations( constraintViolations, 1 );

		assertConstraintViolation(
				constraintViolations.iterator().next(),
				Customer.class,
				null,
				"orders[].orderNumber"
		);

		order.setOrderNumber( 123 );

		constraintViolations = validator.validate( ( Person ) customer );
		assertCorrectNumberOfViolations( constraintViolations, 0 );
	}

	// TODO - map or remove
	@Test
	public void testObjectTraversion() {
		Validator validator = TestUtil.getValidatorUnderTest();

		Customer customer = new Customer();
		customer.setFirstName( "John" );
		customer.setLastName( "Doe" );

		for ( int i = 0; i < 100; i++ ) {
			Order order = new Order();
			customer.addOrder( order );
		}

		Set<ConstraintViolation<Customer>> constraintViolations = validator.validate(
				customer, Default.class, First.class, Second.class, Last.class
		);
		assertCorrectNumberOfViolations( constraintViolations, 100 );
	}

	class Car {
		@Pattern(regexp = "[A-Z][A-Z][A-Z]-[0-9][0-9][0-9]", groups = { First.class, Second.class })
		private String licensePlateNumber;

		Car(String licensePlateNumber) {
			this.licensePlateNumber = licensePlateNumber;
		}

		public String getLicensePlateNumber() {
			return licensePlateNumber;
		}

		public void setLicensePlateNumber(String licensePlateNumber) {
			this.licensePlateNumber = licensePlateNumber;
		}
	}

	interface First {
	}

	interface Second {
	}
}
