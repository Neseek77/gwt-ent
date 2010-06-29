package com.gwtent.showcase.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.domain.User;
import com.gwtent.uibinder.client.UIBind;

/**
 * 
 * @author James Luo
 *
 * 29/06/2010 1:45:42 PM
 */
public class CreateYourBinder1 extends Composite implements ShowCase{

	private static CreateYourBinder1UiBinder uiBinder = GWT
			.create(CreateYourBinder1UiBinder.class);

	interface CreateYourBinder1UiBinder extends
			UiBinder<Widget, CreateYourBinder1> {
	}
	
	interface DataBinder extends com.gwtent.uibinder.client.DataBinder<CreateYourBinder1>{
	}
	DataBinder dataBinder = GWT.create(DataBinder.class);

	@UiField
	@UIBind(path="getUser().dob")
	MyDateBox myDateBox;
	
	@UiField
	@UIBind(path="getUser().email")
	ASmartClientEditBox smartClientEditBox;
	
	private User user;
	
	@UiField
	Button button;

	public CreateYourBinder1() {
		initWidget(uiBinder.createAndBindUi(this));
		
		dataBinder.bindAll(this);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		dataBinder.validate(true);
	}

	public void setUser(User user) {
		this.user = user;
		dataBinder.modelChanged();
	}

	public User getUser() {
		return user;
	}

	
	//------ShowCase code---------------
	@Override
	public String getCaseName() {
		return "Create Your Binder";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[]{"CreateYourBinder1.java", "CreateYourBinder1.ui.xml", "MyDateBox.java", "ASmartClientEditBox.java", "ASmartClientEditBoxBinder.java"};
	}

	@Override
	public Widget getShowCaseWidget() {
		return this;
	}

}
