package com.gwtent.showcase.client.htmltemplate;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtent.client.template.HTMLEvent;
import com.gwtent.client.template.HTMLTemplatePanel;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.client.template.UIBind;
import com.gwtent.client.widget.EnumListBox;
import com.gwtent.showcase.client.BaseShowCasePanel;
import com.gwtent.showcase.client.domain.Country;
import com.gwtent.showcase.client.domain.User;
import com.gwtent.showcase.client.domain.UserFactory;

@MyHTMLTemplate(value = "HtmlTemplate_UIBind_Table.html", renameId=true)
public class HTMLTplUIBindPage extends BaseShowCasePanel {
	
	protected User user;

	public HTMLTplUIBindPage(String html) {
		super(html);
	}

	
	@HTMLWidget
	@UIBind(path = "user.firstName")
	protected TextBox edtFirstName = new TextBox();
	
	//you can setup the dom id if you want 
	@HTMLWidget("edtLastName")
	@UIBind(path = "user.lastName")
	protected TextBox myLastNameTextBox = new TextBox();
	
	
	@HTMLWidget
	@UIBind(path = "getUser().email")
	protected TextBox edtEmail = new TextBox();
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		//this.modelChanged();  
	}


	//Support property path as well
	@HTMLWidget
	@UIBind(path = "user.address.street")
	protected TextBox edtStreet = new TextBox();
	
	@HTMLWidget
	@UIBind(path = "user.address.subTown")
	protected TextBox edtSubTown = new TextBox();
	
	@HTMLWidget
	@UIBind(path = "user.dob")
	protected DateBox edtDOB = new DateBox();

	@HTMLWidget
	@UIBind(path = "user.address.country")
	protected EnumListBox<Country> edtCountry = new EnumListBox<Country>();
	
	@HTMLWidget
	protected Button btnValidate = new Button("Validate", new ClickHandler(){
		public void onClick(ClickEvent event) {
			validate(true);
		}});
	
	@HTMLWidget
	protected Button btnSubmit = new Button("Submit", new ClickHandler(){
		public void onClick(ClickEvent event) {
			validate(true);
		}});
	
	
	@HTMLWidget
	protected Button btnChangeDataByCode = new Button("Change Model By Code", new ClickHandler(){
		public void onClick(ClickEvent event) {
			user = UserFactory.getInstance().getNextUser();
			modelChanged();
		}});

	public String[] getResourceFileNames() {
		return new String[]{"HTMLTplUIBindPage.java"};
	}
	
	public String getCaseName() {
		return "HTML Template Page With UIBind and Validator";
	}
}
