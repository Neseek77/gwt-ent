package com.gwtent.showcase.client.aop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.gwtent.htmltemplate.client.HTMLEvent;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.showcase.client.SubContent;
import com.gwtent.showcase.client.htmltemplate.MyHTMLTemplate;

@MyHTMLTemplate("aop/AOP.html")
public class AOPMainPage extends HTMLTemplatePanel {

	public AOPMainPage(String html) {
		super(html);
	}

	@HTMLWidget
	protected SubContent subContentAop = new SubContent();
	
	
	@HTMLEvent(value = {"linkAopOverview"})
	protected void doHomeClick(){
		Window.alert("Not finish yet, please have a look 'basic' link below.");
	}
	
	
	@HTMLEvent(value = {"linkAopBasic"})
	protected void doAopBasicClick(){
		if (basicPage == null){
			basicPage = GWT.create(AOPBasicPage.class);
			subContentAop.addShowCase(basicPage);
		}
		
		subContentAop.showShowCase(basicPage);
	}
	
	private AOPBasicPage basicPage;

}
