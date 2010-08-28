// $Id: UnknownProviderBootstrapTest.java 20225 2010-08-23 14:02:20Z hardy.ferentschik $
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

import javax.validation.Validation;
import javax.validation.ValidationException;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.Test;

/**
 * @author Hardy Ferentschik
 */
public class UnknownProviderBootstrapTest extends AbstractTest {

	@Test(expectedExceptions = ValidationException.class)
	@SpecAssertion(section = "4.4.5", id = "f")
	public void testUnknownProviderThrowsValidationException() {
		Validation.byDefaultProvider().configure();
		//ValidatorFactory factory = configuration.buildValidatorFactory();
	}
}
