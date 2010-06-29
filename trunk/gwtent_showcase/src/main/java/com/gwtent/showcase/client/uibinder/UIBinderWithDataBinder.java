package com.gwtent.showcase.client.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.domain.Country;
import com.gwtent.showcase.client.domain.User;
import com.gwtent.showcase.client.domain.UserFactory;
import com.gwtent.uibinder.client.UIBind;
import com.gwtent.widget.client.EnumListBox;

/**
 * What's the different between GWT HTMLTemplate and GWT UIBinder?
 * 
 * <P>1, Changed all @HTMLWidget to @UiField
 * <p>2, In HTMLTemplate, you control how to create your widget, in GWT UIBinder, widget are created automatically
 * <p>3, In HTMLTemplate, you control how to handle event, in GWT UIBinder, it's direct support it.
 *
 * <p>How to make DataBinder and Validate worked with GWT UIBinder</p>
 * <p>
 *   <ul>
 *     <li>Create your DataBinder interface, it's looks like how UIBinder does. Please see "DataBinder" in this class</li>
 *     <li>Create the instance of your DataBinder. Please note, this is NOT static, this is the difference between GWT UIBinder and GWTENT DataBinder</li>
 *     <li>In your constructor, invoke dataBinder.bindAll(this);</li>
 *     <li>When you module changed by code, invoke dataBinder.modelChanged();</li>
 *     <li>When you need validate, invoke dataBinder.validate();</li>
 *   </ul>
 * </p>
 *
 * 29/06/2010 10:03:19 AM
 */
public class UIBinderWithDataBinder extends Composite implements ShowCase{

	private static UIBinderWithDataBinderUiBinder uiBinder = GWT
			.create(UIBinderWithDataBinderUiBinder.class);

	interface UIBinderWithDataBinderUiBinder extends
			UiBinder<Widget, UIBinderWithDataBinder> {
	}

	interface DataBinder extends com.gwtent.uibinder.client.DataBinder<UIBinderWithDataBinder>{
		
	}
	DataBinder dataBinder = GWT.create(DataBinder.class);
	
	User user;
	

	@UiField
	@UIBind(path = "user.firstName")
	protected TextBox edtFirstName;
	
	//you can setup the dom id if you want, in GWT UIBinder you can't 
	@UiField
	@UIBind(path = "user.lastName")
	protected TextBox edtLastName;
	
	
	@UiField
	@UIBind(path = "getUser().email")
	protected TextBox edtEmail;
	

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
		
		dataBinder.modelChanged();  
	}


	//Support property path as well
	@UiField
	@UIBind(path = "user.address.street")
	protected TextBox edtStreet;
	
	@UiField
	@UIBind(path = "user.address.subTown")
	protected TextBox edtSubTown;
	
	@UiField
	@UIBind(path = "user.dob")
	protected DateBox edtDOB;

	@UiField
	@UIBind(path = "user.address.country")
	protected EnumListBox<Country> edtCountry;
	
	@UiField
	protected Button btnValidate;
	
	@UiField
	protected Button btnSubmit;
	
	
	@UiField
	protected Button btnChangeDataByCode;
	

	public UIBinderWithDataBinder() {
		initWidget(uiBinder.createAndBindUi(this));

		dataBinder.bindAll(this);
	}

	@UiHandler("btnChangeDataByCode")
	void onbtnChangeDataByCodeClick(ClickEvent e) {
		user = UserFactory.getInstance().getNextUser();
		dataBinder.modelChanged();
	}
	
	@UiHandler("btnValidate")
	void onbtnValidateClick(ClickEvent e) {
		dataBinder.validate(true);
	}
	
	@UiHandler("btnSubmit")
	void onbtnSubmitClick(ClickEvent e) {
		dataBinder.validate(true);
	}

	@Override
	public String getCaseName() {
		return "GWT UIBinder With GWTENT DataBinder";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[]{"UIBinderWithDataBinder.java", "UIBinderWithDataBinder.ui.xml"};
	}

	@Override
	public Widget getShowCaseWidget() {
		return this;
	}

}
