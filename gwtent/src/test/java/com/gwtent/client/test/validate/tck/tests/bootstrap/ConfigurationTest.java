// $Id: ConfigurationTest.java 20225 2010-08-23 14:02:20Z hardy.ferentschik $
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

import javax.validation.Configuration;

import com.gwtent.client.test.validate.tck.AbstractTest;
import com.gwtent.client.test.validate.tck.SpecAssertion;
import com.gwtent.client.test.validate.tck.Test;
import com.gwtent.client.test.validate.tck.util.TestUtil;
import com.gwtent.reflection.client.ClassHelper;
import com.gwtent.reflection.client.ParameterizedType;
import com.gwtent.reflection.client.Type;

/**
 * @author Hardy Ferentschik
 */

public class ConfigurationTest extends AbstractTest {

	@Test
	@SpecAssertion(section = "4.4.3", id = "a")
	public void testProviderUnderTestDefinesSubInterfaceOfConfiguration() {
		boolean foundSubinterfaceOfConfiguration = false;
		Type[] types = ClassHelper.AsClass(TestUtil.getValidationProviderUnderTest().getClass()).getGenericInterfaces();
		for ( Type type : types ) {
			if ( type.isParameterized() != null ) {
				ParameterizedType paramType = type.isParameterized();
				Type[] typeArguments = paramType.getActualTypeArguments();
//				for ( Type typeArgument : typeArguments ) {
//					if ( typeArgument instanceof Class && Configuration.class.isAssignableFrom( ( Class ) typeArgument ) ) {
//						foundSubinterfaceOfConfiguration = true;
//					}
//				}
			}
		}
		assertTrue( foundSubinterfaceOfConfiguration, "Could not find subinterface of Configuration" );
	}
}
