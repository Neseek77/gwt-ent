package com.gwtent.showcase.client.reflection;

import com.google.gwt.core.client.GWT;
import com.gwtent.client.template.HTMLEvent;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.SubContent;

@HTMLTemplate(value = "Reflection.html", renameId = false)
public class ReflectionPage extends HTMLTemplatePanel {

	public ReflectionPage(String html) {
		super(html);
	}

	@HTMLWidget
	protected SubContent subContentRef = new SubContent();
	
	
//	@HTMLEvent(value = {"linkRefOverview"})
//	protected void doHomeClick(){
//		
//	}
	
	
	@HTMLEvent(value = {"linkRefBasic"})
	protected void dolinkRefBasicClick(){
		if (reflectBasicPage == null){
			reflectBasicPage = GWT.create(ReflectBasicPage.class);
			subContentRef.addShowCase(reflectBasicPage);
		}
		
		subContentRef.showShowCase(reflectBasicPage);
	}
	
	@HTMLEvent(value = {"linkRefEnum"})
	protected void dolinkRefEnumClick(){
		if (reflectEnumPage == null){
			reflectEnumPage = GWT.create(ReflectEnumPage.class);
			subContentRef.addShowCase(reflectEnumPage);
		}
		
		subContentRef.showShowCase(reflectEnumPage);
	}
	
	@HTMLEvent(value = {"linkRefDomain"})
	protected void dolinkRefDomainClick(){
		if (reflectDomainPage == null){
			reflectDomainPage = GWT.create(ReflectDomainPage.class);
			subContentRef.addShowCase(reflectDomainPage);
		}
		
		subContentRef.showShowCase(reflectDomainPage);
	}
	
	private ReflectBasicPage reflectBasicPage;
	private ReflectEnumPage reflectEnumPage;
	private ReflectDomainPage reflectDomainPage;
}
