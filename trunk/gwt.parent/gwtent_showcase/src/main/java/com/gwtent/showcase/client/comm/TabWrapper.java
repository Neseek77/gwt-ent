package com.gwtent.showcase.client.comm;

import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Widget;

/**
 * 
 * @author James Luo
 *
 * 07/04/2010 5:01:58 PM
 */
public class TabWrapper {
	private final Element element;
	private int tabIndex = -1;
	private Widget widget;
	
	private LazyLoadWidgetCallback lazyWidgetCreateListener;
	
	/**
	 * widget can be null, if it's null, this class with using LazyWidgetCreateCallback to get widget
	 * @param element
	 * @param tabIndex
	 * @param widget
	 */
	public TabWrapper(Element element, Widget widget){
		this.element = element;
		this.setWidget(widget);
	}

	public void setTabIndex(int tabIndex) {
		this.tabIndex = tabIndex;
	}

	public int getTabIndex() {
		return tabIndex;
	}

	public Element getElement() {
		return element;
	}

	public void setWidget(Widget widget) {
		this.widget = widget;
	}

	public Widget getWidget() {
		if (widget == null)
			widget = lazyWidgetCreateListener.getWidget();
		
		return widget;
	}

	public void setLazyWidgetCreateCallback(LazyLoadWidgetCallback lazyWidgetCreateCallback) {
		this.lazyWidgetCreateListener = lazyWidgetCreateCallback;
	}

	public LazyLoadWidgetCallback getLazyWidgetCreateCallback() {
		return lazyWidgetCreateListener;
	}
}
