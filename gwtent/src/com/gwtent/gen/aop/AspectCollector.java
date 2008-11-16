package com.gwtent.gen.aop;

import java.util.List;

import com.google.gwt.core.ext.typeinfo.JMethod;
import com.gwtent.aop.Pointcut;

/**
 * For AOPCreator
 * Give a JMethod, return all methods implemented by Aspect Classes.
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
public interface AspectCollector {

	public List<JMethod> allMatches(JMethod method);

	public Pointcut getPointcut();

}