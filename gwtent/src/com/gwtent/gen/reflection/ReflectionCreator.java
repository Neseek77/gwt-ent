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
package com.gwtent.gen.reflection;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JRealClassType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

import com.gwtent.client.reflection.PrimitiveTypeImpl;
import com.gwtent.client.reflection.Type;
import com.gwtent.client.test.annotations.Entity;
import com.gwtent.gen.LogableSourceCreator;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class ReflectionCreator extends LogableSourceCreator {

	private final boolean isUseLog = true;
	
	static final String SUFFIX = "__ClassType";

	public ReflectionCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
	}

	protected void createSource(SourceWriter source, JClassType classType) {
		String className = classType.getSimpleSourceName();

		source.println("public "
				+ getSimpleUnitName(classType) + "(){");
		source.indent();
		source.println("super(\"" + classType.getQualifiedSourceName() + "\");");
		source.println("addClassMeta();");
		source.println("addAnnotations();");
		source.println("addFields();");
		source.println("addMethods();");
		source.outdent();
		source.println("}");

		source
				.println("protected void checkInvokeParams(String methodName, int paramCount, Object[] args) throws IllegalArgumentException{");
		source.indent();
		source.println("if (args.length != paramCount){");
		source.indent();
		source
				.println("throw new IllegalArgumentException(\"Method: \" + methodName + \"request \" + paramCount + \" params, but invoke provide \" + args.length + \" params.\");");
		source.outdent();
		source.println("}");
		source.outdent();
		source.println("}");
		source.println();

		JMethod[] methods = classType.getMethods();

		source.println("public Object invoke(Object instance, String methodName, Object[] args) {");
		source.indent();

		source.println(className + " content = (" + className
				+ ")instance;");

		source.println("if (args == null){");
		source.indent();
		source.println("args = new Object[]{};");
		source.outdent();
		source.println("}");

		for (int i = 0; i < methods.length; i++) {
			String methodName = methods[i].getName();
			JParameter[] methodParameters = methods[i].getParameters();
			JType returnType = methods[i].getReturnType();

			source.println("if (methodName.equals(\"" + methodName
					+ "\")) {");
			source.indent();
			source.println("checkInvokeParams(methodName, "
					+ methodParameters.length + ", args);");

			if (!returnType.getSimpleSourceName().equals("void")) {
				source.println("return "
						+ boxIfNeed(returnType.getSimpleSourceName(),
								"content."
										+ methodName
										+ "("
										+ getInvokeParams(
												methodParameters,
												"args") + ")") + ";");
			} else {
				source.println("content." + methodName + "("
						+ getInvokeParams(methodParameters, "args")
						+ ")" + ";");
				source.println("return null;");
			}

			source.outdent();
			source.print("} else ");

		}
		source.println("{");
		source.indent();
		source
				.println("throw new IllegalArgumentException(\"Method: \" + methodName + \" can't found.\");");
		source.outdent(); 
		source.println("}");
		source.outdent();
		source.println("}");
		source.println();

		// -----Add Class MetaData--------------------------------
		addClassMeta(classType, source);
		// -----Add Class Annotation------------------------------------
		addClassAnnotation(classType, source);
		// -----Add fields----------------------------------------
		addFields(classType, source);
		// -----Add methods---------------------------------------
		addMethods(classType, source);
	}

	protected void addClassMeta(JClassType classType, SourceWriter source) {
		source.println();
		source.println("	//add fields");
		source.println();

		source.println("protected void addClassMeta(){");
		source.indent();

		GeneratorHelper.addMetaDatas("this", source, classType);

		source.outdent();
		source.println("}");
	}

	protected void addClassAnnotation(JClassType classType, SourceWriter source) {
		source.println();
		source.println("  //add annotations of class");
		source.println();

		source.println("protected void addAnnotations(){");
		source.indent();

		Annotation[] annotations = AnnotationsHelper
				.getAnnotations((JRealClassType) classType);
		GeneratorHelper.addAnnotations(this.typeOracle, "this", source,
				annotations);

		source.outdent();
		source.println("}");
	}

	protected void addFields(JClassType classType, SourceWriter source) {
		source.println();
		source.println("	//add fields");
		source.println();

		source.println("protected void addFields(){");
		source.indent();

		source.println("FieldImpl field = null;");

		JField[] fields = classType.getFields();

		for (int i = 0; i < fields.length; i++) {
			JField field = fields[i];
			source.println("field = new FieldImpl(this, \"" + field.getName()
					+ "\");");
			source.println("field.addModifierBits("
					+ GeneratorHelper.AccessDefToInt(field) + "); ");
			source.println("field.setTypeName(\""
					+ field.getType().getQualifiedSourceName() + "\");");

			GeneratorHelper.addMetaDatas("field", source, field);

			Annotation[] annotations = AnnotationsHelper.getAnnotations(field);
			GeneratorHelper.addAnnotations(this.typeOracle, "field", source,
					annotations);

			source.println();

		}
		source.outdent();
		source.println("}");
	}

	protected void addMethods(JClassType classType, SourceWriter source) {
		source.println();
		source.println("	//add methods");
		source.println();

		source.println("protected void addMethods(){");
		source.indent();

		source.println("MethodImpl method = null;");

		JMethod[] methods = classType.getMethods();

		for (int i = 0; i < methods.length; i++) {
			JMethod method = methods[i];
			source.println("method = new MethodImpl(this, \"" + method.getName()
					+ "\");");
			source.println("method.addModifierBits("
					+ GeneratorHelper.AccessDefToInt(method) + "); ");
			source.println("method.setReturnTypeName(\""
					+ method.getReturnType().getQualifiedSourceName() + "\");");

			GeneratorHelper.addMetaDatas("method", source, method);
			JParameter[] params = method.getParameters();
			for (int j = 0; j < params.length; j++) {
				JParameter param = params[j];
				source.println("new ParameterImpl(method, \""
						+ param.getType().getQualifiedSourceName() + "\", \""
						+ param.getName() + "\");");
				// TODO Support annotation of Parameter
			}

			Annotation[] annotations = AnnotationsHelper.getAnnotations(method);
			GeneratorHelper.addAnnotations(this.typeOracle, "method", source,
					annotations);

			source.println();

		}

		source.outdent();
		source.println("}");
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
		composer.setSuperclass("com.gwtent.client.reflection.impl.ClassTypeImpl");
		// composer.addImplementedInterface(
		// "com.coceler.gwt.client.reflection.Class");
		composer.addImport("com.gwtent.client.reflection.*");
		composer.addImport("com.gwtent.client.reflection.impl.*");
		composer.addImport("java.util.*");
		composer.addImport(classType.getPackage().getName() + ".*");

		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
	}

	protected Type createTypeByJType(JType jtype) {
		if (jtype instanceof JPrimitiveType) {
			return PrimitiveTypeImpl.valueOf(((JPrimitiveType) jtype)
					.getSimpleSourceName());
		} else if (jtype instanceof JClassType) {

		}
		return null;
	}

	protected String getInvokeParams(JParameter[] methodParams, String argeName) {
		StringBuilder result = new StringBuilder("");
		for (int i = 0; i < methodParams.length; i++) {
			if (i == 0) {
				result.append("("
						+ unboxIfNeed(methodParams[i].getType()
								.getSimpleSourceName(), argeName + "[" + i
								+ "]") + ")");
			}

			if (i != methodParams.length - 1) {
				result.append(", ");
			}
		}
		return result.toString();
	}

	/**
	 * jdk1.4 did support box and unbox, so
	 * 
	 * @param type
	 * @return
	 */
	public String ensureObjectType(String type) {
		if (type.equals("String")) {
			return "String";
		} else if (type.equals("int")) {
			return "Integer";
		} else if (type.equals("byte")) {
			return "Byte";
		}
		if (type.equals("short")) {
			return "Short";
		}
		if (type.equals("long")) {
			return "Long";
		}
		if (type.equals("float")) {
			return "Float";
		}
		if (type.equals("double")) {
			return "Double";
		}
		if (type.equals("boolean")) {
			return "Boolean";
		}
		if (type.equals("char")) {
			return "Character";
		} else {
			return type;
		}
	}

	/**
	 * object type not equals type, that means PrimitiveType
	 * 
	 * @param type
	 * @return
	 */
	public boolean isPrimitiveType(String type) {
		return !(ensureObjectType(type).equals(type));
	}

	/**
	 * 
	 * @param requestType
	 * @param argeName
	 * @return
	 */
	public String unboxIfNeed(String requestType, String argeName) {
		if (!isPrimitiveType(requestType)) {
			return "(" + requestType + ")" + argeName;
		} else if (requestType.equals("Integer")) {
			return "((Integer)" + argeName + ").intValue()";
		} else if (requestType.equals("Byte")) {
			return "((Byte)" + argeName + ").byteValue()";
		}
		if (requestType.equals("Short")) {
			return "((Short)" + argeName + ").shortValue()";
		}
		if (requestType.equals("long")) {
			return "((Byte)" + argeName + ").byteValue()";
		}
		if (requestType.equals("float")) {
			return "((Long)" + argeName + ").longValue()";
		}
		if (requestType.equals("double")) {
			return "((Double)" + argeName + ").doubleValue()";
		}
		if (requestType.equals("boolean")) {
			return "((Boolean)" + argeName + ").booleanValue()";
		}
		if (requestType.equals("char")) {
			return "((Character)" + argeName + ").charValue()";
		} else {
			return "(" + requestType + ")" + argeName;
		}
	}

	/**
	 * Method invoke return an Object, so this auto box
	 * 
	 * @param requestType
	 * @param argeName
	 * @return
	 */
	public String boxIfNeed(String requestType, String argeName) {
		if (!isPrimitiveType(requestType)) {
			// return "(" + requestType + ")" + argeName;
			// Change to Object to avoid import problem
			return "(Object)" + argeName;
		} else if (requestType.equals("integer")) {
			return "Integer.valueOf(" + argeName + ")";
		} else if (requestType.equals("Byte")) {
			return "Byte.valueOf(" + argeName + ")";
		}
		if (requestType.equals("Short")) {
			return "Short.valueOf(" + argeName + ")";
		}
		if (requestType.equals("long")) {
			return "Byte.valueOf(" + argeName + ")";
		}
		if (requestType.equals("float")) {
			return "Long.valueOf(" + argeName + ")";
		}
		if (requestType.equals("double")) {
			return "Double.valueOf(" + argeName + ")";
		}
		if (requestType.equals("boolean")) {
			return "Boolean.valueOf(" + argeName + ")";
		}
		if (requestType.equals("char")) {
			return "Character.valueOf(" + argeName + ")";
		} else {
			return "(" + requestType + ")" + argeName;
		}
	}

	@Override
	protected String getSUFFIX() {
		return SUFFIX;
	}
	
}
