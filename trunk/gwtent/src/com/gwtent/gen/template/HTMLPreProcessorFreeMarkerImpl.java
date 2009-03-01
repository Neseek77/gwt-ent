package com.gwtent.gen.template;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.gen.GenUtils;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.SimpleHash;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class HTMLPreProcessorFreeMarkerImpl implements HTMLPreProcessor {

	private Configuration freemarkerCfg = new Configuration();
	private JClassType classType = null;
	private String html = "";
	private SourceLines lines = new SourceLines();
	
	public static class SourceAppender{
		private final String id;
		private final SourceLines lines;
		private final boolean isMethod;
		
		public SourceAppender(String id, SourceLines lines, boolean isMethod){
			this.id = id;
			this.lines = lines;
			this.isMethod = isMethod;
		}
		
		public String setProperty(String name, String value){
			if (isMethod)
				lines.add(id + "().set" + name + "(\"" + value + "\");");
			else
				lines.add(id + ".set" + name + "(\"" + value + "\");");
			
			return "";
		}

		public String getId() {
			return id;
		}
	}
	
	private String[] getVariable(HTMLTemplate template){
		return template.variables();
	}
	
	public SimpleHash hashMapFromStringList(String[] strs){
		SimpleHash result = new SimpleHash();
		for (String str : strs){
			int pos = str.indexOf("=");
			if (pos >= 0){
				String key = str.substring(0, pos);
				String value = str.substring(pos + 1);
				result.put(key, value);
			}
		}
		
		Map<Object, HTMLWidget> widgets = GenUtils.getAllAnnotations(classType, HTMLWidget.class);
		for (Object obj : widgets.keySet()){
			SourceAppender appender = null;
			HTMLWidget widget = widgets.get(obj);
			
			if (obj instanceof JClassType){
				//HTMLWidget on a Class? 
			}else if (obj instanceof JField){
				appender = new SourceAppender(TemplateUtils.getId((JField)obj, widget), lines, false);
			}else if (obj instanceof JMethod){
				appender = new SourceAppender(TemplateUtils.getId((JMethod)obj, widget), lines, true);
			}
			
			if (appender != null)
				result.put(appender.id, new BeanModel(appender, BeansWrapper.getDefaultInstance()));			
		}
		
		return result;
	}
	
	
	public void process(SourceWriter source,
			JClassType classType) throws IOException {
		this.classType = classType;
		
		String basePath = "/";
		String url = "";

		HTMLTemplate template = GenUtils.getClassTypeAnnotation(classType, HTMLTemplate.class);
		if (template != null){
			
			
			basePath = "/";
			if (template.basePath().length() > 0)
				basePath = template.basePath();
			url = template.value();

			if ((url.indexOf("/") == -1) && (template.basePath().length() <= 0)) {
	      url = classType.getPackage().getName().replace('.', '/') + "/" + url;
	    }

		}
		
		freemarkerCfg.setClassForTemplateLoading(this.getClass(), basePath);
		Template temp = freemarkerCfg.getTemplate(url);
		Writer out = new StringWriter();
		try {
			temp.process(hashMapFromStringList(getVariable(template)), out);
		} catch (TemplateException e) {
			new RuntimeException(e.getMessage(), e);
		}
    out.flush(); 
		html = out.toString();
	}


	public String getHTML() {
		return html;
	}


	public SourceLines getSourceLines() {
		return lines;
	}


}
