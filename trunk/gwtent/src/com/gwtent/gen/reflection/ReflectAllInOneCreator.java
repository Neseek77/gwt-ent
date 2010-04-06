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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Constraint;

import org.aspectj.lang.annotation.Aspect;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.HasAnnotations;
import com.google.gwt.core.ext.typeinfo.JAnnotationMethod;
import com.google.gwt.core.ext.typeinfo.JAnnotationType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JParameter;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.client.CheckedExceptionWrapper;
import com.gwtent.client.reflection.HasReflect;
import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.reflection.Reflectable;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.impl.TypeOracleImpl;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenUtils;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.gen.reflection.ReflectionCreator.ReflectionSourceCreator;

public class ReflectAllInOneCreator extends LogableSourceCreator {
	
	private List<String> allGeneratedClassNames = new ArrayList<String>();
	private Set<JClassType> relationClassesProcessed = new HashSet<JClassType>();

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
	
	private List<JClassType> candidateList = new ArrayList<JClassType>();
	private Map<JClassType, Reflectable> candidates = new HashMap<JClassType, Reflectable>();

	@Override
	public void createSource(SourceWriter source, JClassType classType) {
		allGeneratedClassNames = genAllClasses(source);
		
		source.println("public " + getSimpleUnitName(classType) + "(){");
		source.indent();
		
		for (String classname : allGeneratedClassNames){
			source.println("new " + classname + "();");
		}
		
		source.outdent();
		source.println("}");
	}
	
	private List<String> genAllClasses(SourceWriter sourceWriter){
		List<String> result = new ArrayList<String>();
		for(JClassType type : candidateList){
			String className = type.getPackage().getName().replace('.', '_') + '_' + getSimpleUnitNameWithOutSuffix(type); //type.getPackage().getName().replace('.', '_') + '_' + type.getSimpleSourceName().replace('.', '_'); //getSimpleUnitName(type);
			sourceWriter.indent();
			if (type.isEnum() == null)
				sourceWriter.println("private static class " + className + " extends com.gwtent.client.reflection.impl.ClassTypeImpl {");
			else
				sourceWriter.println("private static class " + className + " extends com.gwtent.client.reflection.impl.EnumTypeImpl {");

			new ReflectionSourceCreator(className, type, sourceWriter, this.typeOracle, logger, candidates.get(type)).createSource();
			sourceWriter.outdent();
			sourceWriter.println("}");

			result.add(className);
		}
		return result;
	}
	
	//TODO refactor by source visitor pattern
	
	private void getAllReflectionClasses() throws NotFoundException{

		//System annotations
		addClassIfNotExists(typeOracle.getType(Retention.class.getCanonicalName()), ReflectableHelper.getDefaultSettings(typeOracle));
		addClassIfNotExists(typeOracle.getType(Documented.class.getCanonicalName()), ReflectableHelper.getDefaultSettings(typeOracle));
		addClassIfNotExists(typeOracle.getType(Inherited.class.getCanonicalName()), ReflectableHelper.getDefaultSettings(typeOracle));
		addClassIfNotExists(typeOracle.getType(Target.class.getCanonicalName()), ReflectableHelper.getDefaultSettings(typeOracle));
		addClassIfNotExists(typeOracle.getType(Deprecated.class.getCanonicalName()), ReflectableHelper.getDefaultSettings(typeOracle));
		
		//=====GWT0.7
		for (JClassType classType : typeOracle.getTypes()) {
			Reflectable reflectable = GenUtils.getClassTypeAnnotationWithMataAnnotation(classType, Reflectable.class);
			if (reflectable != null){
				processClass(classType, reflectable);
			}
		}
		//======end of gwt0.7
	}

	private void processClass(Class<?> clazz, Reflectable reflectable) {
		processClass(typeOracle.findType(ReflectionUtils.getQualifiedSourceName(clazz)), reflectable);
	}
	
	private void processClass(JClassType classType, Reflectable reflectable) {
		if (! genExclusion(classType)){
			processRelationClasses(classType, reflectable);
		  processAnnotationClasses(classType, reflectable);
			addClassIfNotExists(classType, reflectable);
		}
	}
	
	private Reflectable getNearestSetting(Class<?> clazz, Reflectable defaultSetting){
		return getNearestSetting(typeOracle.findType(ReflectionUtils.getQualifiedSourceName(clazz)), defaultSetting);
	}
	
	/**
	 * Get nearest Reflectable, if not found, using defaultSetting
	 * @param classType
	 * @param defaultSetting
	 * @return
	 */
	private Reflectable getNearestSetting(JClassType classType, Reflectable defaultSetting){
		Reflectable result = GenUtils.getClassTypeAnnotationWithMataAnnotation(classType, Reflectable.class);
		if (result != null)
			return result;
		else
			return defaultSetting;
	}
	
	private void processRelationClass(JClassType classType, Reflectable reflectable){
		Reflectable nearest = getNearestSetting(classType, reflectable);
		processRelationClasses(classType, nearest);
		processAnnotationClasses(classType, nearest);
		addClassIfNotExists(classType, nearest);
	}
	
	private boolean hasReflection(HasAnnotations type){
		return type.getAnnotation(HasReflect.class) != null;
	}
	
	private boolean hasReflectionAnnotation(HasAnnotations type){
		return (type.getAnnotation(HasReflect.class) != null) && type.getAnnotation(HasReflect.class).annotation();
	}
	
