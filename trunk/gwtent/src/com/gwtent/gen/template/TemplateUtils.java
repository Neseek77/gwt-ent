package com.gwtent.gen.template;

import com.google.gwt.core.ext.typeinfo.JField;
import com.google.gwt.core.ext.typeinfo.JMethod;
import com.gwtent.htmltemplate.client.HTMLWidget;

public class TemplateUtils {
	
	public static String getId(HTMLWidget widget){
		if ((widget.value() != null) && (widget.value().length() > 0)){
			return widget.value();
		}
		
		return "";
	}
	
	public static String getId(JField field, HTMLWidget widget){
		String id = getId(widget);
		if (id.length() > 0){
			return id;
		}else{
			return field.getName();
		}
	}
	
	public static String getId(JMethod method, HTMLWidget widget){
		String id = getId(widget);
		if (id.length() > 0){
			return id;
		}else{
			String name = method.getName();
			if (name.startsWith("get")){
				name = name.substring(4);
			}
			return name;
		}
	}
}
