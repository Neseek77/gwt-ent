package com.gwtent.gen.aop;

import java.util.List;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.gwtent.aop.Pointcut;

public interface AspectCollector {
	public static AspectCollector INSTANCE = null;
	
	/**
	 * All classes who need aspect support 
	 * @return
	 */
	//List<JClassType> allAspectedClasses();
	
	/**
	 * All classes who provide advice opeartion
	 * @return
	 */
	List<Class<?>> allAdvicedClasses();
	
	
	List<Pointcut> allPointCut();
	
}
