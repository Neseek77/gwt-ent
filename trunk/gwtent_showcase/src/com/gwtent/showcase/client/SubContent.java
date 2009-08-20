package com.gwtent.showcase.client;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.DeckPanel;
import com.gwtent.showcase.client.htmltemplate.HTMLTplBasicPage;

public class SubContent extends DeckPanel {
	
	private Map<ShowCase, Integer> contentMap = new HashMap<ShowCase, Integer>();
	
	public void addShowCase(ShowCase showCase){
		ContentWidget content = new ContentWidget(showCase);
		
		this.add(content);
		contentMap.put(showCase, this.getWidgetIndex(content));
	}
	
	public void showShowCase(ShowCase showCase){
		this.showWidget(contentMap.get(showCase));
	}
}
