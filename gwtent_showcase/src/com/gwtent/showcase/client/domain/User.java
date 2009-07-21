package com.gwtent.showcase.client.domain;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import com.gwtent.client.validate.Validatable;
import com.gwtent.client.validate.constraints.Required;

@Validatable
@GroupSequence({Groups.Billable.class, Groups.BuyInOneClick.class})
public class User {

	@Required
  @Size(min=8, max=200)
  private String firstName;
  
  @Required
  private String lastName;
  
  @NotNull(groups={Groups.Billable.class})
  private Address address;
  
  @NotNull(groups={Groups.BuyInOneClick.class})
  @NotEmpty(groups={Groups.BuyInOneClick.class})
  private CreditCard defaultCreditCard;

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
}
