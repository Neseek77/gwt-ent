package com.gwtent.showcase.client.htmltemplate;

import com.google.gwt.user.client.ui.DeckPanel;
import com.gwtent.client.template.HTMLEvent;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.ShowcaseHTMLPanel;

@ShowcaseHTMLPanel("HtmlTemplate.html")
public class HTMLTplMainPage extends HTMLTemplatePanel {

	public HTMLTplMainPage(String html) {
		super(html);
	}

	@HTMLWidget
	protected DeckPanel subContent = new DeckPanel();
	
	
	@HTMLEvent(value = {"linkTplOverview"})
	protected void doHomeClick(){
		
	}
	
	
	@HTMLEvent(value = {"linkTplBasic"})
	protected void doHTMLTemplateClick(){
		
	}
	
	
	
}
