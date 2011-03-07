// $Id: GroupInheritanceTest.java 20225 2010-08-23 14:02:20Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.groups.inheritance;

import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectConstraintTypes;
import static com.gwtent.client.test.validate.tck.util.TestUtil.assertCorrectNumberOfViolations;
import static org.testng.Assert.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.PropertyDescriptor;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;

/**
 * @author Hardy Ferentschik
 */

public class GroupInheritanceTest extends AbstractTest {

	@Test
	@SpecAssertion(section = "3.4.1", id = "a")
	public void testGroupCanInheritGroupsViaInterfaceInheritance() {
		Validator validator = TestUtil.getValidatorUnderTest();
		Part part = new Part();
		part.setPartNumber( 123456 );

		Set<ConstraintViolation<Part>> violations = validator.validate( part, All.class );
		assertCorrectNumberOfViolations( violations, 2 );
		assertCorrectConstraintTypes( violations, Digits.class, AssertTrue.class );

		part.setPartNumber( 12345 );
		part.setQaChecked( true );
		violations = validator.validate( part, All.class );
		assertCorrectNumberOfViolations( violations, 0 );
	}

	@Test
	@SpecAssertion(section = "3.4.1", id = "b")
	public void testGroupMembership() {
		Validator validator = TestUtil.getValidatorUnderTest();
		BeanDescriptor descriptor = validator.getConstraintsForClass( MiniaturePart.class );

		//  PreManufacturing belongs implicitly to All
		PropertyDescriptor propertyDescriptor = descriptor.getConstraintsForProperty( "partNumber" );
		Set<ConstraintDescriptor<?>> descriptorsForGroup = propertyDescriptor.findConstraints()
				.unorderedAndMatchingGroups( All.class )
				.getConstraintDescriptors();
		assertEquals( descriptorsForGroup.size(), 1, "Wrong number of descriptors" );
		assertEquals( descriptorsForGroup.iterator().next().getAnnotation().annotationType(), Digits.class );

		//  PostManufacturing belongs implicitly to All
		propertyDescriptor = descriptor.getConstraintsForProperty( "qaChecked" );
		descriptorsForGroup = propertyDescriptor.findConstraints()
				.unorderedAndMatchingGroups( All.class )
				.getConstraintDescriptors();
		assertEquals( descriptorsForGroup.size(), 1, "Wrong number of descriptors" );
    		assertEquals( descriptorsForGroup.iterator().next().getAnnotation().annotationType(), AssertTrue.class );

		propertyDescriptor = descriptor.getConstraintsForProperty( "size" );
		descriptorsForGroup = propertyDescriptor.findConstraints()
				.unorderedAndMatchingGroups( All.class )
				.getConstraintDescriptors();
		assertEquals( descriptorsForGroup.size(), 1, "Wrong number of descriptors" );
		assertEquals( descriptorsForGroup.iterator().next().getAnnotation().annotationType(), Max.class );
	}

	class Part {
		@Digits(integer = 5, fraction = 0, groups = PreManufacturing.class)
		private int partNumber;

		@AssertTrue(groups = PostManufacturing.class)
		private boolean qaChecked;

		public int getPartNumber() {
			return partNumber;
		}

		public void setPartNumber(int partNumber) {
			this.partNumber = partNumber;
		}

		public boolean getQaChecked() {
			return qaChecked;
		}

		public void setQaChecked(boolean qaChecked) {
			this.qaChecked = qaChecked;
		}
	}

	class MiniaturePart extends Part {
		@Max(value = 10, groups = All.class)
		private int size;
	}

	interface PreManufacturing {
	}

	interface PostManufacturing {
	}

	interface All extends PreManufacturing, PostManufacturing {
	}

}
