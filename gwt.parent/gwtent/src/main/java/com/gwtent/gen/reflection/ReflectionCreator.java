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

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.JAnnotationMethod;
import com.google.gwt.core.ext.typeinfo.JAnnotationType;
import com.google.gwt.core.ext.typeinfo.JArrayType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.JPrimitiveType;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.gen.GenUtils;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.reflection.client.HasReflect;
import com.gwtent.reflection.client.Reflectable;
import com.gwtent.reflection.client.Type;
import com.gwtent.reflection.client.impl.PrimitiveTypeImpl;

public class ReflectionCreator extends LogableSourceCreator {

	private final boolean isUseLog = true;

	static final String SUFFIX = "__ClassType";

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

		public ReflectionSourceCreator(String className, JClassType classType,
				SourceWriter sourceWriter,
				com.google.gwt.core.ext.typeinfo.TypeOracle typeOracle, TreeLogger logger,
				Reflectable reflectable) {
			this.className = className;
			this.sourceWriter = sourceWriter;
			this.classType = classType;
			this.typeOracle = typeOracle;
			this.logger = logger;
			this.reflectable = reflectable;
		}

		public void createSource() {
			if (classType.isAnnotation() != null){
				createAnnotationImpl(classType.isAnnotation());
			}

			sourceWriter.println("public " + className + "(){");
			sourceWriter.indent();
			// sourceWriter.println("super(\"" + classType.getQualifiedSourceName() +
			// "\");");
			// sourceWriter.println("super(" + classType.getQualifiedSourceName() +
			// ".class);");
			sourceWriter.println("super(" + classType.getQualifiedSourceName() + ".class);");
			//sourceWriter.println("addClassMeta();");
			sourceWriter.println("addAnnotations();");
			sourceWriter.println("addFields();");
			sourceWriter.println("addMethods();");
			
			if (this.reflectable.constructors()){
				if ((classType.isClass() != null)
						&& GenUtils.hasPublicDefaultConstructor(classType)) {
					if ((!classType.isAbstract()) && (classType.isDefaultInstantiable())) {
						sourceWriter.println("new ConstructorImpl(this){");
						sourceWriter.println("	public Object newInstance() {");
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
			if (classType.getSuperclass() != null && classType.getSuperclass().isPublic()) {
				sourceWriter.println("if (" + "TypeOracleImpl.findType("
						+ classType.getSuperclass().getQualifiedSourceName() + ".class)"
						+ " != null)");
				sourceWriter
						.println("setSuperclass((ClassTypeImpl)TypeOracleImpl.findType("
								+ classType.getSuperclass().getQualifiedSourceName()
								+ ".class).isClassOrInterface());");
			}

			sourceWriter.println();
			for (JClassType type : classType.getImplementedInterfaces()) {
				//sourceWriter.println("if (" + "TypeOracleImpl.findType(\""
				//		+ type.getQualifiedSourceName() + "\")" + " != null)");
				//sourceWriter
				//		.println("addImplementedInterface((ClassTypeImpl)TypeOracleImpl.findType(\""
				//				+ type.getQualifiedSourceName() + "\").isClassOrInterface());");
				sourceWriter.println("addImplementedInterface(" + type.getQualifiedSourceName() + ".class);");
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

			JMethod[] methods = classType.getMethods();

			sourceWriter
					.println("public Object invoke(Object instance, String methodName, Object[] args) throws MethodInvokeException {");
			sourceWriter.indent();

			sourceWriter.println(classType.getQualifiedSourceName() + " content = ("
					+ classType.getQualifiedSourceName() + ")instance;");

			sourceWriter.println("if (args == null){");
			sourceWriter.indent();
			sourceWriter.println("args = new Object[]{};");
			sourceWriter.outdent();
			sourceWriter.println("}");

			for (JMethod method : methods) {
				if (!method.isPublic())
					continue;

				String methodName = method.getName();
				JParameter[] methodParameters = method.getParameters();
				JType returnType = method.getReturnType();

				sourceWriter
						.println("if (methodName.equals(\"" + methodName + "\")) {");
				sourceWriter.indent();
				sourceWriter.println("checkInvokeParams(methodName, "
						+ methodParameters.length + ", args);");

				if (needCatchException(method)) {
					sourceWriter.println("try{");
					sourceWriter.indent();
				}

				if (!returnType.getSimpleSourceName().equals("void")) {
					sourceWriter.println("return "
							+ boxIfNeed(returnType.getQualifiedSourceName(), "content."
									+ methodName + "("
									+ getInvokeParams(methodParameters, "args") + ")") + ";");
				} else {
					sourceWriter.println("content." + methodName + "("
							+ getInvokeParams(methodParameters, "args") + ")" + ";");
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
			sourceWriter.println("return super.invoke(instance, methodName, args);");
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

			// -----Add Class MetaData--------------------------------
			//addClassMeta(classType, sourceWriter);
			// -----Add Class Annotation------------------------------------
			addClassAnnotation(classType, sourceWriter);
			// -----Add fields----------------------------------------
			addFields(classType, sourceWriter);
			// -----Add methods---------------------------------------
			addMethods(classType, sourceWriter);
		}

		private void createAnnotationImpl(JAnnotationType annotation) {
			String implClassName = className + "Impl";
			sourceWriter.println("private static class " + implClassName + " extends AnnotationImpl implements " + annotation.getQualifiedSourceName() + "{");
			JAnnotationMethod[] methods = annotation.getMethods();
			//declare variable
      for (JAnnotationMethod method : methods) {
      	sourceWriter.println("private final " + method.getReturnType().getQualifiedSourceName() + " " + method.getName() + ";");
      }
      
      //Constructor
      StringBuilder sb = new StringBuilder();
      sb.append("public ").append(implClassName).append("(Class<? extends java.lang.annotation.Annotation> clazz");
      for (JAnnotationMethod method : methods){
      	sb.append(", ").append(method.getReturnType().getQualifiedSourceName()).append(" ").append(method.getName());
      }
      sb.append("){");
      sourceWriter.println(sb.toString());
      sourceWriter.println("super(clazz);");
      for (JAnnotationMethod method : methods){
      	sourceWriter.println("this." + method.getName() + " = "+ method.getName() +";");
      }
      sourceWriter.println("}");
      
      //Methods
      for (JAnnotationMethod method : methods){
      	sourceWriter.println("public " + method.getReturnType().getQualifiedSourceName() + " " + method.getName() + "() {");
      	sourceWriter.println("  return " + method.getName() + ";");
      	sourceWriter.println("}");
      }
      
      sourceWriter.println("}");
		}

//		protected void addClassMeta(JClassType classType, SourceWriter source) {
//			source.println();
//
//			source.println("protected void addClassMeta(){");
//			source.indent();
//
//			GeneratorHelper.addMetaDatas("this", source, classType);
//
//			source.outdent();
//			source.println("}");
//		}

		protected void addClassAnnotation(JClassType classType, SourceWriter source) {
			source.println();

			source.println("protected void addAnnotations(){");
			source.indent();
			
			if (this.reflectable.classAnnotations()){
				Annotation[] annotations = AnnotationsHelper.getAnnotations(classType);
				GeneratorHelper.addAnnotations_AnnotationImpl(this.typeOracle, "this", source,
						annotations, logger);
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
				
				if (needReflect || field.getAnnotation(HasReflect.class) != null){
					if (field.isEnumConstant() == null)
						source.println("field = new FieldImpl(this, \"" + field.getName()	+ "\");");
					else
						source.println("field = new EnumConstantImpl(this, \"" + field.getName()	+ "\", " + field.isEnumConstant().getOrdinal() + ");");
					
					source.println("field.addModifierBits("
							+ GeneratorHelper.AccessDefToInt(field) + "); ");
					source.println("field.setTypeName(\""
							+ field.getType().getQualifiedSourceName() + "\");");

					//GeneratorHelper.addMetaDatas("field", source, field);

					if (this.reflectable.fieldAnnotations() || (field.getAnnotation(HasReflect.class) != null && field.getAnnotation(HasReflect.class).annotation())){
						Annotation[] annotations = AnnotationsHelper.getAnnotations(field);
						GeneratorHelper.addAnnotations_AnnotationImpl(this.typeOracle, "field", source,
								annotations, logger);
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
				if (!method.isPublic())
					continue;

				if (needReflect || method.getAnnotation(HasReflect.class) != null){
					source.println("method = new MethodImpl(this, \"" + method.getName()
							+ "\");");
					source.println("method.addModifierBits("
							+ GeneratorHelper.AccessDefToInt(method) + "); ");
					source.println("method.setReturnTypeName(\""
							+ method.getReturnType().getQualifiedSourceName() + "\");");

					//GeneratorHelper.addMetaDatas("method", source, method);
					JParameter[] params = method.getParameters();
					for (int j = 0; j < params.length; j++) {
						JParameter param = params[j];
						source.println("new ParameterImpl(method, \""
								+ param.getType().getQualifiedSourceName() + "\", \""
								+ param.getName() + "\");");
						// TODO Support annotation of Parameter
					}

					if (this.reflectable.fieldAnnotations() || (method.getAnnotation(HasReflect.class) != null && method.getAnnotation(HasReflect.class).annotation())){
						Annotation[] annotations = AnnotationsHelper.getAnnotations(method);
						GeneratorHelper.addAnnotations_AnnotationImpl(this.typeOracle, "method", source,
								annotations, logger);
					}

					source.println();	
				}
			}

			source.outdent();
			source.println("}");
		}

		private boolean needCatchException(JMethod method) {
			boolean result = false;
			JClassType runtimeException = typeOracle.findType(
					RuntimeException.class.getCanonicalName()).isClassOrInterface();
			for (JType type : method.getThrows()) {
				result = !type.isClassOrInterface().isAssignableTo(runtimeException);
				if (result)
					return result;
			}
			return result;
		}

		protected String getInvokeParams(JParameter[] methodParams, String argeName) {
			StringBuilder result = new StringBuilder("");
			for (int i = 0; i < methodParams.length; i++) {
				String requestType = methodParams[i].getType().getQualifiedSourceName();
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
				result.append("(" + unboxIfNeed(requestType, argeName + "[" + i + "]")
						+ ")");

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
			// System.out.println("requestType: " + requestType + " argeName: " +
			// argeName);

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
				return "Long.valueOf(" + argeName + ")";
			}
			if (requestType.equals("float")) {
				return "Float.valueOf(" + argeName + ")";
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

	}

	protected void createSource(SourceWriter source, JClassType classType) {
		String className = getSimpleUnitName(classType);
		new ReflectionSourceCreator(className, classType, source, this.typeOracle, logger, ReflectableHelper.getFullSettings(this.typeOracle))
				.createSource();

		// source.println("public " + getSimpleUnitName(classType) + "(){");
		// source.indent();
		// source.println("super(\"" + classType.getQualifiedSourceName() + "\");");
		// source.println("addClassMeta();");
		// source.println("addAnnotations();");
		// source.println("addFields();");
		// source.println("addMethods();");
		// source.println("");
		// if (classType.getSuperclass() != null){
		// source.println("if (" + "TypeOracleImpl.findType(\"" +
		// classType.getSuperclass().getQualifiedSourceName() + "\")" +
		// " != null)");
		// source.println("setSuperclass((ClassTypeImpl)TypeOracleImpl.findType(\""
		// + classType.getSuperclass().getQualifiedSourceName() +
		// "\").isClassOrInterface());");
		// }
		//		
		// source.println();
		// for (JClassType type : classType.getImplementedInterfaces()){
		// source.println("if (" + "TypeOracleImpl.findType(\"" +
		// type.getQualifiedSourceName() + "\")" + " != null)");
		// source.println(
		// "addImplementedInterface((ClassTypeImpl)TypeOracleImpl.findType(\"" +
		// type.getQualifiedSourceName() + "\").isClassOrInterface());");
		// }
		// source.outdent();
		// source.println("}");
		//
		// source
		// .println(
		// "protected void checkInvokeParams(String methodName, int paramCount, Object[] args) throws IllegalArgumentException{"
		// );
		// source.indent();
		// source.println("if (args.length != paramCount){");
		// source.indent();
		// source
		// .println(
		// "throw new IllegalArgumentException(\"Method: \" + methodName + \"request \" + paramCount + \" params, but invoke provide \" + args.length + \" params.\");"
		// );
		// source.outdent();
		// source.println("}");
		// source.outdent();
		// source.println("}");
		// source.println();
		//
		// JMethod[] methods = classType.getMethods();
		//
		// source.println(
		// "public Object invoke(Object instance, String methodName, Object[] args) throws RuntimeException {"
		// );
		// source.indent();
		//
		// source.println(classType.getQualifiedSourceName() + " content = (" +
		// classType.getQualifiedSourceName() + ")instance;");
		//
		// source.println("if (args == null){");
		// source.indent();
		// source.println("args = new Object[]{};");
		// source.outdent();
		// source.println("}");
		//
		// for (JMethod method : methods) {
		// if (! method.isPublic())
		// continue;
		//			
		// String methodName = method.getName();
		// JParameter[] methodParameters = method.getParameters();
		// JType returnType = method.getReturnType();
		//
		// source.println("if (methodName.equals(\"" + methodName + "\")) {");
		// source.indent();
		// source.println("checkInvokeParams(methodName, " + methodParameters.length
		// + ", args);");
		//
		// if (needCatchException(method)){
		// source.println("try{");
		// source.indent();
		// }
		//			
		// if (!returnType.getSimpleSourceName().equals("void")) {
		// source.println("return "
		// + boxIfNeed(returnType.getSimpleSourceName(), "content."
		// + methodName + "(" + getInvokeParams(methodParameters, "args")
		// + ")") + ";");
		// } else {
		// source.println("content." + methodName + "("
		// + getInvokeParams(methodParameters, "args") + ")" + ";");
		// source.println("return null;");
		// }
		//			
		// if (needCatchException(method)){
		// source.println("}catch (Throwable e){");
		// source.println("throw new CheckedExceptionWrapper(e);");
		// source.println("}");
		// source.outdent();
		// }
		//
		// source.outdent();
		// source.print("} else ");
		//
		// }
		// source.println("{");
		// source.indent();
		// source
		// .println(
		// "throw new IllegalArgumentException(\"Method: \" + methodName + \" can't found.\");"
		// );
		// source.outdent();
		// source.println("}");
		// source.outdent();
		// source.println("}");
		// source.println();
		//
		// // -----Add Class MetaData--------------------------------
		// addClassMeta(classType, source);
		// // -----Add Class Annotation------------------------------------
		// addClassAnnotation(classType, source);
		// // -----Add fields----------------------------------------
		// addFields(classType, source);
		// // -----Add methods---------------------------------------
		// addMethods(classType, source);
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
		if (classType.isEnum() == null)
			composer.setSuperclass("com.gwtent.reflection.client.impl.ClassTypeImpl");
		else
			composer.setSuperclass("com.gwtent.reflection.client.impl.EnumTypeImpl");
			
		composer.addImport("com.gwtent.common.client.*");
		composer.addImport("com.gwtent.reflection.client.*");
		composer.addImport("com.gwtent.reflection.client.impl.*");
		composer.addImport("com.google.gwt.core.client.*");
		composer.addImport("java.util.*");
		composer.addImport(classType.getPackage().getName() + ".*");

		PrintWriter printWriter = context
				.tryCreate(logger, packageName, simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
	}

	protected String getPackageName(JClassType classType) {
		return "com.gwtent.reflection.client.gen."
				+ classType.getPackage().getName();
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

}
