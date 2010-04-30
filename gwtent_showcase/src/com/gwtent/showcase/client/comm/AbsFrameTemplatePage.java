package com.gwtent.showcase.client.comm;

import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
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

	protected abstract void doInitLazyLoadWidgets(HTMLElementTabLazyLoadManager lazyLoadManager);
	
	public void addLazyLoadWidget(String id, LazyLoadWidgetCallback callback) {
		tabManager.addTab(this.getElementById(id), callback);
	}

}
