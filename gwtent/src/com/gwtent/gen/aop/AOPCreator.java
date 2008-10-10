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

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
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

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.PrimitiveType;
import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.reflection.Type;
import com.gwtent.client.test.TestReflection;
import com.gwtent.client.test.annotations.Entity;
import com.gwtent.gen.LogableSourceCreator;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;

public class AOPCreator extends LogableSourceCreator {

	private final boolean isUseLog = true;
	
	static final String SUFFIX = "__AOP";

	public AOPCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
		
//		PropertyOracle propertyOracle = context.getPropertyOracle();
//		try {
//			String matcher = propertyOracle.getPropertyValue(logger, "aop.matcher.test");
//			System.out.println(matcher);
//		} catch (BadPropertyValueException e) {
//			e.printStackTrace();
//		}
	}

	public String createWrapper() {
		try {
			JClassType classType = typeOracle.getType(typeName);
			SourceWriter source = getSourceWriter(classType, isUseLog, 6);

			if (source == null) {
				return classType.getParameterizedQualifiedSourceName()
						+ SUFFIX;
			} else {
				String className = classType.getSimpleSourceName();
				source.indent();

				// source.print("public ");
				source.println("public " + getSimpleUnitName(classType) + "(){");
				source.indent();
				source.println("makeSureCreateClassType();");
				//source.println("addAnnotations();");
				//source.println("addFields();");
				//source.println("addMethods();");
				source.outdent();
				source.println("}");

				String reflectionClassName = getSimpleUnitName(classType) + "_";
				source.println("public static class " + reflectionClassName + " extends " + classType.getSimpleSourceName() + " implements Reflection {");
				source.println("}");
				
				source.println("public void makeSureCreateClassType() {");
				source.indent();
				//source.println("ClassType type = (ClassType)GWT.create(TestReflection.class);");
				//source.println("ClassType type = (ClassType)GWT.create(" + reflectionClassName + ".class);");
				source.outdent();
				source.println("}");
				
				source.outdent();
				source.commit(logger);
				return getUnitName(classType);
			}
		} catch (NotFoundException e) {
			e.printStackTrace();
			return null;
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

	protected Type createTypeByJType(JType jtype) {
		if (jtype instanceof JPrimitiveType) {
			return PrimitiveType.valueOf(((JPrimitiveType) jtype)
					.getSimpleSourceName());
		} else if (jtype instanceof JClassType) {

		}
		return null;
	}

	@Override
	protected String getSUFFIX() {
		return SUFFIX;
	}
	

}
