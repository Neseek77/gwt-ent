package com.gwtent.showcase.client.comm;

import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.showcase.client.SubContent;

/**
 * 
 * @author James Luo
 *
 * 30/04/2010 4:24:20 PM
 */
public abstract class AbsFrameTemplatePage extends HTMLTemplatePanel implements HTMLElementTabLazyLoadManager {

	public AbsFrameTemplatePage(String html) {
		super(html);
	}
	
	@HTMLWidget
	public SubContent subContentRef = new SubContent();
	
	protected TabsManager tabManager = new TabsManager(subContentRef);
	
	protected void doAfterBinderAllEditors(){
		doInitLazyLoadWidgets(this);
	}

	/**
	 * In this class, add lazy widget by call something like this:
	 * 
	 * <p>lazyLoadManager.addLazyLoadWidget(HTMLElementID, new LazyLoadWidgetCallback(){
	 * <p>			public ShowCase getWidget() {
	 * <p>				return A widget;
	 * <p>			}});
	 * 
	 * @param lazyLoadManager
	 */
	protected abstract void doInitLazyLoadWidgets(HTMLElementTabLazyLoadManager lazyLoadManager);
	
	public void addLazyLoadWidget(String id, LazyLoadWidgetCallback callback) {
		tabManager.addTab(this.getElementById(id), callback);
	}

}
