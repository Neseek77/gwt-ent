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
import com.gwtent.showcase.client.domain.UserFactory;
import com.gwtent.uibinder.client.UIBind;

/**
 * 
 * @author James Luo
 *
 * 29/06/2010 12:32:49 PM
 */
public class CreateYourBinder extends Composite implements ShowCase {

	private static CreateYourBinderUiBinder uiBinder = GWT
			.create(CreateYourBinderUiBinder.class);

	interface CreateYourBinderUiBinder extends UiBinder<Widget, CreateYourBinder> {
	}
	
	interface DataBinder extends com.gwtent.uibinder.client.DataBinder<CreateYourBinder>{
	}
	DataBinder dataBinder = GWT.create(DataBinder.class);

	@UiField
	Button button;
	
	@UiField
	@UIBind(path="james")
	UserNameEditor edtJames;
	
	@UiField
	@UIBind(path="lei")
	UserNameEditor edtLei;
	
	@UiField
	@UIBind(path="empty")
	UserNameEditor edtEmpty;
	
	
	User james = UserFactory.getInstance().getJames();
	User lei = UserFactory.getInstance().getLei();
	User empty = UserFactory.getInstance().getEmpty();

	public CreateYourBinder() {
		initWidget(uiBinder.createAndBindUi(this));
		
		dataBinder.bindAll(this, true);
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		dataBinder.validate(true);
	}

	
	//------ ShowCase functions
	@Override
	public String getCaseName() {
		return "Create Your Binder";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[]{"CreateYourBinder.java", "CreateYourBinder.ui.xml", "UserNameEditor.java", "UserNameEditorBinder.java"};
	}

	@Override
	public Widget getShowCaseWidget() {
		return this;
	}
	//------ End ShowCase

}
