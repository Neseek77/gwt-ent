package com.gwtent.showcase.client.comm;

import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.showcase.client.ShowCase;

/**
 * 
 * @author James Luo
 *
 * 23/06/2010 4:37:32 PM
 */
public class OverviewPage extends Frame implements ShowCase{

	public OverviewPage(String pageName){
		this.setWidth("100%");
		this.setHeight("500px");
		this.setOverviewPageName(pageName);
	}
	
	private String overviewPageName;

	public void setOverviewPageName(String overviewPageName) {
		this.overviewPageName = overviewPageName;
		
		this.setUrl("./overview/" + overviewPageName + ".html");
	}

	public String getOverviewPageName() {
		return overviewPageName;
	}

	@Override
	public String getCaseName() {
		return "Overview";
	}

	@Override
	public String[] getResourceFileNames() {
		return null;
	}

	@Override
	public Widget getShowCaseWidget() {
		return this;
	}
	
}
