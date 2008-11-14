/**
 * Copyright (C) 2006 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gwtent.gen.aop;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

import com.gwtent.aop.matcher.Matcher;
import com.gwtent.aop.matcher.ClassMethodMatcher;
import com.gwtent.aop.matcher.Objects;
import com.gwtent.client.aop.AOPRegistor;
import com.gwtent.client.aop.intercept.MethodInterceptor;


class MethodAspect {

  private String matcherClassName = "";
  public String getMatcherClassName() {
		return matcherClassName;
	}


	private final Matcher<? super Class<?>> classMatcher;
	private final Matcher<? super Method> methodMatcher;
	
//  MethodAspect(Matcher<? super Class<?>> classMatcher,
//      Matcher<? super Method> methodMatcher, MethodInterceptor... interceptors) {
//	  this(classMatcher, methodMatcher, Arrays.asList(interceptors));
//  }
 
  MethodAspect(String matcherClassName, MethodInterceptor... interceptors) {
  	try {
			Class<ClassMethodMatcher> classz = (Class<ClassMethodMatcher>) Class.forName(matcherClassName);
			try {
				ClassMethodMatcher matcher = classz.getConstructor(null).newInstance(null);
				this.classMatcher = matcher.getClassMatcher();
				this.methodMatcher = matcher.getMethodMatcher();
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
  }
  
//  MethodAspect(Matcher<? super Class<?>> classMatcher,
//	      Matcher<? super Method> methodMatcher, List<MethodInterceptor> interceptors) {
//	    this.classMatcher = Objects.nonNull(classMatcher, "class matcher");
//	    this.methodMatcher = Objects.nonNull(methodMatcher, "method matcher");
//	    this.interceptors = interceptors;
//	  }

  boolean matches(Class<?> clazz) {
    return classMatcher.matches(clazz);
  }

  boolean matches(Method method) {
    return methodMatcher.matches(method);
  }
}
