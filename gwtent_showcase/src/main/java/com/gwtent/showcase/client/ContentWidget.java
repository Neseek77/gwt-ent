package com.gwtent.showcase.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Widget;

public class ContentWidget<T extends Widget> extends Composite {
	
  @SuppressWarnings("unchecked")
	public ContentWidget(ShowCase showCase) {
  	//grid.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
  	grid.setWidget(0, 0, createGridTopLinks(showCase));
  	grid.setHTML(1, 0, "<hr>");
  	grid.setWidget(2, 0, showCase.getShowCaseWidget());
  	caseWidget = (T)showCase.getShowCaseWidget();
  	this.initWidget(grid);
  }
  
  private T caseWidget;
  public T getShowCaseWidget(){
  	return caseWidget;
  }
  
  protected Grid createGridTopLinks(ShowCase showCase){
  	int count = 1;
  	if (showCase.getResourceFileNames() != null)
  		count = count + showCase.getResourceFileNames().length;
  	
  	Grid gridTopLinks = new Grid(1, count);
  	gridTopLinks.getCellFormatter().setWidth(0, 0, "100%");
  	gridTopLinks.setText(0, 0, showCase.getCaseName());
  	
  	if (showCase.getResourceFileNames() != null){
    	for (int i = 0; i < showCase.getResourceFileNames().length; i++){
    		gridTopLinks.setHTML(0, i + 1, "<a target=\"_blank\" href=\"" + linkProvider.getLink(showCase.getClass().getName(), showCase.getResourceFileNames()[i]) + "\">" + getResourceNameFromFileName(showCase.getResourceFileNames()[i]) + "</a>");
    	}  		
  	}
  	
  	return gridTopLinks;
  }
  
  private String getResourceNameFromFileName(String fileName){
  	String[] names = fileName.split("/");
  	if (names.length > 0)
  		return names[names.length-1];
  	else
  		return "";
  }
  
  private Grid grid = new Grid(3, 1);
  private LinkProvider linkProvider = new LinkGoogleCodeProvider();
  
  
  protected static interface LinkProvider{
  	String getLink(String classFullName, String resourceName);
  }
  
  protected static class LinkGoogleCodeProvider implements LinkProvider{

		public String getLink(String classFullName, String resourceFileName) {
			String[] paths = classFullName.split("\\.");
			String path = "/";
			for (int i = 0; i < paths.length - 1; i++){
				path = path + paths[i] + "/";
			}

			return "http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent_showcase/src/main/java/" + path + resourceFileName;
		}
  	
  } 
  
}
