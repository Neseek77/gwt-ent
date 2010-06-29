package com.gwtent.showcase.client.validate;

import java.util.Date;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;
import com.google.gwt.user.datepicker.client.DateBox.DefaultFormat;
import com.gwtent.reflection.client.annotations.Reflect_Domain;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.uibinder.client.UIBind;
import com.gwtent.validate.client.constraints.Required;

/**
 * 
 * @author James Luo
 *
 * 28/06/2010 8:32:50 PM
 */
public class CreateYourValidator extends Composite implements ShowCase {

	private static CreateYourValidatorUiBinder uiBinder = GWT
			.create(CreateYourValidatorUiBinder.class);

	interface CreateYourValidatorUiBinder extends
			UiBinder<Widget, CreateYourValidator> {
	}
	
	interface DataBinder extends com.gwtent.uibinder.client.DataBinder<CreateYourValidator>{
		
	}
	DataBinder dataBinder = GWT.create(DataBinder.class);
	
	
	@Reflect_Domain
	public static class MyDomain{
		@Required
		@MyDOB
		private Date dob;

		public void setDob(Date dob) {
			this.dob = dob;
		}

		public Date getDob() {
			return dob;
		}
	}
	
	MyDomain domain = new MyDomain();

	@UiField
	@UIBind(path = "domain.dob")
	DateBox edtDOB;

	public CreateYourValidator() {
		initWidget(uiBinder.createAndBindUi(this));
		
		edtDOB.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
		edtDOB.setTitle("dd/MM/yyyy");
		
		domain.setDob(new Date());
		dataBinder.bindAll(this, true);
	}


	@Override
	public String getCaseName() {
		return "Create Your Validtor";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[] {"CreateYourValidator.java", "CreateYourValidator.ui.xml", "MyDOB.java", "MyDOBValidator.java"};
	}

	@Override
	public Widget getShowCaseWidget() {
		return this;
	}

}
