// $Id: SequenceResolutionTest.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.groups.groupsequence;

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.GroupDefinitionException;
import javax.validation.GroupSequence;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.SpecAssertions;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;

/**
 * @author Hardy Ferentschik
 */
public class SequenceResolutionTest extends AbstractTest {

	@Test(expectedExceptions = GroupDefinitionException.class)
	@SpecAssertions({
			@SpecAssertion(section = "3.4.2", id = "e"),
			@SpecAssertion(section = "3.4.2", id = "f"),
			@SpecAssertion(section = "3.4.2", id = "i"),
			@SpecAssertion(section = "8.4", id = "a")
	})
	public void testInvalidDefinitionOfDefaultSequenceInEntity() {
		Validator validator = TestUtil.getValidatorUnderTest();
		TestEntity entity = new TestEntity();
		validator.validate( entity, Complete.class );
	}

	@Test
	@SpecAssertions({
			@SpecAssertion(section = "3.4.2", id = "c")
	})
	public void testGroupSequenceContainerOtherGroupSequences() {
		Validator validator = TestUtil.getValidatorUnderTest();
		TestEntity entity = new TestEntity();
		try {
			validator.validate( entity, InvalidGroupSequence.class );
		}
		catch ( GroupDefinitionException e ) {
			// success
		}
	}

	@Test
	@SpecAssertion(section = "3.4.2", id = "j")
	public void testOnlyFirstGroupInSequenceGetEvaluated() {
		Validator validator = TestUtil.getValidatorUnderTest();
		Car car = new Car( "USd-298" );

		// the constraint fails for each group
		Set<ConstraintViolation<Car>> violations = validator.validate( car, First.class );
		assertCorrectNumberOfViolations( violations, 1 );

		violations = validator.validate( car, Second.class );
		assertCorrectNumberOfViolations( violations, 2 );

		// if we validate against the sequence All we only get one violation since group Second won't be executed
		violations = validator.validate( car, All.class );
		assertCorrectNumberOfViolations( violations, 1 );

		// if we validate against the sequence AllReverse we only get two violations since group First won't be executed
		violations = validator.validate( car, AllReverse.class );
		assertCorrectNumberOfViolations( violations, 2 );
	}

	class Car {
		@Pattern(regexp = "[A-Z][A-Z][A-Z]-[0-9][0-9][0-9]", groups = { First.class, Second.class })
		private String licensePlateNumber;

		@NotNull(groups = Second.class)
		private String make;

		Car(String licensePlateNumber) {
			this.licensePlateNumber = licensePlateNumber;
		}

		public String getLicensePlateNumber() {
			return licensePlateNumber;
		}
	}

	interface First {
	}

	interface Second {
	}

	interface Third {
	}

	@GroupSequence({ First.class, Second.class, Third.class })
	interface All {
	}

	@GroupSequence({ Third.class, Second.class, First.class })
	interface AllReverse {
	}

	@GroupSequence({ Second.class, Third.class, First.class })
	interface Mixed {
	}

	@GroupSequence({ First.class, Third.class, Mixed.class })
	interface InvalidGroupSequence {
	}
}
