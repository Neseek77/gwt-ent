// $Id: ConstraintDefinitionsTest.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.constraintdefinition;

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;
import static org.testng.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import javax.validation.metadata.ConstraintDescriptor;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.SpecAssertions;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;

/**
 * @author Hardy Ferentschik
 */

public class ConstraintDefinitionsTest extends AbstractTest {

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "2.1.1", id = "a"),
			@SpecAssertion(section = "2.2", id = "a")
	})
	public void testConstraintWithCustomAttributes() {

		Validator validator = TestUtil.getValidatorUnderTest();
		Set<ConstraintDescriptor<?>> descriptors = validator.getConstraintsForClass( Person.class )
				.getConstraintsForProperty( "lastName" )
				.getConstraintDescriptors();

		assertEquals( descriptors.size(), 2, "There should be two constraints on the lastName property." );
		for ( ConstraintDescriptor<?> descriptor : descriptors ) {
			assertEquals(
					descriptor.getAnnotation().annotationType().getName(),
					AlwaysValid.class.getName(),
					"Wrong annotation type."
			);
		}

		Set<ConstraintViolation<Person>> constraintViolations = validator.validate( new Person( "John", "Doe" ) );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
	}

	@Test
	@SpecAssertion(section = "2.1.1", id = "f")
	public void testDefaultGroupAssumedWhenNoGroupsSpecified() {

		Validator validator = TestUtil.getValidatorUnderTest();
		ConstraintDescriptor<?> descriptor = validator.getConstraintsForClass( Person.class )
				.getConstraintsForProperty( "firstName" )
				.getConstraintDescriptors()
				.iterator()
				.next();

		Set<Class<?>> groups = descriptor.getGroups();
		assertEquals( groups.size(), 1, "The group set should only contain one entry." );
		assertEquals( groups.iterator().next(), Default.class, "The Default group should be returned." );
	}
}
