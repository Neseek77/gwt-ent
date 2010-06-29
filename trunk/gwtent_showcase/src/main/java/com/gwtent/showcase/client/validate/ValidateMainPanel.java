package com.gwtent.showcase.client.validate;

import com.google.gwt.core.client.GWT;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.comm.AbsFrameTemplatePage;
import com.gwtent.showcase.client.comm.HTMLElementTabLazyLoadManager;
import com.gwtent.showcase.client.comm.LazyLoadWidgetCallback;
import com.gwtent.showcase.client.comm.OverviewPage;
import com.gwtent.showcase.client.domain.UserFactory;
import com.gwtent.showcase.client.htmltemplate.HTMLTplUIBindPage;
import com.gwtent.showcase.client.htmltemplate.MyHTMLTemplate;
import com.gwtent.showcase.client.uibinder.UIBinderWithDataBinder;

/**
 * 
 * @author James Luo
 *
 * 29/04/2010 1:27:41 PM
 */

@MyHTMLTemplate("validate/Validate.html")
public class ValidateMainPanel extends AbsFrameTemplatePage{

	public ValidateMainPanel(String html) {
		super(html);
	}

	protected void doInitLazyLoadWidgets(
			HTMLElementTabLazyLoadManager lazyLoadManager) {
		lazyLoadManager.addLazyLoadWidget("linkValOverview", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new OverviewPage("notfinish");
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkValBasic", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new ValidateByCode();
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkValUIBind", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				UIBinderWithDataBinder result = new UIBinderWithDataBinder();
				result.setUser(UserFactory.getInstance().getNextUser());
				return result;
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkValUIBindHTML", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				HTMLTplUIBindPage uibindPage = GWT.create(HTMLTplUIBindPage.class);
				
				//Get some object from server, and refresh the UI
				uibindPage.setUser(UserFactory.getInstance().getJames());
				uibindPage.modelChanged();
				
				return uibindPage;
			}});
		
		lazyLoadManager.addLazyLoadWidget("linkValCreateYourValidate", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new CreateYourValidator();
			}});

	}

	//Nothing here, just display what HTML have
}
