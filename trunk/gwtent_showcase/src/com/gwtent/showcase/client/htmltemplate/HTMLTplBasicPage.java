package com.gwtent.showcase.client.htmltemplate;

import java.util.Date;

import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtent.client.template.HTMLEvent;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.BaseShowCasePanel;
import com.gwtent.showcase.client.domain.Country;

@MyHTMLTemplate(value = "/htmltemplate/HtmlTemplate_Basic.html", renameId=true)
public class HTMLTplBasicPage extends BaseShowCasePanel {

	public HTMLTplBasicPage(String html) {
		super(html);
		
		for (Country c : Country.values())
			edtCountry.addItem(c.toString());
		
		edtDOB.setValue(new Date(1980, 1, 1));
	}

	
	@HTMLWidget
	protected TextBox edtFirstName = new TextBox();
	
	//you can setup the dom id if you want 
	@HTMLWidget("edtLastName")
	protected TextBox myLastNameTextBox = new TextBox();
	
	@HTMLEvent(value = {"linkCheckEmail"})
	protected void doCheckEmailClick(){
		Window.alert("LinkCheckEmail clicked");
	}
	
	@HTMLWidget
	protected TextBox edtStreet = new TextBox();
	
	@HTMLWidget
	protected TextBox edtSubTown = new TextBox();
	
	@HTMLWidget
	protected DateBox edtDOB = new DateBox();

	@HTMLWidget
	protected ListBox edtCountry = new ListBox();

	public String[] getResourceFileNames() {
		return new String[]{"HTMLTplBasicPage.java", "../htmls/HtmlTemplate_Basic.html"};
	}

	public String getCaseName() {
		return "Basic HTML Template Page";
	}
	
}
