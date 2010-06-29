package com.gwtent.showcase.client.validate;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.constraints.Size;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.reflection.client.Reflectable;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.validate.client.ValidatorFactory;
import com.gwtent.validate.client.constraints.Email;
import com.gwtent.validate.client.constraints.Required;

/**
 * 
 * @author James Luo
 *
 * 23/06/2010 4:44:38 PM
 */
public class ValidateByCode extends Composite implements ShowCase {

	private static ValidateByCodeUiBinder uiBinder = GWT
			.create(ValidateByCodeUiBinder.class);

	interface ValidateByCodeUiBinder extends UiBinder<Widget, ValidateByCode> {
	}

	/**
	 * This is your domain class, the only thing you need do is to make it Reflectable.
	 */
	@Reflectable
	public static class ClassToValidate{	
		
		@Required
	  @Size(min=2, max=30)
	  private String firstName;
	  
	  @Required
	  private String lastName;
	  
	  //@Regular(regex="^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+", message="Please input right email address.")
	  //Direct using Regular of Email annotation
	  @Email
	  private String email;

		public void setEmail(String email) {
			this.email = email;
		}

		public String getEmail() {
			return email;
		}

		public void setLastName(String lastName) {
			this.lastName = lastName;
		}

		public String getLastName() {
			return lastName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getFirstName() {
			return firstName;
		}
	}
	
	
	@UiField
	Button button;

	public ValidateByCode() {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText("Validate");
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		//Create domain object and setup the values
		ClassToValidate obj = new ClassToValidate();
		obj.setFirstName("First Name");
		obj.setLastName(null); //
		obj.setEmail("not a good email address");
		
		//Validate it by GWT validator
		Set<ConstraintViolation<ClassToValidate>> validateResult = ValidatorFactory.getGWTValidator().validate(obj);
		
		//Display the result
		String errorMsg = "";
		for (ConstraintViolation<ClassToValidate> violation : validateResult){
			errorMsg += violation.getPropertyPath() + ":" + violation.getMessage() + " Value: " + violation.getInvalidValue() + "\n";
		}
		Window.alert(errorMsg);
	}

	@Override
	public String getCaseName() {
		return "Validate By Code";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[]{"ValidateByCode.java"};
	}
	
	public Widget getShowCaseWidget(){
		return this;
	}

}
