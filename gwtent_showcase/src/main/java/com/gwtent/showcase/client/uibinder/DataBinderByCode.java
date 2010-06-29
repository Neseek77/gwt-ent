package com.gwtent.showcase.client.uibinder;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.htmltemplate.client.HTMLWidget;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.domain.User;
import com.gwtent.showcase.client.domain.UserFactory;
import com.gwtent.uibinder.client.ModelRootAccessor;
import com.gwtent.uibinder.client.UIBinderManager;

/**
 * 
 * @author James Luo
 *
 * 29/06/2010 11:01:08 AM
 */
public class DataBinderByCode implements ShowCase {
	
	public DataBinderByCode(){
		mainPanel.setWidth("100%");
		
		mainPanel.add(new Label("If you didn't use GWT UIBinder or GWTENT HTMLTemplate, you can still using our binder system."));
		
		mainPanel.add(grid);
		mainButtonPanel.add(btnValidate);
		mainButtonPanel.add(btnSubmit);
		mainButtonPanel.add(btnChangeDataByCode);
		mainPanel.add(mainButtonPanel);
		
		grid.setText(0, 0, "First Name:");
		grid.setWidget(0, 1, edtFirstName);
		
		grid.setText(1, 0, "Last Name:");
		grid.setWidget(1, 1, edtLastName);
		
		grid.setText(2, 0, "Email: ");
		grid.setWidget(2, 1, edtEmail);
		
		/**
		 * The main code is here, tell binder manager how to binder
		 */
		dateBinder = new UIBinderManager<DataBinderByCode>(){

			@Override
			public void bindAll(DataBinderByCode owner) {
				this.addBinder(edtFirstName, "firstName", false, User.class, new ModelRootAccessor(){
					public String getRootPath() {
						return "firstName";
					}
					public Object getValue() {
						return user;
					}
					public void setValue(Object value) {
						//don't need if it's a domain object, because you only change user.firstName, not user itself.
						//Only need this when you binder to a class level variable. 
					}}, true);
				
				this.addBinder(edtLastName, "lastName", false, User.class, new ModelRootAccessor(){
					public String getRootPath() {
						return "lastName";
					}
					public Object getValue() {
						return user;
					}
					public void setValue(Object value) {
					}}, true);
				
				this.addBinder(edtEmail, "email", false, User.class, new ModelRootAccessor(){
					public String getRootPath() {
						return "email";
					}
					public Object getValue() {
						return user;
					}
					public void setValue(Object value) {
					}}, true);
			}};
			
			dateBinder.bindAll(this);
	}

	
	private final UIBinderManager<DataBinderByCode> dateBinder;
	
	private User user;
	
	private VerticalPanel mainPanel = new VerticalPanel();
	private HorizontalPanel mainButtonPanel = new HorizontalPanel();
	private FlexTable grid = new FlexTable();
	private TextBox edtFirstName = new TextBox();
	private TextBox edtLastName = new TextBox();
	private TextBox edtEmail = new TextBox();
	

	protected Button btnValidate = new Button("Validate", new ClickHandler(){
		public void onClick(ClickEvent event) {
			dateBinder.validate(true);
		}});
	
	protected Button btnSubmit = new Button("Submit", new ClickHandler(){
		public void onClick(ClickEvent event) {
			dateBinder.validate(true);
		}});
	
	
	protected Button btnChangeDataByCode = new Button("Change Model By Code", new ClickHandler(){
		public void onClick(ClickEvent event) {
			user = UserFactory.getInstance().getNextUser();
			dateBinder.modelChanged();
		}});

	
	@Override
	public String getCaseName() {
		return "DataBinder By Code";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[]{"DataBinderByCode.java"};
	}

	@Override
	public Widget getShowCaseWidget() {
		return mainPanel;
	}

	public void setUser(User user) {
		this.user = user;
		dateBinder.modelChanged();
	}

	public User getUser() {
		return user;
	}

}
