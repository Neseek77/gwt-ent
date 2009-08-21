package com.gwtent.showcase.client.reflection;

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
	
	
	@HTMLEvent(value = {"linkRefOverview"})
	protected void doHTMLTemplateClick(){
//		if (basicPage == null){
//			basicPage = GWT.create(HTMLTplBasicPage.class);
//			subContent.addShowCase(basicPage);
//		}
//		
//		subContent.showShowCase(basicPage);
	}
	
	

}
