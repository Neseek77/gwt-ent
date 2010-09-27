/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/

package com.gwtent.gen.reflection;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.HasAnnotations;
import com.google.gwt.core.ext.typeinfo.JAnnotationMethod;
import com.google.gwt.core.ext.typeinfo.JAnnotationType;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.core.ext.typeinfo.JTypeParameter;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.common.client.CheckedExceptionWrapper;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenUtils;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.reflection.client.HasReflect;
import com.gwtent.reflection.client.Reflectable;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.reflection.client.Type;
import com.gwtent.reflection.client.impl.AnnotationTypeImpl;
import com.gwtent.reflection.client.impl.PrimitiveTypeImpl;

public class ReflectionCreator extends LogableSourceCreator {

	private final boolean isUseLog = true;

	static final String SUFFIX = "__Reflection";

	private JClassType reflectionType = null;

	public ReflectionCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
	}

	public static class ReflectionSourceCreator {
		private final String className;
		private final SourceWriter sourceWriter;
		private final JClassType classType;
		private final com.google.gwt.core.ext.typeinfo.TypeOracle typeOracle;
		private final Reflectable reflectable;
		private final TreeLogger logger;
		private List<JClassType> candidateList = new ArrayList<JClassType>();
		private Map<JClassType, Reflectable> candidates = new HashMap<JClassType, Reflectable>();
		private Set<JClassType> relationClassesProcessed = new HashSet<JClassType>();

		public ReflectionSourceCreator(String className, JClassType classType,
				SourceWriter sourceWriter,
				com.google.gwt.core.ext.typeinfo.TypeOracle typeOracle,
				TreeLogger logger, Reflectable reflectable) {
			this.className = className;
			this.sourceWriter = sourceWriter;
			this.classType = classType;
			this.typeOracle = typeOracle;
			this.logger = logger;
			this.reflectable = reflectable;
		}

		private String getClassInterface(JClassType type) {
			if (type instanceof JTypeParameter) {
				JTypeParameter typep = (JTypeParameter) type;
				type = typep.getBaseType();
			}
			String className = type.getPackage().getName().replace('.', '_')
					+ '_' + getSimpleUnitNameWithOutSuffix(type)
					+ "_GWTENTAUTO_ClassType"; // type.getPackage().getName().replace('.',
			// '_') + '_' +
			// type.getSimpleSourceName().replace('.',
			// '_');
			// //getSimpleUnitName(type);

			return className;

		}

		public void createSource() {
			String simpleUnitName = getSimpleUnitNameWithOutSuffix(classType);

			sourceWriter.println(" private static boolean inited = false;");
			sourceWriter
					.println(" protected static ClassType<?> instance = null;");

			sourceWriter.println("public " + className + "() {");
			sourceWriter.println("	if (!inited) {");
			sourceWriter.println("		inited = true;");
			sourceWriter.println("		instance =");
			sourceWriter.println("		   new " + simpleUnitName
					+ "___ClassImpl();");
			sourceWriter.println("	} ");
			sourceWriter.println("	classType = instance;");
			sourceWriter.println("}");

			sourceWriter.println("public static class " + simpleUnitName
					+ "___ClassImpl ");
			JClassType refType = classType;

			if (refType.isAnnotation() != null) {
				sourceWriter.println(" extends "
						+ AnnotationTypeImpl.class.getName() + "<"
						+ refType.getQualifiedSourceName() + "> {");

			} else if (refType.isEnum() == null) {
				sourceWriter
						.println(" extends com.gwtent.reflection.client.impl.ClassTypeImpl<"
								+ refType.getQualifiedSourceName() + "> {");

			} else {
				sourceWriter
						.println(" extends com.gwtent.reflection.client.impl.EnumTypeImpl<"
								+ refType.getQualifiedSourceName() + ">{");
			}

			sourceWriter.indent();
			createInnerClass();
			sourceWriter.outdent();
			sourceWriter.println("}");
		}

		public void createInnerClass() {

			// if (classType.isAnnotation() != null){
			// createAnnotationImpl(classType.isAnnotation());
			// }
			// 创建用到的类的接口
			createRelInterface();

			if (classType.isAnnotation() != null) {
				createAnnotationImpl(this.sourceWriter, classType
						.isAnnotation());

			}

			// constructor
			sourceWriter.println("public "
					+ getSimpleUnitNameWithOutSuffix(classType)
					+ "___ClassImpl(){");
			sourceWriter.indent();
			// sourceWriter.println("super(\"" +
			// classType.getQualifiedSourceName() +
			// "\");");
			// sourceWriter.println("super(" +
			// classType.getQualifiedSourceName() +
			// ".class);");
			sourceWriter.println("super(" + classType.getQualifiedSourceName()
					+ ".class);");
			// sourceWriter.println("addClassMeta();");
			sourceWriter.println("addAnnotations();");
			sourceWriter.println("addFields();");
			sourceWriter.println("addMethods();");

			if (this.reflectable.constructors()) {
				if ((classType.isClass() != null)
						&& GenUtils.hasPublicDefaultConstructor(classType)) {
					if ((!classType.isAbstract())
							&& (classType.isDefaultInstantiable())) {
						sourceWriter.println("new ConstructorImpl(this){");
						sourceWriter
								.println("	public java.lang.Object newInstance() {");
						sourceWriter.println("return new "
								+ classType.getQualifiedSourceName() + "();");
						// sourceWriter.println("		return GWT.create(" +
						// classType.getQualifiedSourceName() + ".class);");
						sourceWriter.println("	}");
						sourceWriter.println("};");
					}
				}
			}

			sourceWriter.println("");

			if (classType.getSuperclass() != null
					&& classType.getSuperclass().isPublic()) {
				sourceWriter.println("  setSuperclassName(\""
						+ classType.getSuperclass().getQualifiedSourceName()
						+ "\");");
				if (reflectable.superClasses()) {
					// sourceWriter.println("if (" + "TypeOracleImpl.findType("
					// +
					// classType.getSuperclass().getQualifiedSourceName() +
					// ".class)" + " != null)");
					String classInterface = getClassInterface(classType
							.getSuperclass());
					if (classType.getSuperclass().isParameterized() != null) {
						// new String[]{java.lang.String, java.lang.Integer}
						JClassType[] paramTypeArgs = classType.getSuperclass()
								.isParameterized().getTypeArgs();
						String actArgs = GeneratorHelper
								.stringArrayToCode(GeneratorHelper
										.convertJClassTypeToStringArray(paramTypeArgs));

						StringBuffer actualTypeString = new StringBuffer();
						actualTypeString.append("    new ClassType[]{");
						for (int i = 0; i < paramTypeArgs.length; i++) {
							actualTypeString.append("(ClassType)GWT.create("
									+ getClassInterface(paramTypeArgs[i])
									+ ".class), ");
						}
						actualTypeString.append("    }");
						sourceWriter
								.println("  setSuperclass(new ParameterizedTypeImpl(\""
										+ classType.getSuperclass()
												.getQualifiedSourceName()
										+ "\", (ClassType)GWT.create("
										+ classInterface
										+ ".class), "

										+ actArgs
										+ ","
										+ actualTypeString
										+ "));");
					} else {

						sourceWriter
								.println("  setSuperclass((ClassType)GWT.create("
										+ classInterface + ".class));");
						// sourceWriter.println("  setSuperclass(\"在这里创建类sxf\");");
					}
				}
			}

			sourceWriter.println();
			if (reflectable.relationTypes()) {
				for (JClassType type : classType.getImplementedInterfaces()) {
					if (type.isParameterized() != null) {
						String actArgs = GeneratorHelper
								.stringArrayToCode(GeneratorHelper
										.convertJClassTypeToStringArray(type
												.isParameterized()
												.getTypeArgs()));

						JClassType[] paramTypeArgs = type.isParameterized()
								.getTypeArgs();
						StringBuffer actualTypeString = new StringBuffer();
						actualTypeString.append("    new ClassType[]{");
						for (int i = 0; i < paramTypeArgs.length; i++) {
							actualTypeString.append("(ClassType)GWT.create("
									+ getClassInterface(paramTypeArgs[i])
									+ ".class), ");
						}
						actualTypeString.append("    }");
						sourceWriter.println("addImplementedInterface(\""
								+ type.getQualifiedSourceName()
								+ "\", "
								+ TypeHelper.getClassTypeCode(type
										.getQualifiedSourceName()) + ","

								+ actArgs + "," + actualTypeString + ");");
					} else {
						sourceWriter.println("addImplementedInterface("
								+ TypeHelper.getClassTypeCode(type
										.getQualifiedSourceName()) + " );");
					}
				}
			}
			sourceWriter.outdent();
			sourceWriter.println("}");

			// sourceWriter
			// .println(
			// "protected void checkInvokeParams(String methodName, int paramCount, Object[] args) throws IllegalArgumentException{"
			// );
			// sourceWriter.indent();
			// sourceWriter.println("if (args.length != paramCount){");
			// sourceWriter.indent();
			// sourceWriter
			// .println(
			// "throw new IllegalArgumentException(\"Method: \" + methodName + \"request \" + paramCount + \" params, but invoke provide \" + args.length + \" params.\");"
			// );
			// sourceWriter.outdent();
			// sourceWriter.println("}");
			// sourceWriter.outdent();
			// sourceWriter.println("}");
			sourceWriter.println();

			invoke(classType);
			sourceWriter.println();

			// -----Add Class MetaData--------------------------------
			// addClassMeta(classType, sourceWriter);
			// -----Add Class Annotation------------------------------------
			addClassAnnotation(classType, sourceWriter);
			// -----Add fields----------------------------------------
			addFields(classType, sourceWriter);
			// -----Add methods---------------------------------------
			addMethods(classType, sourceWriter);

			setFieldValue(classType, sourceWriter);

			getFieldValue(classType, sourceWriter);
		}

		protected String getSimpleUnitNameWithOutSuffix(JClassType classType) {
			return classType.getName().replace('.', '_');
		}

		// sxf copy from ReflectAllInOneCreator
		private void processAnnotationClasses(HasAnnotations annotations,
				Reflectable reflectable) {
			if (!reflectable.classAnnotations())
				return;

			Annotation[] annos = AnnotationsHelper.getAnnotations(annotations);
			if (annos == null)
				return;

			for (Annotation annotation : annos) {
				processAnnotation(annotation);
			}
		}

		// sxf copy from ReflectAllInOneCreator
		private boolean addClassIfNotExists(JClassType classType,
				Reflectable setting) {
			// Add next line we can make sure we just append normal class type,
			// always get from TypeOracle
			// not JParameterizedType or JTypeParameter etc...
			// RC2 we support ParameterizedType now.
			if (classType != null && classType.isParameterized() == null) {
				// System.out.println("addClassIfNotExists: " +
				// classType.getQualifiedSourceName());
				classType = this.typeOracle.findType(classType
						.getQualifiedSourceName());

			}

			// we just process public classes
			if ((classType == null)
					|| (classType.isPrivate())
					|| (classType.isProtected())
					|| (GeneratorHelper.isSystemClass(classType) && !classType
							.isPublic()))
				return false;

			// no need java.lang.class
			if (classType.getQualifiedSourceName().equals("java.lang.Class"))
				return false;

			if (candidateList.indexOf(classType.getErasedType()) < 0) {
				candidateList.add(classType.getErasedType());
				candidates.put(classType.getErasedType(), setting);
				return true;
			}

			return false;
		}

		// sxf copy from ReflectAllInOneCreator
		private Reflectable getNearestSetting(JClassType classType,
				Reflectable defaultSetting) {
			Reflectable result = GenUtils
					.getClassTypeAnnotationWithMataAnnotation(classType,
							Reflectable.class);
			if (result != null)
				return result;
			else
				return defaultSetting;
		}

		// sxf copy from ReflectAllInOneCreator
		private void processRelationClass(JClassType classType,
				Reflectable reflectable) {
			Reflectable nearest = getNearestSetting(classType, reflectable);
			processRelationClasses(classType, nearest);
			processAnnotationClasses(classType, nearest);
			addClassIfNotExists(classType, nearest);
		}

		// sxf copy from ReflectAllInOneCreator
		private void processMethods(JClassType classType,
				Reflectable reflectable) {
			boolean need = reflectable.relationTypes();
			for (JMethod method : classType.getMethods()) {
				if (reflectable.fieldAnnotations()
						|| (hasReflectionAnnotation(method))) {
					processAnnotationClasses(method, reflectable);

					HasReflect hasReflect = method
							.getAnnotation(HasReflect.class);
					JClassType type = null;

					if (need || (hasReflect != null && hasReflect.resultType())) {
						if (method.getReturnType() != null
								&& method.getReturnType().isArray() != null) {
							JArrayType array = method.getReturnType().isArray();
							JType componentType = array.getComponentType();
							if (componentType.isClassOrInterface() != null) {
								type = componentType.isClassOrInterface();
							}
							processRelationClasses(type, reflectable);

							addClassIfNotExists(type, reflectable);

						}
						if (method.getReturnType() != null
								&& method.getReturnType().isClassOrInterface() != null) {
							type = method.getReturnType().isClassOrInterface();

							if (!type.isAssignableTo(classType))
								processRelationClasses(type, reflectable);

							addClassIfNotExists(type, reflectable);
						}
					}

					if (need
							|| (hasReflect != null && hasReflect
									.parameterTypes())) {
						for (JParameter parameter : method.getParameters()) {
							if (parameter.getType() != null
									&& parameter.getType().isClassOrInterface() != null) {
								type = parameter.getType().isClassOrInterface();

								if (!type.isAssignableTo(classType))
									processRelationClasses(type, reflectable);

								addClassIfNotExists(type, reflectable);
							}
						}
					}
				}
			}
		}

		// sxf copy from ReflectAllInOneCreator
		private boolean hasReflection(HasAnnotations type) {
			return type.getAnnotation(HasReflect.class) != null;
		}

		// sxf copy from ReflectAllInOneCreator
		private boolean hasReflectionAnnotation(HasAnnotations type) {
			return (type.getAnnotation(HasReflect.class) != null)
					&& type.getAnnotation(HasReflect.class).annotation();
		}

		// sxf copy from ReflectAllInOneCreator
		private void processFields(JClassType classType, Reflectable reflectable) {
			boolean need = reflectable.relationTypes();

			for (JField field : classType.getFields()) {
				if (reflectable.fieldAnnotations()
						|| (hasReflectionAnnotation(field))) {
					// 不知道为什么，在Object中，会多出一个这样的属性，类型是javascriptObject
					if (classType.getQualifiedSourceName().equals(
							Object.class.getName())
							&& (field.getName().equals("expando") || field
									.getName().equals("typeMarker"))) {
						continue;
					}
					processAnnotationClasses(field, reflectable);

					JClassType type = field.getType().isClassOrInterface();
					if (type != null)
						if (need
								|| (hasReflection(field) && field
										.getAnnotation(HasReflect.class)
										.fieldType()))
							if (!type.isAssignableTo(classType)) // some times,
								// it's
								// itself of
								// devided
								// class
								processRelationClasses(type, reflectable);

					addClassIfNotExists(type, reflectable);
				}

			}
		}

		// sxf copy from ReflectAllInOneCreator
		private void processRelationClasses(JClassType classType,
				Reflectable reflectable) {
			if (classType == null)
				return;

			if (classType.isParameterized() != null)
				classType = classType.isParameterized().getBaseType();

			if (classType.isRawType() != null || classType.isWildcard() != null
					|| classType.isTypeParameter() != null)
				classType = classType.getErasedType();

			if (relationClassesProcessed.contains(classType))
				return;

			processAnnotationClasses(classType, reflectable);

			if (reflectable.superClasses()) {
				if (classType.getSuperclass() != null) {
					processRelationClass(classType.getSuperclass(), reflectable);
				}
			}

			if (reflectable.relationTypes()) {
				for (JClassType type : classType.getImplementedInterfaces()) {
					processRelationClass(type, reflectable);
				}
			}

			relationClassesProcessed.add(classType);

			processFields(classType, reflectable);

			processMethods(classType, reflectable);
		}

		// sxf copy from ReflectAllInOneCreator
		private void processClass(Class<?> clazz, Reflectable reflectable) {
			processClass(typeOracle.findType(ReflectionUtils
					.getQualifiedSourceName(clazz)), reflectable);
		}

		// sxf copy from ReflectAllInOneCreator
		protected GenExclusion getGenExclusion() {
			return GenExclusionCompositeReflection.INSTANCE;
		}

		// sxf copy from ReflectAllInOneCreator
		protected boolean genExclusion(JClassType classType) {
			if (getGenExclusion() != null) {
				return getGenExclusion().exclude(classType);
			} else
				return false;
		}

		// sxf copy from ReflectAllInOneCreator
		private void processClass(JClassType classType, Reflectable reflectable) {
			if (!genExclusion(classType)) {
				// if (addClassIfNotExists(classType, reflectable)) {//不需要增加自己了
				processRelationClasses(classType, reflectable);
				processAnnotationClasses(classType, reflectable);
				// }
			}
		}

		// sxf copy from ReflectAllInOneCreator
		private Reflectable getNearestSetting(Class<?> clazz,
				Reflectable defaultSetting) {
			return getNearestSetting(typeOracle.findType(ReflectionUtils
					.getQualifiedSourceName(clazz)), defaultSetting);
		}

		// sxf copy from ReflectAllInOneCreator
		private Reflectable getFullSettings() {
			return ReflectableHelper.getFullSettings(typeOracle);
		}

		// sxf copy from ReflectAllInOneCreator
		private void processClassFromAnnotationValue(Object value) {
			if (value != null && value instanceof Class
					&& (!((Class) value).getName().equals("void"))) {
				processClass((Class) value, getNearestSetting((Class) value,
						getFullSettings()));
			}
		}

		// sxf copy from ReflectAllInOneCreator
		private void processJType(JType type) {
			JClassType classType = null;
			if (type.isClassOrInterface() != null) {
				classType = type.isClassOrInterface();
			} else if (type.isArray() != null) {
				processJType(type.isArray().getComponentType());
			} else if (type.isAnnotation() != null) {
				classType = type.isAnnotation();
			}

			if (classType != null)
				processClass(classType, getNearestSetting(classType,
						getFullSettings()));
		}

		// sxf copy from ReflectAllInOneCreator
		private void processAnnotation(Annotation annotation) {
			if (annotation.annotationType().getName().startsWith(
					"java.lang.annotation")) {
				return; // Document's parent is itself, must check here
			} else {
				JClassType classType = this.typeOracle.findType(ReflectionUtils
						.getQualifiedSourceName(annotation.annotationType()));

				if (classType == null)
					return; //

				addClassIfNotExists(classType, getNearestSetting(classType,
						getFullSettings()));

				// Go through all annotation methods, if has class, add that
				// class to reflection as well
				JAnnotationType annoType = classType.isAnnotation();
				JAnnotationMethod[] methods = annoType.getMethods();
				for (JAnnotationMethod method : methods) {
					Object value = null;
					try {
						value = annotation.annotationType().getMethod(
								method.getName(), new Class[] {}).invoke(
								annotation, null);
						// System.out.println(value);
						// System.out.println(value.getClass());
						if (value instanceof Class) {
							processClassFromAnnotationValue(value);
						} else if (value.getClass().isArray()) {
							for (int i = 0; i < Array.getLength(value); i++) {
								if (Array.get(value, i) instanceof Class)
									processClassFromAnnotationValue(Array.get(
											value, i));
							}
						} else if (value instanceof Annotation) {
							processAnnotation((Annotation) value);
						}
					} catch (Exception e) {
						throw new CheckedExceptionWrapper(e);
					}

					if (method.getReturnType() != null) {
						JType type = method.getReturnType();
						processJType(type);

					}
				}

				Class<? extends Annotation> annotationType = annotation
						.annotationType();
				Annotation[] metaAnnotations = annotationType.getAnnotations();
				for (Annotation metaAnnotation : metaAnnotations) {
					processAnnotation(metaAnnotation);
				}
			}
		}

		// sxf
		private void createRelInterface() {

			Reflectable reflectable = GenUtils
					.getClassTypeAnnotationWithMataAnnotation(classType,
							Reflectable.class);
			if (reflectable == null) {
				reflectable = ReflectableHelper.getFullSettings(typeOracle);
			}
			try {
				addClassIfNotExists(typeOracle.getType(Retention.class
						.getCanonicalName()), ReflectableHelper
						.getDefaultSettings(typeOracle));
				addClassIfNotExists(typeOracle.getType(Documented.class
						.getCanonicalName()), ReflectableHelper
						.getDefaultSettings(typeOracle));
				addClassIfNotExists(typeOracle.getType(Inherited.class
						.getCanonicalName()), ReflectableHelper
						.getDefaultSettings(typeOracle));
				addClassIfNotExists(typeOracle.getType(Target.class
						.getCanonicalName()), ReflectableHelper
						.getDefaultSettings(typeOracle));
				addClassIfNotExists(typeOracle.getType(Deprecated.class
						.getCanonicalName()), ReflectableHelper
						.getDefaultSettings(typeOracle));
			} catch (NotFoundException e) {
				throw new RuntimeException(e);
			}

			processClass(classType, reflectable);
			for (JClassType type : candidateList) {
				String className = type.getPackage().getName()
						.replace('.', '_')
						+ '_'
						+ getSimpleUnitNameWithOutSuffix(type)
						+ "_GWTENTAUTO_ClassType"; // type.getPackage().getName().replace('.',
				// '_') + '_' +
				// type.getSimpleSourceName().replace('.',
				// '_');
				// //getSimpleUnitName(type);

				sourceWriter.println("@ReflectionTarget(value=\""
						+ type.getQualifiedSourceName() + "\")");
				sourceWriter.println("public static interface " + className
						+ " extends com.gwtent.reflection.client.ClassType {}");
			}

		}

		private void invoke(JClassType classType) {
			JMethod[] methods = classType.getMethods();

			sourceWriter
					.println("public java.lang.Object invoke(java.lang.Object instance, String methodName, Object[] args) throws MethodInvokeException {");
			sourceWriter.indent();

			sourceWriter.println(classType.getQualifiedSourceName()
					+ " content = (" + classType.getQualifiedSourceName()
					+ ")instance;");

			sourceWriter.println("if (args == null){");
			sourceWriter.indent();
			sourceWriter.println("args = new Object[]{};");
			sourceWriter.outdent();
			sourceWriter.println("}");

			for (JMethod method : methods) {
				if (!method.isPublic())
					continue;

				if (!this.reflectable.methods()) {
					HasReflect hasReflect = method
							.getAnnotation(HasReflect.class);
					if (hasReflect == null)
						continue;
				}

				String methodName = method.getName();
				JParameter[] methodParameters = method.getParameters();
				JType returnType = method.getReturnType();

				sourceWriter.println("if (methodName.equals(\"" + methodName
						+ "\")) {");
				sourceWriter.indent();
				sourceWriter.println("checkInvokeParams(methodName, "
						+ methodParameters.length + ", args);");

				if (needCatchException(method)) {
					sourceWriter.println("try{");
					sourceWriter.indent();
				}

				if (!returnType.getSimpleSourceName().equals("void")) {
					sourceWriter.println("return "
							+ boxIfNeed(returnType.getQualifiedSourceName(),
									"content."
											+ methodName
											+ "("
											+ getInvokeParams(methodParameters,
													"args") + ")") + ";");
				} else {
					sourceWriter.println("content." + methodName + "("
							+ getInvokeParams(methodParameters, "args") + ")"
							+ ";");
					sourceWriter.println("return null;");
				}

				if (needCatchException(method)) {
					sourceWriter.println("}catch (Throwable e){");
					sourceWriter.println("throw new MethodInvokeException(e);");
					sourceWriter.println("}");
					sourceWriter.outdent();
				}

				sourceWriter.outdent();
				sourceWriter.print("} else ");

			}
			sourceWriter
					.println("return super.invoke(instance, methodName, args);");
			// sourceWriter.println("{");
			// sourceWriter.indent();
			// sourceWriter
			// .println(
			// "throw new IllegalArgumentException(\"Method: \" + methodName + \" can't found.\");"
			// );
			// sourceWriter.outdent();
			// sourceWriter.println("}");
			sourceWriter.outdent();
			sourceWriter.println("}");
		}

		// protected void addClassMeta(JClassType classType, SourceWriter
		// source) {
		// source.println();
		//
		// source.println("protected void addClassMeta(){");
		// source.indent();
		//
		// GeneratorHelper.addMetaDatas("this", source, classType);
		//
		// source.outdent();
		// source.println("}");
		// }

		protected void addClassAnnotation(JClassType classType,
				SourceWriter source) {
			source.println();

			source.println("protected void addAnnotations(){");
			source.indent();

			if (this.reflectable.classAnnotations()) {
				Annotation[] annotations = AnnotationsHelper
						.getAnnotations(classType);
				GeneratorHelper.addAnnotations_AnnotationImpl(this.typeOracle,
						"this", source, annotations, logger);
			}

			source.outdent();
			source.println("}");
		}

		protected void addFields(JClassType classType, SourceWriter source) {
			source.println();

			source.println("protected void addFields(){");
			source.indent();

			boolean needReflect = this.reflectable.fields();
			source.println("FieldImpl field = null;");

			JField[] fields = classType.getFields();

			for (int i = 0; i < fields.length; i++) {
				JField field = fields[i];
				// 不知道为什么，在Object中，会多出一个这样的属性，类型是javascriptObject
				if (classType.getQualifiedSourceName().equals(
						Object.class.getName())
						&& (field.getName().equals("expando") || field
								.getName().equals("typeMarker"))) {
					continue;
				}
				if (needReflect
						|| field.getAnnotation(HasReflect.class) != null) {
					if (field.isEnumConstant() == null)
						source.println("field = new FieldImpl(this, \""
								+ field.getName() + "\");");
					else
						source.println("field = new EnumConstantImpl(this, \""
								+ field.getName() + "\", "
								+ field.isEnumConstant().getOrdinal() + ");");

					source.println("field.addModifierBits("
							+ GeneratorHelper.AccessDefToInt(field) + "); ");
					String fieldType = field.getType().getQualifiedSourceName();
					source.println("field.setTypeName(\"" + fieldType + "\");");
					if (reflectable.relationTypes()) {
						StringBuffer classTypeCode = TypeHelper
								.getClassTypeCode(field.getType()
										.getQualifiedSourceName());
						source
								.println("field.setType(" + classTypeCode
										+ "); ");
						// GeneratorHelper.addMetaDatas("field", source, field);
					}
					if (this.reflectable.fieldAnnotations()
							|| (field.getAnnotation(HasReflect.class) != null && field
									.getAnnotation(HasReflect.class)
									.annotation())) {
						Annotation[] annotations = AnnotationsHelper
								.getAnnotations(field);
						GeneratorHelper.addAnnotations_AnnotationImpl(
								this.typeOracle, "field", source, annotations,
								logger);
					}

					source.println();
				}
			}
			source.outdent();
			source.println("}");
		}

		protected void addMethods(JClassType classType, SourceWriter source) {
			source.println();

			source.println("protected void addMethods(){");
			source.indent();

			source.println("MethodImpl method = null;");

			JMethod[] methods = classType.getMethods();

			boolean needReflect = this.reflectable.methods();

			for (int i = 0; i < methods.length; i++) {
				JMethod method = methods[i];
				// sxf update
				// if (!method.isPublic())
				// continue;

				if (method.isPrivate()) {
					continue;
				}

				if (needReflect
						|| method.getAnnotation(HasReflect.class) != null) {
					source.println("method = new MethodImpl(this, \""
							+ method.getName() + "\");");
					source.println("method.addModifierBits("
							+ GeneratorHelper.AccessDefToInt(method) + "); ");
					source.println("method.setReturnTypeName(\""
							+ method.getReturnType().getQualifiedSourceName()
							+ "\");");

					// no need java.lang.class
					if (reflectable.relationTypes()
							&& !method.getReturnType().getQualifiedSourceName()
									.equals("java.lang.Class")) {
						source.println("method.setReturnType("
								+ TypeHelper.getClassTypeCode(method
										.getReturnType()
										.getQualifiedSourceName()) + ");");
					}
					if (method.isAnnotationMethod() != null) {
						try {
							Class<?> clazz = Class.forName(classType
									.getQualifiedBinaryName());
							Method m = clazz.getMethod(method.getName());
							if (m != null) {
								source.println("method.setDefaultValue("
										+ GeneratorHelper.annoValueToCode(
												typeOracle,
												m.getDefaultValue(), logger)
										+ ");");
							}
						} catch (ClassNotFoundException e) {
							this.logger
									.log(
											com.google.gwt.core.ext.TreeLogger.Type.ERROR,
											"Default value of method will not avaliable for reflection. "
													+ method.toString()
													+ e.toString());
						} catch (SecurityException e) {
							this.logger
									.log(
											com.google.gwt.core.ext.TreeLogger.Type.ERROR,
											"Default value of method will not avaliable for reflection. "
													+ method.toString()
													+ e.toString());
						} catch (NoSuchMethodException e) {
							this.logger
									.log(
											com.google.gwt.core.ext.TreeLogger.Type.ERROR,
											"Default value of method will not avaliable for reflection. "
													+ method.toString()
													+ e.toString());
						}
					}

					// GeneratorHelper.addMetaDatas("method", source, method);
					JParameter[] params = method.getParameters();
					for (int j = 0; j < params.length; j++) {
						JParameter param = params[j];
						// no need java.lang.class
						if (reflectable.relationTypes()
								&& !param.getType().getQualifiedSourceName().equals(
												"java.lang.Class")) {
							source.println("new ParameterImpl(method, \""
									+ param.getType().getQualifiedSourceName()
									+ "\","
									+ TypeHelper
											.getClassTypeCode(param.getType()
													.getQualifiedSourceName())
									+ ", \"" + param.getName() + "\");");
						}else{
							source.println("new ParameterImpl(method, \""
									+ param.getType().getQualifiedSourceName()
									+ "\","
									+ "null"
									+ ", \"" + param.getName() + "\");");
						}
						// TODO Support annotation of Parameter
					}

					if (this.reflectable.fieldAnnotations()
							|| (method.getAnnotation(HasReflect.class) != null && method
									.getAnnotation(HasReflect.class)
									.annotation())) {
						Annotation[] annotations = AnnotationsHelper
								.getAnnotations(method);
						GeneratorHelper.addAnnotations_AnnotationImpl(
								this.typeOracle, "method", source, annotations,
								logger);
					}

					source.println();
				}
			}

			source.outdent();
			source.println("}");
		}

		// sxf add
		protected void setFieldValue(JClassType classType, SourceWriter source) {
			sourceWriter
					.println("public void setFieldValue(Object instance, String fieldName, Object value) {");
			sourceWriter.indent();

			sourceWriter.println(classType.getQualifiedSourceName()
					+ " content = (" + classType.getQualifiedSourceName()
					+ ")instance;");

			JField[] fields = classType.getFields();

			for (int i = 0; i < fields.length; i++) {
				JField jField = fields[i];

				if (jField.isPrivate()
						|| jField.isFinal()
						|| (jField.isProtected() && GeneratorHelper
								.isSystemClass(classType))) {
					continue;
				}

				if (!this.reflectable.fields()) {
					HasReflect hasReflect = jField
							.getAnnotation(HasReflect.class);
					if (hasReflect == null)
						continue;
				}

				String fieldName = jField.getName();

				sourceWriter.println("if (fieldName.equals(\"" + fieldName
						+ "\")) {");
				sourceWriter.indent();

				String value = unboxWithoutConvert(jField.getType()
						.getQualifiedSourceName(), "value");
				sourceWriter
						.println("content." + fieldName + "=" + value + ";");

				sourceWriter.outdent();
				sourceWriter.print("} else ");

			}
			sourceWriter
					.println("    super.setFieldValue(instance, fieldName, value);");
			// sourceWriter.println("{");
			// sourceWriter.indent();
			// sourceWriter
			// .println(
			// "throw new IllegalArgumentException(\"Method: \" + methodName + \" can't found.\");"
			// );
			// sourceWriter.outdent();
			// sourceWriter.println("}");
			sourceWriter.outdent();
			sourceWriter.println("}");
			sourceWriter.println();
		}

		// sxf add
		protected void getFieldValue(JClassType classType, SourceWriter source) {
			sourceWriter
					.println("public Object getFieldValue(Object instance, String fieldName) {");
			sourceWriter.indent();

			sourceWriter.println(classType.getQualifiedSourceName()
					+ " content = (" + classType.getQualifiedSourceName()
					+ ")instance;");

			JField[] fields = classType.getFields();

			for (int i = 0; i < fields.length; i++) {
				JField jField = fields[i];
				// Private or protected field under package java.x, javax.x is
				// not accessible
				if (jField.isPrivate()
						|| (jField.isProtected() && GeneratorHelper
								.isSystemClass(classType))) {
					continue;
				}

				if (!this.reflectable.fields()) {
					HasReflect hasReflect = jField
							.getAnnotation(HasReflect.class);
					if (hasReflect == null)
						continue;
				}

				String fieldName = jField.getName();

				sourceWriter.println("if (fieldName.equals(\"" + fieldName
						+ "\")) {");
				sourceWriter.indent();

				sourceWriter.println("return content." + fieldName + ";");

				sourceWriter.outdent();
				sourceWriter.print("} else ");

			}
			sourceWriter
					.println("    return super.getFieldValue(instance, fieldName);");
			// sourceWriter.println("{");
			// sourceWriter.indent();
			// sourceWriter
			// .println(
			// "throw new IllegalArgumentException(\"Method: \" + methodName + \" can't found.\");"
			// );
			// sourceWriter.outdent();
			// sourceWriter.println("}");
			sourceWriter.outdent();
			sourceWriter.println("}");
			sourceWriter.println();
		}

		private boolean needCatchException(JMethod method) {
			boolean result = false;
			JClassType runtimeException = typeOracle.findType(
					RuntimeException.class.getCanonicalName())
					.isClassOrInterface();
			for (JType type : method.getThrows()) {
				result = !type.isClassOrInterface().isAssignableTo(
						runtimeException);
				if (result)
					return result;
			}
			return result;
		}

		protected String getInvokeParams(JParameter[] methodParams,
				String argeName) {
			StringBuilder result = new StringBuilder("");
			for (int i = 0; i < methodParams.length; i++) {
				String requestType = methodParams[i].getType()
						.getQualifiedSourceName();
				JType paramType = methodParams[i].getType();
				if (paramType instanceof JArrayType) {
					paramType = ((JArrayType) paramType).getComponentType();
				}
				if (paramType.isTypeParameter() != null)
					requestType = paramType.isTypeParameter().getBaseType()
							.getQualifiedSourceName();
				if (methodParams[i].getType() instanceof JArrayType) {
					if (!requestType.contains("[]"))
						requestType += "[]";
				}
				result.append("("
						+ unboxIfNeed(requestType, argeName + "[" + i + "]")
						+ ")");

				if (i != methodParams.length - 1) {
					result.append(", ");
				}
			}
			return result.toString();
		}

		private void createAnnotationImpl(SourceWriter sourceWriter,
				JAnnotationType annotation) {
			sourceWriter.println();
			String className = annotation.getQualifiedSourceName();
			String implClassName = className.replace('.', '_') + "Impl";
			// we don't have class object,instead,we have a className string,
			// so can't extends AnnotationImpl
			sourceWriter.println("public static class " + implClassName
					// + " extends AnnotationImpl "
					+ " implements " + annotation.getQualifiedSourceName()
					+ "{");
			sourceWriter.indent();
			JAnnotationMethod[] methods = annotation.getMethods();
			// declare variable
			for (JAnnotationMethod method : methods) {
				sourceWriter.println("private final "
						+ method.getReturnType().getQualifiedSourceName() + " "
						+ method.getName() + ";");
			}

			// Constructor
			StringBuilder sb = new StringBuilder();
			sb.append("public ").append(implClassName).append(
					"(Object[] values){");
			sourceWriter.println(sb.toString());

			// for (JAnnotationMethod method : methods){
			// sb.append(", ").append(method.getReturnType().getQualifiedSourceName()).append(" ").append(method.getName());
			// }
			int i = 0;
			for (JAnnotationMethod method : methods) {
				sourceWriter.println("  this."
						+ method.getName()
						+ " = "
						+ unboxIfNeed(method.getReturnType()
								.getQualifiedSourceName(), "values[" + i + "]")
						+ ";");
				i++;
			}
			sourceWriter.println("}");

			// Methods
			for (JAnnotationMethod method : methods) {
				sourceWriter.println("public "
						+ method.getReturnType().getQualifiedSourceName() + " "
						+ method.getName() + "() {");
				sourceWriter.println("  return " + method.getName() + ";");
				sourceWriter.println("}");
			}

			sourceWriter.outdent();

			sourceWriter.println("public Class<"
					+ annotation.getQualifiedSourceName()
					+ "> annotationType() {");
			sourceWriter.indent();

			sourceWriter.println("return "
					+ annotation.getQualifiedSourceName() + ".class;");
			sourceWriter.outdent();
			sourceWriter.println("  }");

			sourceWriter.println("}");
			sourceWriter.println();

			// create createAnnotation method
			sourceWriter.println("public " + implClassName
					+ " createAnnotation(Object[] params) {");
			sourceWriter.indent();
			sourceWriter.println("return new " + implClassName + "(params);");
			sourceWriter.outdent();
			sourceWriter.println("}");
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
			// System.out.println("requestType: " + requestType + " argeName: "
			// + argeName);
			// return "(" + requestType + ")" + argeName;

			if (!isPrimitiveType(requestType)) {
				return "(" + requestType + ")" + argeName;
			} else if (requestType.equals("int")) {
				return "((Integer)" + argeName + ").intValue()";
			} else if (requestType.equals("byte")) {
				return "((Byte)" + argeName + ").byteValue()";
			}
			if (requestType.equals("short")) {
				return "((Short)" + argeName + ").shortValue()";
			}
			if (requestType.equals("long")) {
				return "((Long)" + argeName + ").longValue()";
			}
			if (requestType.equals("float")) {
				return "((Float)" + argeName + ").floatValue()";
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

		// sxf add
		public String unboxWithoutConvert(String requestType, String argeName) {
			// System.out.println("requestType: " + requestType + " argeName: "
			// + argeName);
			// return "(" + requestType + ")" + argeName;

			if (!isPrimitiveType(requestType)) {
				return "(" + requestType + ")" + argeName;
			} else if (requestType.equals("int")) {
				return "((Integer)" + argeName + ")";
			} else if (requestType.equals("byte")) {
				return "((Byte)" + argeName + ")";
			}
			if (requestType.equals("short")) {
				return "((Short)" + argeName + ")";
			}
			if (requestType.equals("long")) {
				return "((Long)" + argeName + ")";
			}
			if (requestType.equals("float")) {
				return "((Float)" + argeName + ")";
			}
			if (requestType.equals("double")) {
				return "((Double)" + argeName + ")";
			}
			if (requestType.equals("boolean")) {
				return "((Boolean)" + argeName + ")";
			}
			if (requestType.equals("char")) {
				return "((Character)" + argeName + ")";
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
			return argeName;

			// if (!isPrimitiveType(requestType)) {
			// // return "(" + requestType + ")" + argeName;
			// // Change to Object to avoid import problem
			// return "(Object)" + argeName;
			// } else if (requestType.equals("integer")) {
			// return "Integer.valueOf(" + argeName + ")";
			// } else if (requestType.equals("Byte")) {
			// return "Byte.valueOf(" + argeName + ")";
			// }
			// if (requestType.equals("Short")) {
			// return "Short.valueOf(" + argeName + ")";
			// }
			// if (requestType.equals("long")) {
			// return "Long.valueOf(" + argeName + ")";
			// }
			// if (requestType.equals("float")) {
			// return "Float.valueOf(" + argeName + ")";
			// }
			// if (requestType.equals("double")) {
			// return "Double.valueOf(" + argeName + ")";
			// }
			// if (requestType.equals("boolean")) {
			// return "Boolean.valueOf(" + argeName + ")";
			// }
			// if (requestType.equals("char")) {
			// return "Character.valueOf(" + argeName + ")";
			// } else {
			// return "(" + requestType + ")" + argeName;
			// }
		}

	}

	protected void createSource(SourceWriter source, JClassType classType) {
		String className = getSimpleUnitName(classType);
		Reflectable reflectable = GenUtils
				.getClassTypeAnnotationWithMataAnnotation(
						getReflectionType(classType), Reflectable.class);
		if (reflectable == null)
			reflectable = ReflectableHelper.getFullSettings(typeOracle);

		new ReflectionSourceCreator(className, getReflectionType(classType),
				source, this.typeOracle, logger, reflectable).createSource();
	}

	/**
	 * SourceWriter instantiation. Return null if the resource already exist.
	 * 
	 * @return sourceWriter
	 */
	public SourceWriter doGetSourceWriter(JClassType classType) {
		String packageName = getPackageName(classType);
		String simpleName = getSimpleUnitName(classType);
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				packageName, simpleName);
		if (getReflectionType(classType).isAnnotation() != null) {
			composer
					.setSuperclass("com.gwtent.reflection.client.impl.AnnotationTypeProxy<"
							+ getReflectionType(classType)
									.getQualifiedSourceName() + ">");

		} else if (getReflectionType(classType).isEnum() == null) {
			composer
					.setSuperclass("com.gwtent.reflection.client.impl.ClassTypeProxy<"
							+ getReflectionType(classType)
									.getQualifiedSourceName() + ">");
		} else {
			composer
					.setSuperclass("com.gwtent.reflection.client.impl.EnumTypeProxy<"
							+ getReflectionType(classType)
									.getQualifiedSourceName() + ">");
		}

		// composer.addImplementedInterface(classType.getQualifiedSourceName());
		composer.addImport("java.lang.Object"); // Some times user has there own
		// Object class
		composer.addImport(classType.getQualifiedSourceName());

		composer.addImport("com.gwtent.common.client.*");
		composer.addImport("com.gwtent.reflection.client.*");
		composer.addImport("com.gwtent.reflection.client.impl.*");
		composer.addImport("com.google.gwt.core.client.*");
		composer.addImport("java.util.*");
		composer.addImport(classType.getPackage().getName() + ".*");
		composer.addImport(getReflectionType(classType).getPackage().getName()
				+ ".*");

		PrintWriter printWriter = context.tryCreate(logger, packageName,
				simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
	}

	protected String getPackageName(JClassType classType) {
		classType = getReflectionType(classType);
		String packageName = classType.getPackage().getName();
		// avoid java.lang.SecurityException: Prohibited package name:
		// java.lang...
		if (GeneratorHelper.isSystemClass(classType)) {
			packageName = Reflectable.class.getPackage().getName();
		}
		return packageName;
		// return "com.gwtent.reflection.client.gen."
		// + classType.getPackage().getName();
	}

	protected String getSimpleUnitName(JClassType classType) {
		// sxf update
		// String simpleUnitNameWithOutSuffix =
		// getSimpleUnitNameWithOutSuffix(getReflectionType(classType));
		// String simpleUnitNameWithOutSuffix2 =
		// getSimpleUnitNameWithOutSuffix(classType);
		// String string = simpleUnitNameWithOutSuffix
		// + "_" + simpleUnitNameWithOutSuffix2 + getSUFFIX();

		// String reflectionTypeName = getReflectionType(classType).getName();
		// return reflectionTypeName+ getSUFFIX();

		// return getSimpleUnitNameWithOutSuffix(getReflectionType(classType)) +
		// "_" + getSimpleUnitNameWithOutSuffix(classType) + getSUFFIX();
		JClassType refectionType = getReflectionType(classType);
		if (GeneratorHelper.isSystemClass(refectionType)) {
			return refectionType.getPackage().getName().replace('.', '_') + "_"
					+ getSimpleUnitNameWithOutSuffix(refectionType) + "_"
					+ getSUFFIX();
		} else {
			return getSimpleUnitNameWithOutSuffix(refectionType) + "_"
					+ getSUFFIX();
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

	@Override
	protected String getSUFFIX() {
		return SUFFIX;
	}

	protected JClassType getReflectionType(JClassType classType) {
		if (reflectionType == null)
			reflectionType = GeneratorHelper.getReflectionClassType(typeOracle,
					classType);

		return reflectionType;
	}

}
