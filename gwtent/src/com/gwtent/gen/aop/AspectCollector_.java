package com.gwtent.gen.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.gwtent.aop.ClassFilter;
import com.gwtent.aop.MethodMatcher;
import com.gwtent.aop.Pointcut;
import com.gwtent.client.aop.annotation.Aspect;

public class AspectCollector_ implements Pointcut, ClassFilter, MethodMatcher {

	private final TypeOracle typeOracle;
	
	private List<JClassType> aspectClasses = new ArrayList<JClassType>();
	
	public AspectCollector_(TypeOracle typeOracle){
		this.typeOracle = typeOracle;
		
		for (JClassType classType : typeOracle.getTypes()) {
			Aspect aspect = classType.getAnnotation(Aspect.class);
			if (aspect != null){
				aspectClasses.add(classType);
			}
		}
	}
	
	@Override
	public ClassFilter getClassFilter() {
		return this;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return this;
	}

	@Override
	public boolean matches(Class<?> clazz) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass, Object... args) {
		// TODO Auto-generated method stub
		return false;
	}

}
