package com.gwtent.test.aspectj;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.aspectj.weaver.BCException;
import org.aspectj.weaver.reflect.ReflectionWorld;
import org.aspectj.weaver.tools.ContextBasedMatcher;
import org.aspectj.weaver.tools.FuzzyBoolean;
import org.aspectj.weaver.tools.JoinPointMatch;
import org.aspectj.weaver.tools.MatchingContext;
import org.aspectj.weaver.tools.PointcutDesignatorHandler;
import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParameter;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.PointcutPrimitive;
import org.aspectj.weaver.tools.ShadowMatch;

//import org.springframework.aop.ProxyMethodInvocation;
//import org.springframework.aop.interceptor.ExposeInvocationInterceptor;
//import org.springframework.aop.support.AopUtils;


import com.gwtent.aop.matcher.Matcher;
import com.gwtent.aop.matcher.MethodMatcher;

public class AspectJExpress {

	private static final Set<PointcutPrimitive> DEFAULT_SUPPORTED_PRIMITIVES = new HashSet<PointcutPrimitive>();

	static {
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.EXECUTION);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.ARGS);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.REFERENCE);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.THIS);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.TARGET);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.WITHIN);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ANNOTATION);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_WITHIN);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_ARGS);
		DEFAULT_SUPPORTED_PRIMITIVES.add(PointcutPrimitive.AT_TARGET);
	}

	private String expression;
	
	private final Map<Method, ShadowMatch> shadowMapCache = new HashMap<Method, ShadowMatch>();
	 
	private PointcutParser pointcutParser;

	private Class<?> pointcutDeclarationScope;

	private String[] pointcutParameterNames = new String[0];

	private Class[] pointcutParameterTypes = new Class[0];

	private PointcutExpression pointcutExpression;
	
	private final Set<MethodMatcher> methodMatchers = new HashSet<MethodMatcher>();

	
	public AspectJExpress(){
		this.pointcutParser =
			PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
					DEFAULT_SUPPORTED_PRIMITIVES);
		this.pointcutParser.registerPointcutDesignatorHandler(new BeanNamePointcutDesignatorHandler());
	}
	
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("AspectJExpressionPointcut: ");
		if (this.pointcutParameterNames != null && this.pointcutParameterTypes != null) {
			sb.append("(");
			for (int i = 0; i < this.pointcutParameterTypes.length; i++) {
				sb.append(this.pointcutParameterTypes[i].getName());
				sb.append(" ");
				sb.append(this.pointcutParameterNames[i]);
				if ((i+1) < this.pointcutParameterTypes.length) {
					sb.append(", ");
				}
			}
			sb.append(")");
		}
		sb.append(" ");
		if (getExpression() != null) {
			sb.append(getExpression());
		}
		else {
			sb.append("<pointcut expression not set>");
		}
		return sb.toString();
	}
	
	public void setExpression(String expression) {
		this.expression = expression;
	}


	public String getExpression() {
		return expression;
	}
	
	/**
	 * Return the underlying AspectJ pointcut expression.
	 */
	public PointcutExpression getPointcutExpression() {
		checkReadyToMatch();
		return this.pointcutExpression;
	}


	public boolean matches(Class targetClass) {
		checkReadyToMatch();
		boolean result = false;
		try {
			result = this.pointcutExpression.couldMatchJoinPointsInType(targetClass);
		}
		catch (BCException ex) {
			//logger.debug("PointcutExpression matching rejected target class", ex);
			result = false;
		}
		
		if (result)
			return result;
		else{
			return matchesByClassMatcher(targetClass) != null;
		}	
	}

	public boolean isRuntime() {
		checkReadyToMatch();
		return this.pointcutExpression.mayNeedDynamicTest();
	}

	public boolean matches(Method method, Class targetClass, Object[] args) {
		checkReadyToMatch();
//		ShadowMatch shadowMatch = null;
		ShadowMatch originalShadowMatch = null;
		try {
			//shadowMatch = getShadowMatch(AopUtils.getMostSpecificMethod(method, targetClass), method);
			originalShadowMatch = getShadowMatch(method, method);
			if (originalShadowMatch.alwaysMatches()) 
				return true;
			else
				return matchesByMethodMatcher(method, targetClass, args);
				
		}
		catch (ReflectionWorld.ReflectionWorldException ex) {
			// Could neither introspect the target class nor the proxy class ->
			// let's simply consider this method as non-matching.
			return false;
		}
	}

	void addMethodMatcher(MethodMatcher methodMatcher){
		methodMatchers.add(methodMatcher);
	}
	
	/**
	 * 
	 * @param clazz
	 * @return if matcher, return MethodMatcher, otherwise return null
	 */
	private MethodMatcher matchesByClassMatcher(Class<?> clazz){
		for (MethodMatcher methodMatcher : methodMatchers){
			if (methodMatcher.getClassMatcher().matches(clazz))
				return methodMatcher;
		}
		return null;
	}
	
	private boolean matchesByMethodMatcher(Method method, Class<?> clazz, Object[] args){
		MethodMatcher methodMatcher = matchesByClassMatcher(clazz);
		if (methodMatcher != null){
			return methodMatcher.getMethodMatcher().matches(method);
		}
		
		return false;
	}
	
	private ShadowMatch getShadowMatch(Method targetMethod, Method originalMethod) {
		synchronized (this.shadowMapCache) {
			ShadowMatch shadowMatch = (ShadowMatch) this.shadowMapCache.get(targetMethod);
			if (shadowMatch == null) {
				try {
					shadowMatch = this.pointcutExpression.matchesMethodExecution(targetMethod);
				}
				catch (ReflectionWorld.ReflectionWorldException ex) {
					// Failed to introspect target method, probably because it has been loaded
					// in a special ClassLoader. Let's try the original method instead...
					if (targetMethod == originalMethod) {
						throw ex;
					}
					shadowMatch = this.pointcutExpression.matchesMethodExecution(originalMethod);
				}
				this.shadowMapCache.put(targetMethod, shadowMatch);
			}
			return shadowMatch;
		}
	}

	
	/**
	 * Check whether this pointcut is ready to match,
	 * lazily building the underlying AspectJ pointcut expression.
	 */
	private void checkReadyToMatch() {
		if (getExpression() == null) {
			throw new IllegalStateException("Must set property 'expression' before attempting to match");
		}
		if (this.pointcutExpression == null) {
			this.pointcutExpression = buildPointcutExpression();
		}
	}
	
	/**
	 * Build the underlying AspectJ pointcut expression.
	 */
	private PointcutExpression buildPointcutExpression() {
		PointcutParameter[] pointcutParameters = new PointcutParameter[this.pointcutParameterNames.length];
		for (int i = 0; i < pointcutParameters.length; i++) {
			pointcutParameters[i] = this.pointcutParser.createPointcutParameter(
					this.pointcutParameterNames[i], this.pointcutParameterTypes[i]);
		}
		return this.pointcutParser.parsePointcutExpression(
				getExpression(), this.pointcutDeclarationScope, pointcutParameters);
	}

	/**
	 * Handler for the Spring-specific <code>bean()</code> pointcut designator
	 * extension to AspectJ.
	 * <p>This handler must be added to each pointcut object that needs to
	 * handle the <code>bean()</code> PCD. Matching context is obtained
	 * automatically by examining a thread local variable and therefore a matching
	 * context need not be set on the pointcut.
	 */
	private class BeanNamePointcutDesignatorHandler implements PointcutDesignatorHandler {

		private static final String MATCHCLASS_DESIGNATOR_NAME = "matchclass";

		public String getDesignatorName() {
			return MATCHCLASS_DESIGNATOR_NAME;
		}

		public ContextBasedMatcher parse(String expression) {
			return new BeanNameContextMatcher(expression);
		}
	}
	
	
	/**
	 * Matcher class for the BeanNamePointcutDesignatorHandler.
	 * 
	 * Dynamic match tests for this matcher always return true, 
	 * since the matching decision is made at the proxy creation time.
	 * For static match tests, this matcher abstains to allow the overall
	 * pointcut to match even when negation is used with the bean() poitncut.
	 */
	private class BeanNameContextMatcher implements ContextBasedMatcher {

		private final String matchClassName;
		private final MethodMatcher matcher;
		private final Matcher<? super Class<?>> classMatcher;
		//private final Matcher<? super Method> methodMatcher;

		public BeanNameContextMatcher(String matchClassName) {
			this.matchClassName = matchClassName;
			this.matcher = getMatcherClass();
			classMatcher = matcher.getClassMatcher();
			//methodMatcher = matcher.getMethodMatcher();
			AspectJExpress.this.addMethodMatcher(matcher);
		}

		public boolean couldMatchJoinPointsInType(Class someClass) {
			return classMatcher.matches(someClass);
		}

		public boolean couldMatchJoinPointsInType(Class someClass, MatchingContext context) {
			return classMatcher.matches(someClass);
			//return contextMatch() == FuzzyBoolean.YES ? true : false;
		}

		public boolean matchesDynamically(MatchingContext context) {
			return true;
		}

		public FuzzyBoolean matchesStatically(MatchingContext context) {
			return contextMatch();
		}

		public boolean mayNeedDynamicTest() {
			return false;
		}

		private FuzzyBoolean contextMatch() {
//			String advisedBeanName = getCurrentProxiedBeanName();
//			if (advisedBeanName == null) { // no proxy creation in progress
//				// abstain; can't return YES, since that will make pointcut with negation fail
//				return FuzzyBoolean.MAYBE; 
//			}
//			if (BeanFactoryUtils.isGeneratedBeanName(advisedBeanName)) {
//				return FuzzyBoolean.NO;
//			}
//			if (this.expressionPattern.matches(advisedBeanName)) {
//				return FuzzyBoolean.YES;
//			}
//			if (beanFactory != null) {
//				String[] aliases = beanFactory.getAliases(advisedBeanName);
//				for (int i = 0; i < aliases.length; i++) {
//					if (this.expressionPattern.matches(aliases[i])) {
//						return FuzzyBoolean.YES;
//					}
//				}
//			}
			return FuzzyBoolean.NO;
		}
		
		private MethodMatcher getMatcherClass(){
			try {
				Class<MethodMatcher> classz = (Class<MethodMatcher>) Class.forName(matchClassName);
				try {
					MethodMatcher matcher = classz.getConstructor(null).newInstance(null);
					return matcher;
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			} catch (ClassNotFoundException e) {
				throw new RuntimeException(e);
			}
		}
	}

}
