// $Id: GraphNavigationTest.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.validation.graphnavigation;

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintViolationMessages;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectPropertyPaths;
import static org.testng.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.SpecAssertions;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;


/**
 * @author Hardy Ferentschik
 */
public class GraphNavigationTest extends AbstractTest {

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.1.3", id = "a"),
			@SpecAssertion(section = "3.1.3", id = "k"),
			@SpecAssertion(section = "3.5.1", id = "a"),
			@SpecAssertion(section = "3.5.1", id = "b")
	})
	public void testGraphNavigationDeterminism() {
		// build the test object graph
		User user = new User( "John", "Doe" );

		Address address1 = new Address( null, "11122", "Stockholm" );
		address1.setInhabitant( user );

		Address address2 = new Address( "Kungsgatan 5", "11122", "Stockholm" );
		address2.setInhabitant( user );

		user.addAddress( address1 );
		user.addAddress( address2 );

		Order order = new Order( 1 );
		order.setShippingAddress( address1 );
		order.setBillingAddress( address2 );
		order.setCustomer( user );

		OrderLine line1 = new OrderLine( order, 42 );
		OrderLine line2 = new OrderLine( order, 101 );
		order.addOrderLine( line1 );
		order.addOrderLine( line2 );

		Validator validator = TestUtil.getValidatorUnderTest();

		Set<ConstraintViolation<Order>> constraintViolations = validator.validate( order );
		assertCorrectNumberOfViolations( constraintViolations, 3 );
		assertCorrectPropertyPaths(
				constraintViolations,
				"shippingAddress.addressline1",
				"customer.addresses[0].addressline1",
				"billingAddress.inhabitant.addresses[0].addressline1"
		);
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.1.3", id = "f"),
			@SpecAssertion(section = "3.5.1", id = "d")
	})
	public void testNoEndlessLoop() {
		User john = new User( "John", null );
		john.knows( john );

		Validator validator = TestUtil.getValidatorUnderTest();

		Set<ConstraintViolation<User>> constraintViolations = validator.validate( john );
		assertEquals( constraintViolations.size(), 1, "Wrong number of constraints" );
		TestUtil.assertConstraintViolation(
				constraintViolations.iterator().next(), User.class, null, "lastName"
		);


		User jane = new User( "Jane", "Doe" );
		jane.knows( john );
		john.knows( jane );

		constraintViolations = validator.validate( john );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
		TestUtil.assertConstraintViolation(
				constraintViolations.iterator().next(), User.class, null, "lastName"
		);

		constraintViolations = validator.validate( jane );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
		TestUtil.assertConstraintViolation(
				constraintViolations.iterator().next(), User.class, null, "knowsUser[0].lastName"
		);

		john.setLastName( "Doe" );
		constraintViolations = validator.validate( john );
		assertCorrectNumberOfViolations( constraintViolations, 0 );
	}

	@Test
	@SpecAssertion(section = "3.1.3", id = "b")
	public void testTypeOfContainedValueIsDeterminedAtRuntime() {
		SingleCage cage = new SingleCage();
		Elephant elephant = new Elephant();
		elephant.setWeight( 500 );
		cage.setContainAnimal( elephant );

		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintViolation<SingleCage>> constraintViolations = validator.validate( cage );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
		assertCorrectConstraintViolationMessages( constraintViolations, "An elephant weighs at least 1000 kg" );
	}

	@Test
	@SpecAssertion(section = "3.1.3", id = "e")
	public void testContainedSet() {
		MultiCage cage = new MultiCage();
		cage.addAnimal( new Zebra( null ) );
		cage.addAnimal( new Zebra( null ) );

		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintViolation<MultiCage>> constraintViolations = validator.validate( cage );
		assertCorrectNumberOfViolations( constraintViolations, 2 );
		assertCorrectConstraintViolationMessages( constraintViolations, "A zebra needs a name", "A zebra needs a name" );
	}

	@Test
	@SpecAssertion(section = "3.1.3", id = "h")
	public void testContainedIterable() {
		GameReserve<Zebra> reserve = new GameReserve<Zebra>();
		Herd<Zebra> zebraHerd = new Herd<Zebra>();
		zebraHerd.addAnimal( new Zebra( null ) );
		zebraHerd.addAnimal( new Zebra( null ) );
		reserve.setHerd( zebraHerd );

		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintViolation<GameReserve<Zebra>>> constraintViolations = validator.validate( reserve );
		assertCorrectNumberOfViolations( constraintViolations, 2 );
		assertCorrectConstraintViolationMessages( constraintViolations, "A zebra needs a name", "A zebra needs a name" );
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.1.3", id = "d"),
			@SpecAssertion(section = "3.1.3", id = "j")
	})
	public void testTypeOfContainedValuesIsDeterminedAtRuntime() {
		Zoo zoo = new Zoo();
		Elephant elephant = new Elephant();
		elephant.setWeight( 500 );
		zoo.addAnimal( elephant );

		Condor condor = new Condor();
		condor.setWingspan( 200 );
		zoo.addAnimal( condor );

		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintViolation<Zoo>> constraintViolations = validator.validate( zoo );
		assertCorrectNumberOfViolations( constraintViolations, 2 );
		assertCorrectConstraintViolationMessages(
				constraintViolations,
				"The wingspan of a condor is at least 250 cm",
				"An elephant weighs at least 1000 kg"
		);

	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.1.3", id = "g"),
			@SpecAssertion(section = "3.1.3", id = "i")
	})
	public void testContainedMap() {
		AnimalCaretaker caretaker = new AnimalCaretaker();
		Elephant elephant = new Elephant();
		elephant.setWeight( 500 );
		caretaker.addAnimal( "Jumbo", elephant );

		Condor condor = new Condor();
		condor.setWingspan( 200 );
		caretaker.addAnimal( "Andes", condor );

		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintViolation<AnimalCaretaker>> constraintViolations = validator.validate( caretaker );
		assertCorrectNumberOfViolations( constraintViolations, 2 );
		assertCorrectPropertyPaths(
				constraintViolations,
				"caresFor[Jumbo].weight",
				"caresFor[Andes].wingspan"
		);
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.4.2", id = "b"),
			@SpecAssertion(section = "3.4.2", id = "d")
	})
	public void testFullGraphValidationBeforeNextGroupInSequence() {
		Parent p = new Parent();
		p.setChild( new Child() );
		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintViolation<Parent>> errors = validator.validate( p, Parent.ProperOrder.class );
		assertCorrectNumberOfViolations( errors, 1 );
		assertCorrectPropertyPaths( errors, "child.name" );
		
		p.getChild().setName( "Emmanuel" );
		errors = validator.validate( p, Parent.ProperOrder.class );
		assertCorrectNumberOfViolations( errors, 1 );
		assertCorrectPropertyPaths( errors, "name" );
	}

	@Test
	@SpecAssertion(section = "3.5.1", id = "c")
	public void testNullReferencesGetIgnored() {
		Parent p = new Parent();
		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintViolation<Parent>> errors = validator.validateProperty( p, "child" );
		assertCorrectNumberOfViolations( errors, 0 );
	}
}
