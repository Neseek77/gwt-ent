package com.gwtent.showcase.client.domain;

import java.util.Date;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.gwtent.serialization.client.DataContract;
import com.gwtent.serialization.client.DataMember;
import com.gwtent.validate.client.Validatable;
import com.gwtent.validate.client.constraints.Email;
import com.gwtent.validate.client.constraints.Required;

@Validatable
@GroupSequence({Groups.Billable.class, Groups.BuyInOneClick.class})
@DataContract
public class User implements com.gwtent.showcase.client.uibinder.UserNameEditor.IUserName {

	@Required
  @Size(min=2, max=30)
  @DataMember
  private String firstName;
  
  @Required
  @DataMember
  private String lastName;
  
  @Required
  //@Regular(regex="^([a-zA-Z0-9_.-])+@([a-zA-Z0-9_.-])+\\.([a-zA-Z])+([a-zA-Z])+", message="Please input right email address.")
  //Direct using Regular of Email annotation
  @Email
  @DataMember
  private String email;
  
  @NotNull(groups={Groups.Billable.class})
  private Address address;
  
  @NotNull(groups={Groups.BuyInOneClick.class})
  @NotEmpty(groups={Groups.BuyInOneClick.class})
  @DataMember(type=CreditCard.class)
  private CreditCard defaultCreditCard;
  
  private Date dob;

  public void setFirstName(String name) {
    this.firstName = name;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setAddress(Address address) {
    this.address = address;
  }

  public Address getAddress() {
    return address;
  }

	public void setDefaultCreditCard(CreditCard defaultCreditCard) {
		this.defaultCreditCard = defaultCreditCard;
	}

	public CreditCard getDefaultCreditCard() {
		return defaultCreditCard;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setDob(Date dob) {
		this.dob = dob;
	}

	public Date getDob() {
		return dob;
	}
}
