package com.gwtent.showcase.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.gwtent.htmltemplate.client.HTMLEvent;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.SubContent;
import com.gwtent.showcase.client.comm.AbsFrameTemplatePage;
import com.gwtent.showcase.client.comm.HTMLElementTabLazyLoadManager;
import com.gwtent.showcase.client.comm.LazyLoadWidgetCallback;
import com.gwtent.showcase.client.domain.UserFactory;

@HTMLTemplate(value = "com/gwtent/showcase/public/uibinder/UIBinder.html", renameId = false)
public class UIBinderPage extends AbsFrameTemplatePage {

	public UIBinderPage(String html) {
		super(html);
	}

	@Override
	protected void doInitLazyLoadWidgets(
			HTMLElementTabLazyLoadManager lazyLoadManager) {
		lazyLoadManager.addLazyLoadWidget("linkUIBByCode", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				DataBinderByCode result = new DataBinderByCode();
				result.setUser(UserFactory.getInstance().getNextUser());
				return result;
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkUIBSmall", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return GWT.create(UIBinderSmallExample.class);
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkUIBGWTUIBinder", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				UIBinderWithDataBinder result = new UIBinderWithDataBinder();
				result.setUser(UserFactory.getInstance().getNextUser());
				return result;
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkUIBCreateYourBinder1", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				CreateYourBinder1 result = new CreateYourBinder1();
				result.setUser(UserFactory.getInstance().getEmpty());
				return result;
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkUIBCreateYourBinder", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new CreateYourBinder();
			}});
	}
}
