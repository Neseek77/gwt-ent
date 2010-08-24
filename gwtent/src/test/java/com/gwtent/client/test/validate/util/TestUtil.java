// $Id: TestUtil.java 19635 2010-05-31 14:03:26Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.util;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.validation.Configuration;
import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.metadata.PropertyDescriptor;
import javax.validation.spi.ValidationProvider;

import com.gwtent.validate.client.GWTEntValidatorConfiguration;
import com.gwtent.validate.client.GWTEntValidatorProvider;
import com.gwtent.validate.client.impl.PathImpl;

/**
 * Tests for the <code>ReflectionHelper</code>.
 *
 * @author Hardy Ferentschik
 */
public class TestUtil {
	private static Validator hibernateValidator;

	private TestUtil() {
	}

	public static Validator getValidator() {
		if ( hibernateValidator == null ) {
			Configuration configuration = getConfiguration();
			configuration.traversableResolver( new DummyTraversableResolver() );
			hibernateValidator = configuration.buildValidatorFactory().getValidator();
		}
		return hibernateValidator;
	}

	public static Configuration<GWTEntValidatorConfiguration> getConfiguration() {
		return getConfiguration( GWTEntValidatorProvider.class );
	}

//	public static Configuration<GWTEntValidatorConfiguration> getConfiguration() {
//		return getConfiguration( GWTEntValidatorProvider.class );
//	}

//	public static <T extends Configuration<T>, U extends ValidationProvider<T>> T getConfiguration(Class<U> type) {
//		return getConfiguration( type, Locale.ENGLISH );
//	}

	public static <T extends Configuration<T>, U extends ValidationProvider<T>> T getConfiguration(Class<U> type) {
		//Locale.setDefault( locale );
		return Validation.byProvider( type ).configure();
	}

	/**
	 * @param path The path to the xml file which should server as <code>validation.xml</code> for the returned
	 * <code>Validator</code>.
	 *
	 * @return A <code>Validator</code> instance which respects the configuration specified in the file with the path
	 *         <code>path</code>.
	 */
	public static Validator getValidatorWithCustomConfiguration(String path) {
		//Thread.currentThread().setContextClassLoader( new CustomValidationXmlClassLoader( path ) );
		return getConfiguration().buildValidatorFactory().getValidator();
	}

	public static PropertyDescriptor getPropertyDescriptor(Class<?> clazz, String property) {
		Validator validator = getValidator();
		return validator.getConstraintsForClass( clazz ).getConstraintsForProperty( property );
	}

	public static <T> void assertCorrectConstraintViolationMessages(Set<ConstraintViolation<T>> violations, String... messages) {
		List<String> actualMessages = new ArrayList<String>();
		for ( ConstraintViolation<?> violation : violations ) {
			actualMessages.add( violation.getMessage() );
		}

		assertEquals("Wrong number of error messages" , actualMessages.size(), messages.length);

		for ( String expectedMessage : messages ) {
			assertTrue(
					"The message '" + expectedMessage + "' should have been in the list of actual messages: " + actualMessages,
					actualMessages.contains( expectedMessage )
					
			);
			actualMessages.remove( expectedMessage );
		}
		assertTrue(
				"Actual messages contained more messages as specified expected messages", actualMessages.isEmpty()
		);
	}

	public static <T> void assertCorrectConstraintTypes(Set<ConstraintViolation<T>> violations, Class<?>... expectedConstraintTypes) {
		List<String> actualConstraintTypes = new ArrayList<String>();
		for ( ConstraintViolation<?> violation : violations ) {
			actualConstraintTypes.add(
					( ( Annotation ) violation.getConstraintDescriptor().getAnnotation() ).annotationType().getName()
			);
		}

		assertEquals(
				"Wrong number of constraint types.", expectedConstraintTypes.length, actualConstraintTypes.size()
		);

		for ( Class<?> expectedConstraintType : expectedConstraintTypes ) {
			assertTrue(
					"The constraint type " + expectedConstraintType.getName() + " should have been violated.",
					actualConstraintTypes.contains( expectedConstraintType.getName() )
					
			);
		}
	}

