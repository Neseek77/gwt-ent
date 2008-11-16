package com.gwtent.gen.aop;

import java.lang.annotation.Annotation;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import com.google.gwt.core.ext.typeinfo.JMethod;

public class AOPUtils {

	/**
	 * Get AspectJ expression from a method
	 * @param method
	 * @return the expression
	 */
	public static String getExpression(JMethod method){
		if (method.getAnnotation(Around.class) != null)
			return method.getAnnotation(Around.class).value();
		else if (method.getAnnotation(Before.class) != null)
			return method.getAnnotation(Before.class).value();
		else if (method.getAnnotation(After.class) != null)
			return method.getAnnotation(After.class).value();
		else if (method.getAnnotation(AfterReturning.class) != null)
			return method.getAnnotation(AfterReturning.class).value();
		else if (method.getAnnotation(AfterThrowing.class) != null)
			return method.getAnnotation(AfterThrowing.class).value();
		else if (method.getAnnotation(Pointcut.class) != null)
			return method.getAnnotation(Pointcut.class).value();
		else return null;
	}
	
}
