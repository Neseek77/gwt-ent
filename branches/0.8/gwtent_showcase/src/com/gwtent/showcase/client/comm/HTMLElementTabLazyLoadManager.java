package com.gwtent.showcase.client.comm;

/**
 * 
 * @author James Luo
 *
 * 30/04/2010 4:27:15 PM
 */
public interface HTMLElementTabLazyLoadManager {
	/**
	 * Add a lazy load widget 
	 * @param id when the id of this element clicked or selected and if the widget hasn't create yet, this will create the widget
	 * @param callback, the callback for create a widget
	 */
	void addLazyLoadWidget(String id, LazyLoadWidgetCallback callback);
}
