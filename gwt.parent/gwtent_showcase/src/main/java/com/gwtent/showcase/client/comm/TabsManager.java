package com.gwtent.showcase.client.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.DeckPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.utils.NotifyListener;

/**
 * The {@link TabWrapper} TabWrapper management.
 * 
 * <p>This class made for manage any HTML Element to a Tab
 * 
 * <p>Using ".keepActive" as the CSS once a Element is selected
 * 
 * @author James Luo
 *
 * 07/04/2010 5:04:09 PM
 */
public class TabsManager {
	private List<TabWrapper> tabs = new ArrayList<TabWrapper>();
	private DeckPanel pnlContents;

	public TabsManager(DeckPanel pnlContents){
		this.pnlContents = pnlContents;
	}

	public List<TabWrapper> getTabs() {
		return tabs;
	}
	
	/**
	 * Add a widget to tab. 
	 * @param tabElement, the element of tab bar button
	 * @param widget, the widget which will display once user click "tabElement"
	 * @return
	 */
	public TabWrapper addTab(Element tabElement, Widget widget){
		TabWrapper tab = new TabWrapper(tabElement, widget);
		return addTab(tabElement, tab);
	}

	private TabWrapper addTab(Element tabElement, TabWrapper tab) {
		tabs.add(tab);
		
		DOM.sinkEvents(tabElement, Event.ONCLICK);
    DOM.setEventListener(tabElement, new com.google.gwt.user.client.EventListener() {
      public void onBrowserEvent(Event event) {
        if (DOM.eventGetType(event) == Event.ONCLICK) {
        	selectTab(DOM.eventGetTarget(event));
        }
      }
    });
		
		return tab;
	}
	
	public TabWrapper addTab(Element tabElement, LazyLoadWidgetCallback lazyWidgetCreateCallback){
		TabWrapper tab = new TabWrapper(tabElement, null);
		tab.setLazyWidgetCreateCallback(lazyWidgetCreateCallback);
		return addTab(tabElement, tab);
	}
	
	public void selectTab(TabWrapper tab){
		if (tab.getTabIndex() < 0){
			Widget widget = tab.getWidget();
			pnlContents.add(widget);
			tab.setTabIndex(pnlContents.getWidgetCount() - 1);
		}
		
		int index = tab.getTabIndex();
		if (pnlContents.getVisibleWidget() != index){
			pnlContents.showWidget(index);
			
			for(TabWrapper t : tabs){
				if (t.getTabIndex() == index){
					t.getElement().addClassName("keepActive");
				}else{
					t.getElement().removeClassName("keepActive");
				}
			}
		}
	}
	
	/**
	 * The index by added order
	 * @param index
	 */
	public void selectTab(int index) {
		selectTab(tabs.get(index));
	}
	
	public void selectTab(Element tabElement) {
		TabWrapper tab = this.getTabByElement(tabElement);
		if (tab != null){
			this.selectTab(tab);
		}
		else
			throw new NoSuchElementException();
	}
	
	public TabWrapper getTabByElement(Element tabElement){
		for (TabWrapper tab : tabs){
			if (tab.getElement().equals(tabElement))
				return tab;
		}
		
		return null;
	}
	
	public TabWrapper getTabByContentWidget(Widget widget){
		for (TabWrapper tab : tabs){
			if (tab.getWidget().equals(widget))
				return tab;
		}
		
		return null;
	}
	
	public DeckPanel getPnlContents() {
		return pnlContents;
	}
	
}
