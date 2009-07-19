package com.gwtent.showcase.client;

import com.gwtent.client.template.HTMLTemplate;

@HTMLTemplate(basePath="/com/gwtent/showcase/client/htmls/", renameId=false)
public @interface ShowcaseHTMLPanel {
	/**
	 * The HTML file name based on htmls folder
	 * @return
	 */
	String value();
}
