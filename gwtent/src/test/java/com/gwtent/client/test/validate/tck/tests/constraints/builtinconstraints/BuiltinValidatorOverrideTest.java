// $Id: BuiltinValidatorOverrideTest.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.builtinconstraints;

import javax.validation.constraints.NotNull;

import com.gwtent.client.test.validate.tck.AbstractTest;

/**
 * @author Hardy Ferentschik
 */
public class BuiltinValidatorOverrideTest extends AbstractTest {
//
//	@Test
//	@SpecAssertion(section = "6", id = "b")
//	public void testXmlConfiguredValidatorConfigurationHasPrecedence() {
//		Configuration<?> config = TestUtil.getConfigurationUnderTest();
//		InputStream in = getInputStreamForPath(
//				"org/hibernate/jsr303/tck/tests/constraints/builtinconstraints/builtin-constraints-override.xml"
//		);
//		config.addMapping( in );
//		Validator validator = config.buildValidatorFactory().getValidator();
//		DummyEntity dummy = new DummyEntity();
//		Set<ConstraintViolation<DummyEntity>> violations = validator.validate( dummy );
//		assertCorrectNumberOfViolations( violations, 0 );
//
//		dummy.dummyProperty = "foobar";
//		violations = validator.validate( dummy );
//		assertCorrectNumberOfViolations( violations, 1 );
//	}

	class DummyEntity {
		@NotNull
		String dummyProperty;
	}
}
