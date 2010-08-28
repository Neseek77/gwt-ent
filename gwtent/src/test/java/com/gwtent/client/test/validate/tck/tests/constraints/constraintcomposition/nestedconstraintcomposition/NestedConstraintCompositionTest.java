// $Id: NestedConstraintCompositionTest.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.constraintcomposition.nestedconstraintcomposition;

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintTypes;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintViolationMessages;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;

/**
 * Tests for error creation for nested composed constraints with different variations of @REeportAsSingleViolation.
 *
 * @author Hardy Ferentschik
 */

public class NestedConstraintCompositionTest extends AbstractTest {

	@Test
	@SpecAssertion(section = "2.3", id = "g")
	public void testCompositeConstraint1WithNestedConstraintSingleViolation() {
		Validator validator = TestUtil.getValidatorUnderTest();
		DummyEntity1 dummy = new DummyEntity1( "" );
		Set<ConstraintViolation<DummyEntity1>> constraintViolations = validator.validate( dummy );
		assertCorrectNumberOfViolations( constraintViolations, 2 );
		assertCorrectConstraintTypes(
				constraintViolations, Pattern.class, NestedConstraintSingleViolation.class
		);
		assertCorrectConstraintViolationMessages(
				constraintViolations, "Pattern must match abc", "NestedConstraintSingleViolation failed."
		);
	}

	@Test
	@SpecAssertion(section = "2.3", id = "g")
	public void testCompositeConstraint2WithNestedConstraintSingleViolation() {
		Validator validator = TestUtil.getValidatorUnderTest();
		DummyEntity2 dummy = new DummyEntity2( "" );
		Set<ConstraintViolation<DummyEntity2>> constraintViolations = validator.validate( dummy );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
		assertCorrectConstraintTypes( constraintViolations, CompositeConstraint2.class );
		assertCorrectConstraintViolationMessages( constraintViolations, "CompositeConstraint2 failed." );
	}

	@Test
	@SpecAssertion(section = "2.3", id = "g")
	public void testCompositeConstraint3WithNestedConstraint() {
		Validator validator = TestUtil.getValidatorUnderTest();
		DummyEntity3 dummy = new DummyEntity3( "" );
		Set<ConstraintViolation<DummyEntity3>> constraintViolations = validator.validate( dummy );
		assertCorrectNumberOfViolations( constraintViolations, 3 );
		assertCorrectConstraintTypes( constraintViolations, Pattern.class, Pattern.class, Size.class );
		assertCorrectConstraintViolationMessages(
				constraintViolations, "Pattern must match abc", "Pattern must match ...", "Size must be 3"
		);
	}

	@Test
	@SpecAssertion(section = "2.3", id = "g")
	public void testCompositeConstraint4WithNestedConstraintSingleViolation() {
		Validator validator = TestUtil.getValidatorUnderTest();
		DummyEntity4 dummy = new DummyEntity4( "" );
		Set<ConstraintViolation<DummyEntity4>> constraintViolations = validator.validate( dummy );
		assertCorrectNumberOfViolations( constraintViolations, 1 );
		assertCorrectConstraintTypes( constraintViolations, CompositeConstraint4.class );
		assertCorrectConstraintViolationMessages( constraintViolations, "CompositeConstraint4 failed." );
	}

	class DummyEntity1 {
		@CompositeConstraint1
		String string;

		DummyEntity1(String s) {
			this.string = s;
		}
	}

	class DummyEntity2 {
		@CompositeConstraint2
		String string;

		DummyEntity2(String s) {
			this.string = s;
		}
	}

	class DummyEntity3 {
		@CompositeConstraint3
		String string;

		DummyEntity3(String s) {
			this.string = s;
		}
	}

	class DummyEntity4 {
		@CompositeConstraint4
		String string;

		DummyEntity4(String s) {
			this.string = s;
		}
	}
}
