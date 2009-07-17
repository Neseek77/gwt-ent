package com.gwtent.gen.template;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * The Java version of @HTMLTemplate
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public class HTMLTemplateJava {
  private String value = "";
  private String basePath = "";
  private String html = "";
  private List<String> variables = new ArrayList<String>();
  private boolean compileToSource = true;
  private boolean autoAddWidget = true;
  private boolean autoDefineCSS = true;
  private boolean renameId = true;
  
  public void setValue(String value) {
    this.value = value;
  }
  public String getValue() {
    return value;
  }
  public void setBasePath(String basePath) {
    this.basePath = basePath;
  }
  public String getBasePath() {
    return basePath;
  }
  public void setHtml(String html) {
    this.html = html;
  }
  public String getHtml() {
    return html;
  }
  
  /**
   * always append
   * @param variables
   */
  public void setVariables(String[] variables) {
    appendVariables(variables);
  }
  public String[] getVariables() {
    return (String[])variables.toArray(new String[0]);
  }
  public void appendVariables(String[] variables){
    for (String str : variables){
      this.variables.add(str);
    }
  }
  
  
  public void setCompileToSource(boolean compileToSource) {
    this.compileToSource = compileToSource;
  }
  public boolean isCompileToSource() {
    return compileToSource;
  }
  public void setAutoAddWidget(boolean autoAddWidget) {
    this.autoAddWidget = autoAddWidget;
  }
  public boolean isAutoAddWidget() {
    return autoAddWidget;
  }
  public void setAutoDefineCSS(boolean autoDefineCSS) {
    this.autoDefineCSS = autoDefineCSS;
  }
  public boolean isAutoDefineCSS() {
    return autoDefineCSS;
  }
	public void setRenameId(boolean renameId) {
		this.renameId = renameId;
	}
	public boolean isRenameId() {
		return renameId;
	}
}
