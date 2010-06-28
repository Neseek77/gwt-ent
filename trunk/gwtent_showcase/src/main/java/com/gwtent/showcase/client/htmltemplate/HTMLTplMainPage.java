package com.gwtent.showcase.client.htmltemplate;

import com.google.gwt.core.client.GWT;
import com.gwtent.htmltemplate.client.HTMLEvent;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.SubContent;
import com.gwtent.showcase.client.comm.AbsFrameTemplatePage;
import com.gwtent.showcase.client.comm.HTMLElementTabLazyLoadManager;
import com.gwtent.showcase.client.comm.LazyLoadWidgetCallback;
import com.gwtent.showcase.client.comm.OverviewPage;
import com.gwtent.showcase.client.domain.UserFactory;

@MyHTMLTemplate("/htmltemplate/HtmlTemplate.html")
public class HTMLTplMainPage extends AbsFrameTemplatePage {

	public HTMLTplMainPage(String html) {
		super(html);
	}

	@Override
	protected void doInitLazyLoadWidgets(
			HTMLElementTabLazyLoadManager lazyLoadManager) {
		lazyLoadManager.addLazyLoadWidget("linkTplOverview", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new OverviewPage("notfinish");
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkTplBasic", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return GWT.create(HTMLTplBasicPage.class);
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkTplUIBind", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				HTMLTplUIBindPage uibindPage = GWT.create(HTMLTplUIBindPage.class);
				
				//Get some object from server, and refresh the UI
				uibindPage.setUser(UserFactory.getInstance().getJames());
				uibindPage.modelChanged();
				
				return uibindPage;
			}});
	}
}
