package com.gwtent.showcase.client;

import com.gwtent.client.template.HTMLTemplate;

/**
 * You can create your annotation based on @HTMLTemplate
 * You can override by redefine the annotation methods
 *  
 * @author JamesLuo.au@gmail.com
 *
 */
@HTMLTemplate(basePath="/com/gwtent/showcase/client/htmls/")
public @interface ShowcaseHTMLPanel {
	/**
	 * The HTML file name based on htmls folder
	 * @return
	 */
	String value();
	
	boolean renameId() default false;
}
