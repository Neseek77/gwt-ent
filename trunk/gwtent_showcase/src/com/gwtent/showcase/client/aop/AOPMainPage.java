package com.gwtent.showcase.client.aop;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.gwtent.client.template.HTMLEvent;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.SubContent;

@HTMLTemplate("AOP.html")
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
