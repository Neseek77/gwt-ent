package com.gwtent.showcase.client;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.template.HTMLTemplatePanel;

public abstract class BaseShowCasePanel extends HTMLTemplatePanel implements ShowCase{

	public BaseShowCasePanel(String html) {
		super(html);
	}


	public Widget getWidget() {
		return this;
	}
	

}
