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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.validation.Constraint;

import org.aspectj.lang.annotation.Aspect;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.HasAnnotations;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.reflection.Reflectionable;
import com.gwtent.client.reflection.impl.TypeOracleImpl;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenUtils;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.gen.reflection.ReflectionCreator.ReflectionSourceCreator;

public class ReflectAllInOneCreator extends LogableSourceCreator {
	
	private List<String> allGeneratedClassNames = new ArrayList<String>();
	private List<JClassType> allReflectionClasses = null;

	public ReflectAllInOneCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
		
//		try {
//			System.out.println(context.getPropertyOracle().getPropertyValue(logger, "locale______"));
//		} catch (BadPropertyValueException e) {
//			//nothing, there is no exclusion setting
//		}
	}

	protected GenExclusion getGenExclusion(){
		return GenExclusionCompositeReflection.INSTANCE;
	}

	@Override
	protected String getSUFFIX() {
		return GenUtils.getReflection_SUFFIX();
	}

	@Override
	public void createSource(SourceWriter source, JClassType classType) {
		allGeneratedClassNames = genAllClasses(allReflectionClasses, source);
		
		source.println("public " + getSimpleUnitName(classType) + "(){");
		source.indent();
		
		for (String classname : allGeneratedClassNames){
			source.println("new " + classname + "();");
		}
		
		source.outdent();
		source.println("}");
	}
	
	private List<String> genAllClasses(List<JClassType> types, SourceWriter sourceWriter){
		List<String> result = new ArrayList<String>();
		for(JClassType type : types){
			String className = type.getPackage().getName().replace('.', '_') + '_' + type.getSimpleSourceName().replace('.', '_'); //getSimpleUnitName(type);
			sourceWriter.indent();
			sourceWriter.println("private class " + className + " extends com.gwtent.client.reflection.impl.ClassTypeImpl {");
			new ReflectionSourceCreator(className, type, sourceWriter, this.typeOracle).createSource();
			sourceWriter.outdent();
			sourceWriter.println("}");

			result.add(className);
		}
		return result;
	}
	
	
	//TODO refactor by source visitor pattern
	
	private List<JClassType> getAllReflectionClasses() throws NotFoundException{
		List<JClassType> types = new ArrayList<JClassType>();
		
//		try {
			JClassType reflectionClass = typeOracle.getType(Reflection.class.getCanonicalName());
			JClassType constraintClass = typeOracle.getType(Constraint.class.getCanonicalName());  //For temp use, will refactor here to support full @Reflectionable
			for (JClassType classType : typeOracle.getTypes()) {
			  //|| (classType.getAnnotation(Aspect.class) != null)
				if ((classType.isAssignableTo(reflectionClass)) || (classType.isAssignableTo(constraintClass))
				    || (GenUtils.getClassTypeAnnotationWithMataAnnotation(classType, Reflectionable.class) != null)){
					if (! genExclusion(classType)){
						processRelationClasses(types, classType);
						processAnnotationClasses(types, classType);
						addClassIfNotExists(types, classType);
					}
				}
			}
//		} catch (Exception e) {
//			this.logger.log(TreeLogger.Type.ERROR, e.getMessage(), e);
//		}
		 
		
		return types;
	}
	
	private void processRelationClasses(List<JClassType> types, JClassType classType){
		if (classType.getSuperclass() != null){
			processRelationClasses(types, classType.getSuperclass());
			processAnnotationClasses(types, classType.getSuperclass());
			addClassIfNotExists(types, classType.getSuperclass());
		}
		
		for (JClassType type : classType.getImplementedInterfaces()){
		  processAnnotationClasses(types, type);
		  addClassIfNotExists(types, type);
		}
		
		for (JField field : classType.getFields()) {
		  processAnnotationClasses(types, field);
		  addClassIfNotExists(types, field.getType().isClassOrInterface());
		}
		
		for (JMethod method : classType.getMethods()){
			if (method.getReturnType() != null)
			  processAnnotationClasses(types, method);
			  addClassIfNotExists(types, method.getReturnType().isClassOrInterface());
			  
			
			//TODO How about parameters?
		}
	}
	
	private void processAnnotationClasses(List<JClassType> types, HasAnnotations annotations){
	  Annotation[] annos= AnnotationsHelper.getAnnotations(annotations);
	  if (annos == null)
	    return;
	  
	  for (Annotation annotation : annos){
	    processAnnotation(types, annotation);
	  }
	}
	
	private void processAnnotation(List<JClassType> types, Annotation annotation){
	  if (annotation.annotationType().getName().startsWith("java.lang.annotation")){
	     return;  //Document's parent is itself? must check here
	   }else{
	     JClassType classType = this.typeOracle.findType(annotation.annotationType().getCanonicalName());
	     addClassIfNotExists(types, classType);
	     
	     Class<? extends Annotation> annotationType = annotation.annotationType(); 
	     Annotation[] metaAnnotations = annotationType.getAnnotations();
	     for (Annotation metaAnnotation : metaAnnotations) {
	       processAnnotation(types, metaAnnotation);
	     }
	   }
	}
	
	private void addClassIfNotExists(List<JClassType> types, JClassType classType){
		//Add next line we can make sure we just append normal class type, always get from TypeOracle
		//not JParameterizedType or JTypeParameter etc...
		if (classType != null){
//			System.out.println("addClassIfNotExists: " + classType.getQualifiedSourceName());
			classType = this.typeOracle.findType(classType.getQualifiedSourceName());
		}
		
		if ((classType != null) && (types.indexOf(classType) < 0)){
			types.add(classType);
		}
	}

	protected SourceWriter doGetSourceWriter(JClassType classType) throws NotFoundException {
		if (allReflectionClasses == null)
			allReflectionClasses = getAllReflectionClasses();
		
		String packageName = classType.getPackage().getName();
		String simpleName = getSimpleUnitName(classType);
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				packageName, simpleName);
		composer.setSuperclass(TypeOracleImpl.class.getCanonicalName());
		composer.addImport("com.gwtent.client.*");
		composer.addImport("com.gwtent.client.reflection.*");
		composer.addImport(classType.getPackage().getName() + ".*");
		
		composer.addImport("com.gwtent.client.reflection.impl.*");
		composer.addImport("com.google.gwt.core.client.*");
		composer.addImport("java.util.*");

		//James remove the following, some times client package have the same 
		//class name which is used by system(ie: Map), if using both package
		//Compiler will raise error.
//		Set<String> imports = new HashSet<String>();
//		for (JClassType aClassType : allReflectionClasses){
//			String str = aClassType.getPackage().getName() + ".*";
//			if (! imports.contains(str)){
//				imports.add(str);
//				composer.addImport(str);
//			}
//		}

		PrintWriter printWriter = context.tryCreate(logger, packageName, simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
	}


}
