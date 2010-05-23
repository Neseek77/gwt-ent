package com.gwtent.showcase.client;

import com.google.gwt.user.client.ui.Widget;

/**
 * A widget to show something to user
 * @author JLuo
 *
 */
public interface ShowCase {
	public String getCaseName();
	public Widget getWidget();
	
	public String[] getResourceFileNames();
}
