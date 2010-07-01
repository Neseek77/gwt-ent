package com.gwtent.showcase.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.DeckPanel;
import com.gwtent.htmltemplate.client.HTMLEvent;
import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.htmltemplate.client.HTMLTemplatePanel;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.showcase.client.aop.AOPMainPage;
import com.gwtent.showcase.client.comm.TabsManager;
import com.gwtent.showcase.client.home.HomePanel;
import com.gwtent.showcase.client.htmltemplate.HTMLTplMainPage;
import com.gwtent.showcase.client.reflection.ReflectionPage;
import com.gwtent.showcase.client.uibinder.UIBinderPage;
import com.gwtent.showcase.client.validate.ValidateMainPanel;
               
@HTMLTemplate("com/gwtent/showcase/public/MainPageHTMLPanel.html")
public class MainPageHTMLPanel extends HTMLTemplatePanel {

	public MainPageHTMLPanel(String html) {
		super(html);
	}

	@HTMLWidget
	protected DeckPanel content = new DeckPanel();
	
	
	@HTMLEvent(value = {"linkEnglish"})
	protected void dolinkEnglishClick(){
		openLocal("en");
	}
	
	@HTMLEvent(value = {"linkChinese"})
	protected void dolinkChineseClick(){
		openLocal("zh");
	}
	

	protected void doAfterBinderAllEditors(){
		linkHome = this.getElementById("linkHome");
		linkReflection = this.getElementById("linkReflection");
		linkUIBinding = this.getElementById("linkUIBinding");
		linkHTMLTemplate = this.getElementById("linkHTMLTemplate");
		linkAOP = this.getElementById("linkAOP");
		linkValidation = this.getElementById("linkValidation");
		
		TabsManager tabs = new TabsManager(content);
		tabs.addTab(linkHome, tplHome);
		tabs.addTab(linkAOP, aopMainPage);
		tabs.addTab(linkHTMLTemplate, tplHtmlTemplate);
		tabs.addTab(linkReflection, tplReflection);
		tabs.addTab(linkValidation, tplValidate);
		tabs.addTab(linkUIBinding, tplUIBinder);
  }
	
	Element linkHome;
	Element linkReflection;
	Element linkUIBinding;
	Element linkHTMLTemplate;
	Element linkAOP;
	Element linkValidation;
	
	private HTMLTplMainPage tplHtmlTemplate = GWT.create(HTMLTplMainPage.class);
	private ReflectionPage tplReflection = GWT.create(ReflectionPage.class);
	private UIBinderPage tplUIBinder = GWT.create(UIBinderPage.class);
	private AOPMainPage aopMainPage = GWT.create(AOPMainPage.class);
	private HomePanel tplHome = GWT.create(HomePanel.class);
	private ValidateMainPanel tplValidate = GWT.create(ValidateMainPanel.class);
	
	private void openLocal(String localeName){
		Window.open(getHostPageLocation() + "?locale=" + localeName, "_self",
    "");
	}
	//private HTMLTplMainPage tplMainPage;
	
	private static native String getHostPageLocation()
  /*-{
    var s = $doc.location.href;

    // Pull off any hash.
    var i = s.indexOf('#');
    if (i != -1)
      s = s.substring(0, i);

    // Pull off any query string.
    i = s.indexOf('?');
    if (i != -1)
      s = s.substring(0, i);

    // Ensure a final slash if non-empty.
    return s;
  }-*/;
}
