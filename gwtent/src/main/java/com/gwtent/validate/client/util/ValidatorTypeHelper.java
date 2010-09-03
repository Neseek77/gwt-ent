// $Id: ValidatorTypeHelper.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.validate.client.util;

import java.lang.annotation.Annotation;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.ConstraintValidator;
import javax.validation.ValidationException;

import com.gwtent.reflection.client.ClassHelper;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.ParameterizedType;
import com.gwtent.reflection.client.Type;


/**
 * Helper methods around <code>ConstraintValidator</code> types.
 *
 * @author Emmanuel Bernanrd
 * @author Hardy Ferentschik
 */
public class ValidatorTypeHelper {
	private static final int VALIDATOR_TYPE_INDEX = 1;

	/**
	 * @param validators List of constraint validator classes (for a given constraint).
	 *
	 * @return Return a Map&lt;Class, Class&lt;? extends ConstraintValidator&gt;&gt; where the map
	 *         key is the type the validator accepts and value the validator class itself.
	 */
	public static <T extends Annotation> Map<Type, Class<? extends ConstraintValidator<?, ?>>> getValidatorsTypes(List<Class<? extends ConstraintValidator<T, ?>>> validators) {
		if ( validators == null || validators.size() == 0 ) {
			throw new ValidationException( "No ConstraintValidators associated to @Constraint" );
		}
		else {
			Map<Type, Class<? extends ConstraintValidator<?, ?>>> validatorsTypes =
					new HashMap<Type, Class<? extends ConstraintValidator<?, ?>>>();
			for ( Class<? extends ConstraintValidator<?, ?>> validator : validators ) {
				validatorsTypes.put( extractType( validator ), validator );
			}
			return validatorsTypes;
		}
	}

	private static Type extractType(Class<? extends ConstraintValidator<?, ?>> validator) {
		Map<Type, Type> resolvedTypes = new HashMap<Type, Type>();
		Type constraintValidatorType = resolveTypes( resolvedTypes, ClassHelper.AsClass(validator).getType() );

		//we now have all bind from a type to its resolution at one level
		Type validatorType = ( ( ParameterizedType ) constraintValidatorType ).getActualTypeArguments()[VALIDATOR_TYPE_INDEX];
		if ( validatorType == null ) {
			throw new ValidationException( "null is an invalid type for a constraint validator. " + validator.getName());
		}
		//TODO support GenericArrayType
//		else if ( validatorType instanceof GenericArrayType ) {
//			validatorType = TypeUtils.getArrayType( TypeUtils.getComponentType( validatorType ) );
//		}
//Why here using (while)? changed i
//		while ( resolvedTypes.containsKey( validatorType ) ) {
//			validatorType = resolvedTypes.get( validatorType );
//		}
		if ( resolvedTypes.containsKey( validatorType ) ) {
			validatorType = resolvedTypes.get( validatorType );
		}
		//FIXME raise an exception if validatorType is not a class
		return validatorType;
	}

	private static Type resolveTypes(Map<Type, Type> resolvedTypes, Type type) {
		if ( type == null ) {
			return null;
		}	else if ( type.isParameterized() != null ) {
			ParameterizedType<?> paramType = type.isParameterized();
			if ( paramType.getRawType().isClassOrInterface() == null ) {
				return null; //don't know what to do here
			}
			ClassType<?> rawType = paramType.getRawType().isClassOrInterface();
			//Class<?> rawType = ( Class<?> ) paramType.getRawType();

			//TypeVariable<?>[] originalTypes = rawType.getTypeParameters();
			Type[] partiallyResolvedTypes = paramType.getActualTypeArguments();
			int nbrOfParams = partiallyResolvedTypes.length;
			for ( int i = 0; i < nbrOfParams; i++ ) {
				resolvedTypes.put( partiallyResolvedTypes[i], partiallyResolvedTypes[i] );
			}

			if ( rawType.getDeclaringClass().equals( ConstraintValidator.class ) ) {
				//we found our baby
				return type;
			}
			else {
				Type returnedType = resolveTypeForClassAndHierarchy( resolvedTypes, rawType.getDeclaringClass() );
				if ( returnedType != null ) {
					return returnedType;
				}
			}
		}	else if ( type.isClassOrInterface() != null) {
			Class<?> clazz = type.isClassOrInterface().getDeclaringClass();
			final Type returnedType = resolveTypeForClassAndHierarchy( resolvedTypes, clazz );
			if ( returnedType != null ) {
				return returnedType;
			}
		}	
		//else we don't care I think
		return null;
	}

	private static Type resolveTypeForClassAndHierarchy(Map<Type, Type> resolvedTypes, Class<?> clazz) {
		ClassHelper<?> helper = ClassHelper.AsClass(clazz);
		Type returnedType = resolveTypes( resolvedTypes, helper.getGenericSuperclass() );
		if ( returnedType != null ) {
			return returnedType;
		}
		for ( Type genericInterface : helper.getGenericInterfaces() ) {
			returnedType = resolveTypes( resolvedTypes, genericInterface );
			if ( returnedType != null ) {
				return returnedType;		}
		}
//		for ( Type genericInterface : helper.getType().isClassOrInterface().getImplementedInterfaces() ) {
//			returnedType = resolveTypes( resolvedTypes, genericInterface );
//			if ( returnedType != null ) {
//				return returnedType;
//			}
//		}
		return null;
	}
}