	public static <T> void assertCorrectPropertyPaths(Set<ConstraintViolation<T>> violations, String... propertyPaths) {
		List<Path> propertyPathsOfViolations = new ArrayList<Path>();
		for ( ConstraintViolation<?> violation : violations ) {
			propertyPathsOfViolations.add( violation.getPropertyPath() );
		}

		for ( String propertyPath : propertyPaths ) {
			Path expectedPath = PathImpl.createPathFromString( propertyPath );
			boolean containsPath = false;
			for ( Path actualPath : propertyPathsOfViolations ) {
				if ( assertEqualPaths( expectedPath, actualPath ) ) {
					containsPath = true;
					break;
				}
			}
			if ( !containsPath ) {
				fail( expectedPath + " is not in the list of path instances contained in the actual constraint violations: " + propertyPathsOfViolations );
			}
		}
	}

	public static void assertConstraintViolation(ConstraintViolation violation, String errorMessage, Class rootBean, Object invalidValue, String propertyPath) {
		assertEquals(
				"Wrong propertyPath",
				violation.getPropertyPath(),
				PathImpl.createPathFromString( propertyPath )
				
		);
		assertConstraintViolation( violation, errorMessage, rootBean, invalidValue );
	}

	public static void assertConstraintViolation(ConstraintViolation violation, String errorMessage, Class rootBean, Object invalidValue) {
		assertEquals(
				"Wrong invalid value",
				violation.getInvalidValue(),
				invalidValue
				
		);
		assertConstraintViolation( violation, errorMessage, rootBean );
	}

	public static void assertConstraintViolation(ConstraintViolation violation, String errorMessage, Class rootBean) {
		assertEquals(
				"Wrong root bean type",
				violation.getRootBean().getClass(),
				rootBean
				
		);
		assertConstraintViolation( violation, errorMessage );
	}

	public static void assertConstraintViolation(ConstraintViolation violation, String message) {
		assertEquals("Wrong message", violation.getMessage(), message );
	}

	public static void assertNumberOfViolations(Set violations, int expectedViolations) {
		assertEquals( "Wrong number of constraint violations", violations.size(), expectedViolations );
	}

	public static boolean assertEqualPaths(Path p1, Path p2) {
		Iterator<Path.Node> p1Iterator = p1.iterator();
		Iterator<Path.Node> p2Iterator = p2.iterator();
		while ( p1Iterator.hasNext() ) {
			Path.Node p1Node = p1Iterator.next();
			if ( !p2Iterator.hasNext() ) {
				return false;
			}
			Path.Node p2Node = p2Iterator.next();

			// do the comparison on the node values
			if ( p2Node.getName() == null ) {
				if ( p1Node.getName() != null ) {
					return false;
				}
			}
			else if ( !p2Node.getName().equals( p1Node.getName() ) ) {
				return false;
			}

			if ( p2Node.isInIterable() != p1Node.isInIterable() ) {
				return false;
			}


			if ( p2Node.getIndex() == null ) {
				if ( p1Node.getIndex() != null ) {
					return false;
				}
			}
			else if ( !p2Node.getIndex().equals( p1Node.getIndex() ) ) {
				return false;
			}

			if ( p2Node.getKey() == null ) {
				if ( p1Node.getKey() != null ) {
					return false;
				}
			}
			else if ( !p2Node.getKey().equals( p1Node.getKey() ) ) {
				return false;
			}
		}

		return !p2Iterator.hasNext();
	}
//
//	private static class CustomValidationXmlClassLoader extends ClassLoader {
//		private final String customValidationXmlPath;
//
//		CustomValidationXmlClassLoader(String pathToCustomValidationXml) {
//			super( CustomValidationXmlClassLoader.class.getClassLoader() );
//			customValidationXmlPath = pathToCustomValidationXml;
//		}
//
//		public InputStream getResourceAsStream(String path) {
//			String finalPath = path;
//			if ( "META-INF/validation.xml".equals( path ) ) {
////				log.info( "Using {} as validation.xml", customValidationXmlPath );
//				finalPath = customValidationXmlPath;
//			}
//			return super.getResourceAsStream( finalPath );
//		}
//	}
}
