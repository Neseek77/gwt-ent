package com.gwtent.showcase.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.gwtent.client.template.HTMLEvent;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.SubContent;

@HTMLTemplate(value = "com/gwtent/showcase/public/uibinder/UIBinder.html", renameId = false)
public class UIBinderPage extends HTMLTemplatePanel {

	public UIBinderPage(String html) {
		super(html);
	}

	@HTMLWidget
	protected SubContent subContentUIB = new SubContent();
	
	
//	@HTMLEvent(value = {"linkRefOverview"})
//	protected void doHomeClick(){
//		
//	}
	
	
	@HTMLEvent(value = {"linkUIBSmall"})
	protected void dolinkRefBasicClick(){
		if (uibinderSmallExample == null){
			uibinderSmallExample = GWT.create(UIBinderSmallExample.class);
			subContentUIB.addShowCase(uibinderSmallExample);
		}
		
		subContentUIB.showShowCase(uibinderSmallExample);
	}
	
	
	private UIBinderSmallExample uibinderSmallExample;
}
