package com.gwtent.showcase.client.home;

import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.comm.AbsFrameTemplatePage;
import com.gwtent.showcase.client.comm.HTMLElementTabLazyLoadManager;
import com.gwtent.showcase.client.comm.LazyLoadWidgetCallback;
import com.gwtent.showcase.client.comm.OverviewPage;
import com.gwtent.showcase.client.htmltemplate.MyHTMLTemplate;

/**
 * 
 * @author James Luo
 *
 * 29/04/2010 1:27:41 PM
 */

@MyHTMLTemplate("home/Home.html")
public class HomePanel extends AbsFrameTemplatePage{

	public HomePanel(String html) {
		super(html);
	}

	@Override
	protected void doInitLazyLoadWidgets(
			HTMLElementTabLazyLoadManager lazyLoadManager) {
		lazyLoadManager.addLazyLoadWidget("linkHomeOverview", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new OverviewPage("notfinish");
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkHomeSerialization", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new SerializationJson();
			}});
	}

	//Nothing here, just display what HTML have
}
