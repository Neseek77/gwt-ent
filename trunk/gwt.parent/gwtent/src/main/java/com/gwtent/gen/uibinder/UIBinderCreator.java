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

//
package com.gwtent.gen.uibinder;

import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.JType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenUtils;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.reflection.client.pathResolver.PathResolver;
import com.gwtent.uibinder.client.UIBind;

public class UIBinderCreator extends LogableSourceCreator {
	
	public UIBinderCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
	}
	
	private static class VariableProvider {
    private static Map<JMethod, String> names = new HashMap<JMethod, String>();
    
    private static String findNextName(JMethod method){
      String result = method.getName();
      Integer i = 0;
      
      while (names.containsValue(result)) {
        result = method.getName() + i.toString();
      }
      return result;
    }
    
    static String getName(JMethod method){
      String result = names.get(method);
      if (result == null){
        result = findNextName(method);
        names.put(method, result);
      }
      
      return result;
    }
  }
	
	private String getEventTypeVariableName(JMethod method){
    return "EventType_" + VariableProvider.getName(method);
  }
	
	
	
	private String processHTML(){
		return null;
	}
	
	private boolean processTemplateAnnotation(Annotation annotation, List<Annotation> annotations){
	  if (annotation instanceof HTMLTemplate){
      annotations.add(annotation);
      return true;
    }else if (annotation.annotationType().getName().startsWith("java.lang.annotation")){
      return false;  //Document's parent is itself?
    }
	  
	  Class<? extends Annotation> annotationClass = annotation.annotationType(); 
	  Annotation[] metaAnnotations = annotationClass.getAnnotations();
    for (Annotation metaAnnotation : metaAnnotations) {
      if (processTemplateAnnotation(metaAnnotation, annotations)) {
        annotations.add(annotation);
        return true;
      }
    }
    return false;
	}
	
	
	private void getHTMLTemplateSettings(JClassType classType, List<Annotation> annotationList){
	  JClassType parent = classType.getSuperclass();
    if (parent != null){
      getHTMLTemplateSettings(parent, annotationList);
    }
    
	  Annotation[] annotations = AnnotationsHelper.getAnnotations(classType);
    for (Annotation annotation : annotations){
      processTemplateAnnotation(annotation, annotationList);
    }
	}
	

	protected void createSource(SourceWriter source, JClassType classType){
	  
//	  HTMLTemplate template = GenUtils.getClassTypeAnnotation(classType, HTMLTemplate.class);
	  
//	  if (template.getValue().length() <= 0){
//	    throw new RuntimeException("You have to add @HTMLTemplate or annotations that the annotation type is annotated with @HTMLTemplate to your HTMLTemplate class.");
//	  }
	  
    
    bindToEditor(source, classType);
    
		source.println("public " + getSimpleUnitName(classType) + "(){");
    source.indent();
    source.println("super();");
    source.println("_BindToEditor();");
    source.outdent();
    source.println("}");
	}
	
	private String findClassTypeByPath(JClassType classType, String path){
	  String firstPath = PathResolver.getFirstElementByPath(path);
	  
	  if (firstPath.endsWith("()")){
	  	firstPath = firstPath.substring(0, firstPath.length() - 2);
	  }
	  
	  if (GenUtils.findField(classType, firstPath) != null)
	    return GenUtils.findField(classType, firstPath).getType().getQualifiedSourceName();
	  else if (GenUtils.findMethod(classType, firstPath, new JType[]{}) != null){
	    return GenUtils.findMethod(classType, firstPath, new JType[]{}).getReturnType().getQualifiedSourceName();
	  } else{
	    throw new RuntimeException("Can not find first part(" + firstPath + ") of path(" + path + ") in class(" + classType.getQualifiedSourceName() + ")");
	  }
	}
	
	private void bindToEditor(SourceWriter source, JClassType classType){
	  source.println("private void _BindToEditor(){");
    source.indent();
    
    JClassType curType = classType;
    while (curType != null) {
      for (JMethod method: curType.getMethods()){
        UIBind bind = GenUtils.getMethodAnnotation(method, UIBind.class);
        processUIBinder(source, classType, bind, method.getName()+"()");
      }
      
      for (JField field : curType.getFields()){
        UIBind bind = field.getAnnotation(UIBind.class);
        processUIBinder(source, classType, bind, field.getName());
      }
      
      curType = curType.getSuperclass();
    }
    
    source.println("doAfterBinderAllEditors();");
    
    source.outdent();
    source.println("}");
	}



  private void processUIBinder(SourceWriter source, JClassType classType,
      UIBind bind, String widgetSource) {
    if (bind != null){
      String path = bind.path();
      Boolean readonly = bind.readonly();
      Class<?> modelClass = bind.modelClass();
      
      //if (PathResolver.getResetElementByPath(path).length() <= 0)
      //  throw new RuntimeException("Path (" + path + ") of class (" + classType.getQualifiedSourceName() + ") not right, we are not support variable binding for now.");

      //getUIBinderManager().addBinder(btn, "path", false, "Model.class");
//      source.println("getUIBinderManager().addBinder(" + widgetSource + ", \"" + PathResolver.getResetElementByPath(path) + "\", " + 
//          readonly.toString() + ", " +
//      		"" + findClassTypeByPath(classType, path) + ".class);");
      String setCode = "";
      String rootValueName = PathResolver.getFirstElementByPath(path);
      
      if (! rootValueName.endsWith("()")){
        setCode = rootValueName + " = (" + findClassTypeByPath(classType, path)+")value;";
      }
        
      String autoValidate = "false";
      if (bind.autoValidate())
      	autoValidate = "true";
      
      //new Class<?>[]{Default.class}
      StringBuilder sb = new StringBuilder();
      sb.append("new Class<?>[]{");
      boolean first = true;
      for (Class<?> clazz : bind.groups()){
      	if (!first)
      		sb.append(", ");
      	
      	sb.append(clazz.getCanonicalName()).append(".class");
      	
      	first = false;
      }
      sb.append("}");
      
      source.println("getUIBinderManager().addBinder(" + widgetSource +", \"" + PathResolver.getResetElementByPath(path) + "\", "
          + readonly.toString() + ", " + findClassTypeByPath(classType, path) + ".class,\n" + 
        "        new com.gwtent.client.uibinder.ModelRootAccessor(){\n"+
        "          public Object getValue() {\n" +
        "            return " + rootValueName + ";\n"+
        "          }\n" +
        "          public String getRootPath() {\n" +
				"		         return \""+ rootValueName +"\";\n" +
				"          }\n" + 
        "          public void setValue(Object value) {\n" +
        "            " + setCode + "\n" +           
        "          }}, "+ autoValidate +", " + sb.toString() + ");");
    }
  }
	
  

	/**
	 * SourceWriter instantiation. Return null if the resource already exist.
	 * 
	 * @return sourceWriter
	 */
	public SourceWriter doGetSourceWriter(JClassType classType) throws Exception {
	  if (classType.isAbstract())
	    throw new RuntimeException("HTML Template Class (" + classType.getQualifiedSourceName() + ") can NOT be a abstract class.");
	  
		String packageName = classType.getPackage().getName();
		String simpleName = getSimpleUnitName(classType);
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				packageName, simpleName);
		composer.setSuperclass(classType.getQualifiedSourceName());
		composer.addImport(classType.getQualifiedSourceName());
		
		composer.addImport("com.google.gwt.core.client.*");
		composer.addImport("com.google.gwt.user.client.*");
		composer.addImport("com.gwtent.client.*");
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
		return GenUtils.getTemplate_SUFFIX();
	}
	
	protected GenExclusion getGenExclusion(){
		return null;
	}

}
