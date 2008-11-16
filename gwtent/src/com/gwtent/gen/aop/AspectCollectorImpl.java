package com.gwtent.gen.aop;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.aspectj.lang.annotation.Aspect;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.gwtent.aop.ClassFilter;
import com.gwtent.aop.MethodMatcher;
import com.gwtent.aop.Pointcut;
import com.gwtent.aop.matcher.AspectJExpress;
import com.gwtent.gen.GenUtils;

/**
 * For AOPCreator
 * matchers from all the aspect classes
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
public class AspectCollectorImpl implements Pointcut, ClassFilter, MethodMatcher, AspectCollector {

	private final TypeOracle typeOracle;
	
	private List<JClassType> aspectClasses = new ArrayList<JClassType>();
	
	private Map<JMethod, Pointcut> pointcuts = new HashMap<JMethod, Pointcut>();
	
	public AspectCollectorImpl(TypeOracle typeOracle){
		this.typeOracle = typeOracle;
		
		for (JClassType classType : typeOracle.getTypes()) {
			Aspect aspect = classType.getAnnotation(org.aspectj.lang.annotation.Aspect.class);
			if (aspect != null){
				aspectClasses.add(classType);
			}
		}
		
		for (JClassType classType : this.aspectClasses){
			for (JMethod method : classType.getMethods()){
				String expression = AOPUtils.getExpression(method);
				if ((expression != null) && (expression.length() > 0)){
						this.pointcuts.put(method, new AspectJExpress(expression));
				}
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
		for (JMethod method : this.pointcuts.keySet()){
			if (pointcuts.get(method).getClassFilter().matches(clazz))
				return true;
		}
		
		return false;
	}

	@Override
	public boolean matches(Method method, Class<?> targetClass, Object... args) {
		for (JMethod amethod : this.pointcuts.keySet()){
			if (pointcuts.get(amethod).getMethodMatcher().matches(method, targetClass, args))
				return true;
		}
		
		return false;
	}
	
	/* (non-Javadoc)
	 * @see com.gwtent.gen.aop.AspectCollector#allMatches(com.google.gwt.core.ext.typeinfo.JMethod)
	 */
	public List<JMethod> allMatches(JMethod method){
		List<JMethod> methods = new ArrayList<JMethod>();
		
		Method javaMethod = GenUtils.JMethodToMethod(method);
		Class<?> javaClass = GenUtils.GWTTypeToClass(method.getEnclosingType());
		
		for (JMethod amethod : this.pointcuts.keySet()){
			if (pointcuts.get(amethod).getMethodMatcher().matches(javaMethod, javaClass))
				methods.add(amethod);
		}
		
		return methods;
	}
	
	/* (non-Javadoc)
	 * @see com.gwtent.gen.aop.AspectCollector#getPointcut()
	 */
	public Pointcut getPointcut(){
		return this;
	}

}
