// $Id: AnnotationFactory.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.validate.client.metadata.annotationfactory;

import java.lang.annotation.Annotation;

import com.gwtent.reflection.client.AnnotationType;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Method;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.reflection.client.TypeOracle;


/**
 * Creates live annotations (actually <code>AnnotationProxies</code>) from <code>AnnotationDescriptors</code>.
 *
 * @author Paolo Perrotta
 * @author Davide Marchignoli
 * @see AnnotationProxy
 */
public class AnnotationFactory {

	@SuppressWarnings("unchecked")
	public static <T extends Annotation> T create(AnnotationDescriptor<T> descriptor) {
		ClassType<T> type = TypeOracle.Instance.getClassType(descriptor.type());
		
		if (type != null){
			if ( type.isAnnotation() != null){
				AnnotationType<T> annType = type.isAnnotation();
				Object[] params = new Object[type.getMethods().length];
				
				int i = 0;
				for (Method m : type.getMethods()){
					params[i] = descriptor.valueOf(m.getName());
					i++;
				}
				
				return annType.createAnnotation(params);
			}else{
				throw new RuntimeException("Type must annotation: " + type.getName());
			}
		}else{
			ReflectionUtils.checkReflection(descriptor.type());
		}
		
//		boolean isSecured = System.getSecurityManager() != null;
//		GetClassLoader action = GetClassLoader.fromContext();
//		ClassLoader classLoader = isSecured ? AccessController.doPrivileged( action ) : action.run();
//        //TODO round 34ms to generate the proxy, hug! is Javassist Faster?
//        Class<T> proxyClass = (Class<T>) Proxy.getProxyClass( classLoader, descriptor.type() );
//		InvocationHandler handler = new AnnotationProxy( descriptor );
//		try {
//			return getProxyInstance( proxyClass, handler );
//		}
//		catch (RuntimeException e) {
//			throw e;
//		}
//		catch (Exception e) {
//			throw new RuntimeException( e );
//		}
		return null;
	}
//
//	private static <T extends Annotation> T getProxyInstance(Class<T> proxyClass, InvocationHandler handler) throws
//			SecurityException, NoSuchMethodException, IllegalArgumentException, InstantiationException,
//			IllegalAccessException, InvocationTargetException {
//		GetConstructor<T> action = GetConstructor.action( proxyClass, InvocationHandler.class );
//		final Constructor<T> constructor;
//		if ( System.getSecurityManager() != null ) {
//			constructor = AccessController.doPrivileged( action );
//		}
//		else {
//			constructor = action.run();
//		}
//		return constructor.newInstance( handler );
//	}
}
