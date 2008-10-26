/*
 * Copyright 2007 JamesLuo.au@gmail.com
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtent.gen.aop;

import java.io.PrintWriter;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.client.aop.AOPRegistor;
import com.gwtent.gen.LogableSourceCreator;

public class AOPCreator extends LogableSourceCreator {

	private final boolean isUseLog = true;
	
	static final String SUFFIX = "__AOP";
	
	private Map<Method, List<String>> interceptMethods = new HashMap<Method, List<String>>();

	public AOPCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
		
		AsyncRegistor();
		
//		PropertyOracle propertyOracle = context.getPropertyOracle();
//		try {
//			String matcher = propertyOracle.getPropertyValue(logger, "aop.matcher.test");
//			System.out.println(matcher);
//		} catch (BadPropertyValueException e) {
//			e.printStackTrace();
//		}
	}

	protected void createSource(SourceWriter source, JClassType classType){
		processMethods(classType);
		
		createInterceptorMap(source);
		
		source.println("private static final ClassType classType = TypeOracle.Instance.getClassType(" + classType.getSimpleSourceName() + ".class);");
		source.println();
		
		declareMethodInvocation(source);
		
		source.println();
		source.println("public " + getSimpleUnitName(classType) + "(){");
		source.indent();
		
		createMethodInvocation(source);
		
		source.outdent();
		source.println("}");
		
	}
	
	private void createInterceptorMap(SourceWriter source){
		source.indent();
		source.println("");
		source.println("private static class InterceptorMap{");
		source.indent();
		source.println("static Map<Method, String> interceptors = new HashMap<Method, String>();");
		source.println("static List<String> matcherClassNames = null;");
		
		source.println("static {");
		
		source.indent();
		
		
		for(Method method : interceptMethods.keySet()){
			source.println("matcherClassNames = new ArrayList<String>();");
			List<String> matcherClassNames = interceptMethods.get(method);
			for (String className : matcherClassNames){
				source.println("matcherClassNames.add(\"" + className + "\");");
			}			
			source.println("interceptors.put(classType.findMethod(\"" + method.getName() + "\", " + getParamAsSourceCode(method) + "), matcherClassNames);");
		}
		source.println("}");
		
		source.outdent();
		source.println("}");
		source.outdent();
		source.outdent();
	}
	
	private void declareMethodInvocation(SourceWriter source){
		for (Method method : interceptMethods.keySet()) {
			source.println("private final MethodInvocationLinkedAdapter Ivn_" + MethodNameProvider.getName(method));
		}
	}
	
	private void createMethodInvocation(SourceWriter source){
		for (Method method : interceptMethods.keySet()) {
			source.println("{");
			
			source.println("Method method = classType.findMethod(\"" + method.getName() + "\", " + getParamAsSourceCode(method) + ");");
			source.println("List<MethodInterceptor> interceptors = new ArrayList<MethodInterceptor>();");
			source.println("for (String matcherClassName : InterceptorMap.interceptors.get(method)){");
			source.println("interceptors.addAll(AOPRegistor.getInstance().getInterceptors(matcherClassName));");
			source.println("}");
			source.println("interceptors.add(new MethodInterceptorFinalAdapter());");
			source.println("Ivn_" + MethodNameProvider.getName(method) + " = new MethodInvocationLinkedAdapter(method, this, interceptors);");
			source.println("}");
		}
	}
	
	private void processMethods(JClassType classType){
		MatcherQuery query = BindRegistry.getInstance();
		Class<?> classz = null;
		try {
			String jniSig = classType.getJNISignature();
      jniSig = jniSig.substring(1, jniSig.length() - 1);
      String className = jniSig.replace('/', '.');
			classz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		if ((classz != null) & (query.matches(classz))){
			Method[] methods = classz.getMethods();
			for (int i = 0; i < methods.length; i++) {
				Method method = methods[i];
				
				List<String> matcherClassNames = query.matches(method);
				if (matcherClassNames.size() > 0){
					interceptMethods.put(method, matcherClassNames);
				}
			}
		}
	}

	/**
	 * SourceWriter instantiation. Return null if the resource already exist.
	 * 
	 * @return sourceWriter
	 */
	public SourceWriter doGetSourceWriter(JClassType classType) {
		String packageName = classType.getPackage().getName();
		String simpleName = getSimpleUnitName(classType);
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				packageName, simpleName);
		composer.setSuperclass(classType.getQualifiedSourceName());
		// composer.addImplementedInterface(
		// "com.coceler.gwt.client.reflection.Class");
		composer.addImport(classType.getQualifiedSourceName());
		composer.addImport("com.google.gwt.core.client.*");
		composer.addImport("com.gwtent.client.reflection.*");
		composer.addImport("java.util.*");
		composer.addImport(classType.getPackage().getName() + ".*");

		PrintWriter printWriter = context.tryCreate(logger, packageName,
				simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
		
	}

	@Override
	protected String getSUFFIX() {
		return SUFFIX;
	}
	
	/**
	 * We just Async if nothing in BindRegistry.
	 * We supposed everything should be there when Code Generator started
	 */
	protected void AsyncRegistor(){
		if (AOPRegistor.getInstance().getMatcherClassNames().size() > 0){
			if (BindRegistry.getInstance().size() == 0){
				for (String matcherClassName : AOPRegistor.getInstance().getMatcherClassNames()) {
					BindRegistry.getInstance().bindInterceptor(matcherClassName);
				}
			}
		}
	}
	
	private String getParamAsSourceCode(Method method){
		String result = "new String[]{";
		
		boolean needComma = false;
		for (String type : getParamAsString(method)){
			type = "\"" + type + "\"";
			if (needComma)
				result = result + ", " + type;
			else
				result = result + type;
		}
		
		result = result + "}";
		return result;
	}
	
	private String[] getParamAsString(Method method){
		List<String> result = new ArrayList<String>();
		for (Class<?> clasz : method.getParameterTypes()){
			result.add(clasz.getCanonicalName());
		}
		return result.toArray(new String[]{});
	}

	
	private static class MethodNameProvider {
		private static Map<Method, String> names = new HashMap<Method, String>();
		
		private static String findNextName(Method method){
			String result = method.getName();
			Integer i = 0;
			
			while (names.containsValue(result)) {
				result = method.getName() + i.toString();
			}
			return result;
		}
		
		static String getName(Method method){
			String result = names.get(method);
			if (result == null){
				result = findNextName(method);
				names.put(method, result);
			}
			
			return result;
		}
	}

}
