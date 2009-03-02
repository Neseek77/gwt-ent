package com.gwtent.gen.template;

import java.io.IOException;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.SourceWriter;

public interface HTMLPreProcessor {
	public void process(SourceWriter source, JClassType classType, HTMLTemplateJava template) throws IOException;
	
	public String getHTML();
	public SourceLines getSourceLines();

}
