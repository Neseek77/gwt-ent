package com.gwtent.showcase.client.reflection;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.showcase.client.comm.AbsFrameTemplatePage;
import com.gwtent.showcase.client.comm.HTMLElementTabLazyLoadManager;
import com.gwtent.showcase.client.comm.LazyLoadWidgetCallback;

@HTMLTemplate(value = "com/gwtent/showcase/public/reflection/Reflection.html", renameId = false)
public class ReflectionPage extends AbsFrameTemplatePage {

	public ReflectionPage(String html) {
		super(html);
	}
	
	@Override
	protected void doInitLazyLoadWidgets(
			HTMLElementTabLazyLoadManager lazyLoadManager) {
		lazyLoadManager.addLazyLoadWidget("linkRefBasic", new LazyLoadWidgetCallback(){
			public Widget getWidget() {
				return GWT.create(ReflectBasicPage.class);
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkRefEnum", new LazyLoadWidgetCallback(){
			public Widget getWidget() {
				return GWT.create(ReflectEnumPage.class);
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkRefDomain", new LazyLoadWidgetCallback(){
			public Widget getWidget() {
				return GWT.create(ReflectDomainPage.class);
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkRefControlSize", new LazyLoadWidgetCallback(){
			public Widget getWidget() {
				return GWT.create(ReflectControlSizePage.class);
			}});
	}
}
