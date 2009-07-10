package com.gwtent.showcase.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;
import com.gwtent.client.template.HTMLEvent;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.htmltemplate.HTMLTplMainPage;

@ShowcaseHTMLPanel("MainPage.html")
public class MainPageHTMLPanel extends HTMLTemplatePanel {

	public MainPageHTMLPanel(String html) {
		super(html);
		
		dolinkEnglishClick();
	}

	@HTMLWidget
	protected DeckPanel content = new DeckPanel();
	
	
	@HTMLEvent(value = {"linkHome"})
	protected void doHomeClick(){
		
	}
	
	@HTMLEvent(value = {"linkEnglish", "abcdef"})
	protected void dolinkEnglishClick(){
		Window.alert("English");
	}
	
	@HTMLEvent(value = {"linkHTMLTemplate"})
	protected void doHTMLTemplateClick(){
		if (tplMainPage == null){
			tplMainPage = GWT.create(HTMLTplMainPage.class);
			content.add(tplMainPage);
		}
		
		content.showWidget(0);
	}
	
	
	private HTMLTplMainPage tplMainPage;
}
