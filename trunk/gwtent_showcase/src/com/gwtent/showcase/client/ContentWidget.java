package com.gwtent.showcase.client;

import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.Widget;

public class ContentWidget<T extends Widget> extends Composite {
	
  @SuppressWarnings("unchecked")
	public ContentWidget(ShowCase showCase) {
  	//grid.getCellFormatter().setAlignment(0, 0, HasHorizontalAlignment.ALIGN_RIGHT, HasVerticalAlignment.ALIGN_TOP);
  	grid.setWidget(0, 0, createGridTopLinks(showCase));
  	grid.setHTML(1, 0, "<hr>");
  	grid.setWidget(2, 0, showCase.getWidget());
  	caseWidget = (T)showCase.getWidget();
  	this.initWidget(grid);
  }
  
  private T caseWidget;
  public T getWidget(){
  	return caseWidget;
  }
  
  protected Grid createGridTopLinks(ShowCase showCase){
  	Grid gridTopLinks = new Grid(1, showCase.getResourceNames().length + 1);
  	gridTopLinks.getCellFormatter().setWidth(0, 0, "100%");
  	gridTopLinks.setText(0, 0, showCase.getCaseName());
  	
  	for (int i = 0; i < showCase.getResourceNames().length; i++){
  		gridTopLinks.setHTML(0, i + 1, "<a href=\"" + linkProvider.getLink(showCase.getClass().getName(), showCase.getResourceNames()[i]) + "\">" + showCase.getResourceNames()[i] + "</a>");
  	}
  	
  	return gridTopLinks;
  }
  
  private Grid grid = new Grid(3, 1);
  private LinkProvider linkProvider = new LinkGoogleCodeProvider();
  
  
  protected static interface LinkProvider{
  	String getLink(String classFullName, String resourceName);
  }
  
  protected static class LinkGoogleCodeProvider implements LinkProvider{

		public String getLink(String classFullName, String resourceName) {
			String[] paths = classFullName.split("\\.");
			String path = "/";
			for (int i = 0; i < paths.length - 1; i++){
				path = path + paths[i] + "/";
			}

			return "http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent_showcase/src" + path + resourceName;
		}
  	
  } 
  
}
