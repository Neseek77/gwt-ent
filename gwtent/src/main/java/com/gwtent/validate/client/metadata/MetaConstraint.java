// $Id: MetaConstraint.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $// $Id: MetaConstraint.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.validate.client.metadata;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.ValidationException;

import com.gwtent.reflection.client.Member;
import com.gwtent.reflection.client.Method;
import com.gwtent.reflection.client.Type;
import com.gwtent.reflection.client.TypeOracle;
import com.gwtent.validate.client.impl.ConstraintTree;
import com.gwtent.validate.client.impl.GlobalExecutionContext;
import com.gwtent.validate.client.impl.LocalExecutionContext;
import com.gwtent.validate.client.util.ReflectionHelper;


/**
 * Instances of this class abstract the constraint type  (class, method or field constraint) and give access to
 * meta data about the constraint. This allows a unified handling of constraints in the validator implementation.
 *
 * @author Hardy Ferentschik
 */
public class MetaConstraint<T, A extends Annotation> {

	/**
	 * The member the constraint was defined on.
	 * Field or Method
	 */
	private final Member member;

	/**
	 * The JavaBeans name of the field/property the constraint was placed on. {@code null} if this is a
	 * class level constraint.
	 */
	private final String propertyName;

	/**
	 * The class of the bean hosting this constraint.
	 */
	private final Class<T> beanClass;

	/**
	 * The constraint tree created from the constraint annotation.
	 */
	private final ConstraintTree<A> constraintTree;

	/**
	 * @param beanClass The class in which the constraint is defined on
	 * @param member The member on which the constraint is defined on, {@code null} if it is a class constraint}
	 * @param constraintDescriptor The constraint descriptor for this constraint
	 */
	public MetaConstraint(Class<T> beanClass, Member member, ConstraintDescriptorImpl<A> constraintDescriptor) {
		this.member = member;
		if ( this.member != null ) {
			this.propertyName = ReflectionHelper.getPropertyName( member );
			if ( member instanceof Method && propertyName == null ) { // can happen if member is a Method which does not follow the bean convention
				throw new ValidationException(
						"Annotated methods must follow the JavaBeans naming convention. " + ((Method)member).getName() + "() does not."
				);
			}
		} else {
			this.propertyName = null;
		}
		this.beanClass = beanClass;
		constraintTree = new ConstraintTree<A>( constraintDescriptor );
	}

	/**
	 * @return Returns the list of groups this constraint is part of. This might include the default group even when
	 *         it is not explicitly specified, but part of the redefined default group list of the hosting bean.
	 */
	public Set<Class<?>> getGroupList() {
		return constraintTree.getDescriptor().getGroups();
	}

	public ConstraintDescriptorImpl<A> getDescriptor() {
		return constraintTree.getDescriptor();
	}

	public Class<T> getBeanClass() {
		return beanClass;
	}

	public Member getMember() {
		return member;
	}

	/**
	 * @return The JavaBeans name of the field/property the constraint was placed on. {@code null} if this is a
	 *         class level constraint.
	 */
	public String getPropertyName() {
		return propertyName;
	}

	public ElementType getElementType() {
		return constraintTree.getDescriptor().getElementType();
	}

	public <T, U, V> boolean validateConstraint(GlobalExecutionContext<T> executionContext, LocalExecutionContext<U, V> localExecutionContext) {
		List<ConstraintViolation<T>> constraintViolations = new ArrayList<ConstraintViolation<T>>();
		localExecutionContext.setElementType( getElementType() );
		constraintTree.validateConstraints(
				typeOfAnnotatedElement(), executionContext, localExecutionContext, constraintViolations
		);
		if ( constraintViolations.size() > 0 ) {
			executionContext.addConstraintFailures( constraintViolations );
			return false;
		}
		return true;
	}

	/**
	 * @param o the object from which to retrieve the value.
	 *
	 * @return Returns the value for this constraint from the specified object. Depending on the type either the value itself
	 *         is returned of method or field access is used to access the value.
	 */
	public Object getValue(Object o) {
		switch ( getElementType() ) {
			case TYPE: {
				return o;
			}
			default: {
				return ReflectionHelper.getValue( member, o );
			}
		}
	}

	private Type typeOfAnnotatedElement() {
		Type t = null;
		switch ( getElementType() ) {
			case TYPE: {
				t =  TypeOracle.Instance.getClassType(beanClass);
				break;
			}
			default: {
				t = ReflectionHelper.typeOf( member );
//				if ( t instanceof Class && ( ( Class ) t ).isPrimitive() ) {
//					t = ReflectionHelper.boxedType( t );
//				}
				if (t.isPrimitive() != null) {
					t = TypeOracle.Instance.getClassType(ReflectionHelper.boxedType( t ));
				}
			}
		}
		return t;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "MetaConstraint" );
		sb.append( "{beanClass=" ).append( beanClass );
		sb.append( ", member=" ).append( member );
		sb.append( ", propertyName='" ).append( propertyName ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}
