package com.gwtent.validate.client.impl;

import java.lang.annotation.ElementType;

import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.Path.Node;

/**
 * 
 * @author James Luo
 *
 * 29/07/2010 5:15:29 PM
 */
public class TraversableResolverImpl implements TraversableResolver {

	public boolean isCascadable(Object traversableObject,
			Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, ElementType elementType) {
		return false;
	}

	public boolean isReachable(Object traversableObject,
			Node traversableProperty, Class<?> rootBeanType,
			Path pathToTraversableObject, ElementType elementType) {
		return false;
	}

}
