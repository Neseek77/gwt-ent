package com.gwtent.showcase.client.uibinder;

import javax.validation.constraints.Size;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.reflection.client.Reflectable;
import com.gwtent.reflection.client.annotations.Reflect_Domain;
import com.gwtent.showcase.client.uibinder.UIBinderWithDataBinder.DataBinder;
import com.gwtent.uibinder.client.UIBind;
import com.gwtent.validate.client.constraints.Required;

/**
 * 
 * @author James Luo
 *
 * 29/06/2010 12:19:05 PM
 */
public class UserNameEditor extends Composite {

	/**
	 * Only an interface that UserNameEditor supported
	 *
	 */
	@Reflect_Domain
	public static interface IUserName{
		public void setFirstName(String name);
		
		@Required
		@Size(min=5, max=30)
	  public String getFirstName();
		
	  public void setLastName(String lastName);
	  
	  @Required
		public String getLastName();
	}
	
	private static UserNameEditorUiBinder uiBinder = GWT
			.create(UserNameEditorUiBinder.class);

	interface UserNameEditorUiBinder extends UiBinder<Widget, UserNameEditor> {
	}
	
	interface DataBinder extends com.gwtent.uibinder.client.DataBinder<UserNameEditor>{
		
	}
	DataBinder dataBinder = GWT.create(DataBinder.class);


	public UserNameEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		dataBinder.bindAll(this);
	}
	
	public void setUserName(IUserName userName) {
		this.userName = userName;
		dataBinder.modelChanged();
	}

	public IUserName getUserName() {
		return userName;
	}

	@UiField
	@UIBind(path = "getUserName().firstName")
	TextBox edtFirstName;
	
	@UiField
	@UIBind(path = "getUserName().lastName")
	TextBox edtLastName;

	private IUserName userName;
}