	private void processRelationClasses(JClassType classType, Reflectable reflectable){
		if (classType == null)	
			return;
		
		if (classType.isParameterized() != null || 
				classType.isRawType() != null || 
				classType.isWildcard() != null || 
				classType.isTypeParameter() != null)
			return;
		
		if (relationClassesProcessed.contains(classType))
			return;
		
		processAnnotationClasses(classType, reflectable);
		
		if (reflectable.superClasses()){
			if (classType.getSuperclass() != null){
				processRelationClass(classType.getSuperclass(), reflectable);
			}
		}
		
		if (reflectable.relationTypes()){
			for (JClassType type : classType.getImplementedInterfaces()){
				processRelationClass(type, reflectable);
			}
		}

		relationClassesProcessed.add(classType);
		
		
		
		processFields(classType, reflectable);
		
		processMethods(classType, reflectable);
	}

	private void processFields(JClassType classType, Reflectable reflectable) {
		boolean need = reflectable.relationTypes();
		
		for (JField field : classType.getFields()) {
			if (reflectable.fieldAnnotations() || (hasReflectionAnnotation(field))){
				processAnnotationClasses(field, reflectable);
			  
				JClassType type = field.getType().isClassOrInterface();
			  if (type != null)
			  	if (need || (hasReflection(field) && field.getAnnotation(HasReflect.class).fieldType()))
			  	if (! type.isAssignableTo(classType))  //some times, it's itself of devided class
	  		  	processRelationClasses(type, reflectable);
			  
			  addClassIfNotExists(type, reflectable);
			}
				
		}
	}

	private void processMethods(JClassType classType, Reflectable reflectable) {
		boolean need = reflectable.relationTypes();
		for (JMethod method : classType.getMethods()){
			if (reflectable.fieldAnnotations() || (hasReflectionAnnotation(method))){
				processAnnotationClasses(method, reflectable);
				
				HasReflect hasReflect = method.getAnnotation(HasReflect.class);
				JClassType type = null;
				
				if (need || (hasReflect != null && hasReflect.resultType())){
					if (method.getReturnType() != null && method.getReturnType().isClassOrInterface() != null){
						type = method.getReturnType().isClassOrInterface();
						
						if (! type.isAssignableTo(classType))
							processRelationClasses(type, reflectable);
						
					  addClassIfNotExists(type, reflectable);
					}
				}
				
				
			  if (need || (hasReflect != null && hasReflect.parameterTypes())){
			  	for (JParameter parameter :method.getParameters()){
						if (parameter.getType() != null && parameter.getType().isClassOrInterface() != null){
							type = parameter.getType().isClassOrInterface();
							
							if (! type.isAssignableTo(classType))
								processRelationClasses(type, reflectable);
							
						  addClassIfNotExists(type, reflectable);
						}
					}
			  }
			}
		}
	}
	
	private void processAnnotationClasses(HasAnnotations annotations, Reflectable reflectable){
		if (! reflectable.classAnnotations())
			return;
		
	  Annotation[] annos= AnnotationsHelper.getAnnotations(annotations);
	  if (annos == null)
	    return;
	  
	  for (Annotation annotation : annos){
	    processAnnotation(annotation);
	  }
	}
	
	private Reflectable getFullSettings(){
		return ReflectableHelper.getFullSettings(typeOracle);
	}
	
	private void processClassFromAnnotationValue(Object value){
		if (value != null && value instanceof Class && (!((Class)value).getName().equals("void"))){
			processClass((Class)value, getNearestSetting((Class)value, getFullSettings()));
		}
	}
	
	private void processAnnotation(Annotation annotation){
	  if (annotation.annotationType().getName().startsWith("java.lang.annotation")){
	     return;  //Document's parent is itself, must check here
	   }else{
	     JClassType classType = this.typeOracle.findType(ReflectionUtils.getQualifiedSourceName(annotation.annotationType()));
	     
	     if (classType == null)
	    	 return; //
	     
	     addClassIfNotExists(classType, getNearestSetting(classType, getFullSettings()));
	     
	     //Go through all annotation methods, if has class, add that class to reflection as well
       JAnnotationType annoType = classType.isAnnotation();
       JAnnotationMethod[] methods = annoType.getMethods();
       for (JAnnotationMethod method : methods) {
         Object value = null;
         try {
           value = annotation.annotationType().getMethod(method.getName(), new Class[]{}).invoke(annotation, null);
           //System.out.println(value);
           //System.out.println(value.getClass());
           if (value instanceof Class){
          	 processClassFromAnnotationValue(value);
           }else if (value.getClass().isArray()){
         	    for (int i = 0; i < Array.getLength(value); i++){
         	    	if (Array.get(value, i) instanceof Class)
         	    		processClassFromAnnotationValue(Array.get(value, i));
         	    }
         	  }else if (value instanceof Annotation){
         	  	processAnnotation((Annotation)value);
         	  }
         } catch (Exception e){
         	throw new CheckedExceptionWrapper(e);
         }
       }
	     
	     Class<? extends Annotation> annotationType = annotation.annotationType(); 
	     Annotation[] metaAnnotations = annotationType.getAnnotations();
	     for (Annotation metaAnnotation : metaAnnotations) {
	       processAnnotation(metaAnnotation);
	     }
	   }
	}
	
	private void addClassIfNotExists(JClassType classType, Reflectable setting){
		//Add next line we can make sure we just append normal class type, always get from TypeOracle
		//not JParameterizedType or JTypeParameter etc...
		if (classType != null){
//			System.out.println("addClassIfNotExists: " + classType.getQualifiedSourceName());
			classType = this.typeOracle.findType(classType.getQualifiedSourceName());
		}
		
		//we just process public classes
		if ((classType == null) || (!classType.isPublic()))
		  return;  
		
		if (candidateList.indexOf(classType) < 0)
			candidateList.add(classType);
		candidates.put(classType, setting);
	}

	protected SourceWriter doGetSourceWriter(JClassType classType) throws NotFoundException {
		if (candidates.size() <= 0){
			getAllReflectionClasses();
		}
		
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
