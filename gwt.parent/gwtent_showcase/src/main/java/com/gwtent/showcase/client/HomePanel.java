package com.gwtent.showcase.client;

import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.showcase.client.htmltemplate.MyHTMLTemplate;

/**
 * 
 * @author James Luo
 *
 * 29/04/2010 1:27:41 PM
 */

@MyHTMLTemplate("Home.html")
public class HomePanel extends HTMLTemplatePanel{

	public HomePanel(String html) {
		super(html);
	}

	//Nothing here, just display what HTML have
}
