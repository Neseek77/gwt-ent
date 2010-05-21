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


package com.gwtent.gen.template;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.AnnotationsHelper;
import com.google.gwt.core.ext.typeinfo.JAnnotationMethod;
import com.google.gwt.core.ext.typeinfo.JAnnotationType;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.common.client.CheckedExceptionWrapper;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenUtils;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.htmltemplate.client.HTMLEvent;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.htmltemplate.client.HTMLWidget;

public class TemplateCreator extends LogableSourceCreator {
	
	private HTMLPreProcessor htmlProcessor = new HTMLPreProcessorFreeMarkerImpl();
	
	public TemplateCreator(TreeLogger logger, GeneratorContext context,
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
        i++;
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
	
	private void setHTMLTemplateJava(HTMLTemplate template, HTMLTemplateJava templateJava){
	  templateJava.setValue(template.value());
	  templateJava.setAutoAddWidget(template.autoAddWidget());
	  templateJava.setBasePath(template.basePath());
	  templateJava.setCompileToSource(template.compileToSource());
	  templateJava.setHtml(template.html());
	  templateJava.setVariables(template.variables());
	  templateJava.setAutoDefineCSS(template.autoDefineCSS());
	  templateJava.setRenameId(template.renameId());
	}
	
	private void setHTMLTemplateJavaByReflection(Annotation annotation, HTMLTemplateJava templateJava){
	  JClassType annClassType = null;
    try {
      annClassType = typeOracle.getType(annotation.annotationType().getName());
    } catch (NotFoundException e1) {
      throw new CheckedExceptionWrapper(e1);
    }
    JAnnotationType annoType = annClassType.isAnnotation();
    JAnnotationMethod[] methods = annoType.getMethods();
    for (JAnnotationMethod method : methods) {
      Object value = null;
      try {
        //Currently just support mothod without parameters
        value = annotation.annotationType().getMethod(method.getName(), new Class[]{}).invoke(annotation, null);
      } catch (Exception e){
        throw new CheckedExceptionWrapper(e);
      }
      
      String methodName = method.getName();
      if (methodName.equals("value")){
        templateJava.setValue((String)value);
      }else if (methodName.equals("autoAddWidget")){
        templateJava.setAutoAddWidget((Boolean)value);
      }else if (methodName.equals("basePath")){
        templateJava.setBasePath((String)value);
      }else if (methodName.equals("compileToSource")){
        templateJava.setCompileToSource((Boolean)value);
      }else if (methodName.equals("html")){
        templateJava.setHtml((String)value);
      }else if (methodName.equals("variables")){
        templateJava.setVariables((String[])value);
      }else if (methodName.equals("autoDefineCSS")){
        templateJava.setAutoDefineCSS((Boolean)value);
      }else if (methodName.equals("renameId")){
        templateJava.setAutoDefineCSS((Boolean)value);
      }
    }    
  }
	

  /**
   * Set all values from annotationList to templateJava
   * The devided annotation will override parent's
   * @param annotationList the annotation inherited list
   * @param templateJava
   */
  private void setHTMLTemplateJava(List<Annotation> annotationList, HTMLTemplateJava templateJava){
    for (Annotation annotation : annotationList){
      if (annotation instanceof HTMLTemplate){
        setHTMLTemplateJava((HTMLTemplate)annotation, templateJava);
      }else{
        setHTMLTemplateJavaByReflection(annotation, templateJava);
      }
    }
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
	
	private HTMLTemplateJava getHTMLTemplateSettings(JClassType classType){
	  List<Annotation> annotationList = new ArrayList<Annotation>();
	  getHTMLTemplateSettings(classType, annotationList);
	  
	  HTMLTemplateJava result = new HTMLTemplateJava();
    setHTMLTemplateJava(annotationList, result);
    
    return result;
	}


	protected void createSource(SourceWriter source, JClassType classType){
	  HTMLTemplateJava template = getHTMLTemplateSettings(classType);
		
//	  HTMLTemplate template = GenUtils.getClassTypeAnnotation(classType, HTMLTemplate.class);
	  
//	  if (template.getValue().length() <= 0){
//	    throw new RuntimeException("You have to add @HTMLTemplate or annotations that the annotation type is annotated with @HTMLTemplate to your HTMLTemplate class.");
//	  }
	  
	  source.println("interface TemplateDataBinder extends com.gwtent.uibinder.client.DataBinder<" + classType.getQualifiedSourceName() + ">{");
	  source.println("	");
	  source.println("}");
	  
		source.println("");
		source.println("private static String getHTML(){");
		source.indent();
		if (template.getValue() != ""){
		  String url = template.getBasePath() + template.getValue();
		  if (url.toUpperCase().indexOf("HTTP://") > 0){
		    
		  }else{
//		    if (url.indexOf("/") == -1) {
//		      url = classType.getPackage().getName().replace('.', '/') + "/" + url;
//		    }
//		    URL resource = getClass().getClassLoader().getResource(url);
//		    if (resource == null)
//		      throw new RuntimeException("Can not got resource of " + url);
//		    
//		    File file = new File(resource.getFile());
		  	
		  	try {
					this.htmlProcessor.process(source, classType, template);
				} catch (Exception e1) {
					throw new CheckedExceptionWrapper(e1.toString(), e1);
				}
		  	
		    StringBuffer contents = new StringBuffer();
        BufferedReader reader = null;
        try
        {
//            reader = new BufferedReader(new FileReader(file));
        	  reader = new BufferedReader(new StringReader(htmlProcessor.getHTML()));
            String text = null;
 
            // repeat until all lines is read
            while ((text = reader.readLine()) != null)
            {
              text = text.replace("\\", "\\\\");
            	text = text.replace("\"", "\\\"");
              
              
              if (contents.length() > 0){
                contents.append("+");
              }
              
                contents.append("\"").append(text).append("\"")
                    .append(System.getProperty(
                        "line.separator"));
            }
        } catch (FileNotFoundException e)
        {
            throw new CheckedExceptionWrapper(e);
        } catch (IOException e)
        {
          throw new CheckedExceptionWrapper(e);
        } finally
        {
            try
            {
                if (reader != null)
                {
                    reader.close();
                }
            } catch (IOException e)
            {
              throw new CheckedExceptionWrapper(e);
            }
        }
        
        source.println("final String _HTML = " + contents + ";");
        source.println("return _HTML;");
		  }
		}else if (template.getHtml() != ""){
		  source.println("final String _HTML = \"" + template.getHtml() + "\";");
		  source.println("return _HTML;");
		}else {
		  throw new RuntimeException("You have to setup @HTMLTemplate.value or @HTMLTemplate.html");
		}
		source.outdent();
		source.println("}");
		
		source.println("private void addElements(){");
		source.indent();
		JClassType curType = classType;
		while (curType != null) {
		  for (JField aField: curType.getFields()){
	      processField(source, aField, template);
	    }
	    for (JMethod aField: curType.getMethods()){
	      processMethod(source, aField, template);
	    }
	    
	    curType = curType.getSuperclass();
    }
		
		source.outdent();
		source.println("}");
		
		source.println("protected void doSinkBrowserEvents() {");
		source.indent();
		processEvents(source, classType);
		source.outdent();
    source.println("}");
		
    codeFromHTML(source);
    
    //DataBinderUtils.bindToEditor(source, classType);
    
		source.println("public " + getSimpleUnitName(classType) + "(){");
    source.indent();
    source.println("super(getHTML());");
    if (template.isRenameId())
    	source.println("this.setRenameIdWhenAddWidget(true);");
    else
    	source.println("this.setRenameIdWhenAddWidget(false);");
    source.println("addElements();");
    source.println("doSinkBrowserEvents();");
    
    source.println("this.setUIBinderManager((com.gwtent.uibinder.client.DataBinder)GWT.create(TemplateDataBinder.class));");
    source.println("this.getUIBinderManager().bindAll(this);");
    source.println("doAfterBinderAllEditors();");
    
    
    source.println("_CodeFromHTML();");
    source.outdent();
    source.println("}");
	}
	
	private void codeFromHTML(SourceWriter source){
		source.println("private void _CodeFromHTML() {");
		source.indent();
		for (String str : this.htmlProcessor.getSourceLines()){
			source.println(str);
		}
		source.outdent();
    source.println("}");
	}
	
	private String makeEventCall(JMethod method){
	  if (method.getParameters().length == 0){
	    return method.getName() + "();";
	  }else if ((method.getParameters().length == 1) && (method.getParameters()[0].getType().getQualifiedSourceName().equals(Event.class.getName()))){
	    return method.getName() + "(event);";
	  }else{
	    throw new RuntimeException("The event hanlder either no parameter or one parameter like (Event event).");
	  }
	}
	
	private void processEvents(SourceWriter source, JClassType classType){
	  source.println("com.google.gwt.user.client.Element element = null;");
	  
	  JClassType curType = classType;
    while (curType != null) {
      for (JMethod method: curType.getMethods()){
        HTMLEvent event = GenUtils.getMethodAnnotation(method, HTMLEvent.class);
        if (event != null){
          String[] elementIds = event.value(); //TODO guest elementid by method.getName()
          
          String eventTypeVarName = getEventTypeVariableName(method);
          int eventType = event.eventType().getEvent();
          source.println("final int " + eventTypeVarName + " = " + eventType + ";");
          
          for (String elementId : elementIds){
            //source.println("element = DOM.getElementById(\"" + elementId + "\");");
          	source.println("element = getElementById(\"" + elementId + "\");");
            source.println("if (element != null) {");
            source.println("  DOM.sinkEvents(element, Event.ONCLICK);");
            source.println("  DOM.setEventListener(element, new com.google.gwt.user.client.EventListener() {");
            source.println("    public void onBrowserEvent(Event event) {");
            source.println("      if (DOM.eventGetType(event) == " + eventTypeVarName + ") {");
            source.println("        " + makeEventCall(method));
            source.println("      }");
            source.println("    }");
            source.println("  });");
            source.println("}else");
            source.println("  noSuchElementWhenSinkEvent(\"" + elementId + "\");");
          }
        }
                
      }
      
      curType = curType.getSuperclass();
    }
	}

  private void processMethod(SourceWriter source, JMethod aField, HTMLTemplateJava template) {
    HTMLWidget widget = aField.getAnnotation(HTMLWidget.class);
    if (widget != null){
      if (aField.isPrivate())
        throw new RuntimeException("@Widget can't apply to private method, please make sure subclass can access this method if you  are using @Widget.");
      
      processWidget(source, widget, aField.getName() + "()", aField.getName(), template);
    }
  }

  private void processField(SourceWriter source, JField aField, HTMLTemplateJava template) {
    HTMLWidget widget = aField.getAnnotation(HTMLWidget.class);
    if (widget != null){
      if (aField.isPrivate())
        throw new RuntimeException("@Widget can't apply to private field, please make sure subclass can access this field if you  are using @Widget.");
      
      processWidget(source, widget, aField.getName(), aField.getName(), template);
    }
  }
  
  private void processWidget(SourceWriter source, HTMLWidget widget, String sourcePart, String elementName, HTMLTemplateJava template){
    if (widget != null){
      String elementID = widget.value();
      if (elementID.length() <= 0)
        elementID = elementName;
      
      source.println("add(" + sourcePart + ", \"" + elementID + "\");");
      //source.println("addAndReplaceElement(" + sourcePart + ", \"" + elementID + "\");");
      
      if (widget.css().length() > 0){
        source.println(sourcePart + ".addStyleName(\"" + widget.css() + "\");");
      }
      
      if (template.isAutoDefineCSS()){
        source.println(sourcePart + ".addStyleName(\"" + elementID + "\");");
      }
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
		composer.addImport("com.gwtent.reflection.client.*");
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
