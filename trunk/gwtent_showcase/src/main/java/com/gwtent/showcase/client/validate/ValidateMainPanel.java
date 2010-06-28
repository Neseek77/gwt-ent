package com.gwtent.showcase.client.validate;

import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
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
		
		lazyLoadManager.addLazyLoadWidget("linkValCreateYourValidate", new LazyLoadWidgetCallback(){

			public ShowCase getWidget() {
				return new CreateYourValidator();
			}});

	}

	//Nothing here, just display what HTML have
}
