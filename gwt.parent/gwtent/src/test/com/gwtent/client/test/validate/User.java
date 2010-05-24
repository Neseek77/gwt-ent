package com.gwtent.client.test.validate;

import javax.validation.GroupSequence;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

import com.gwtent.validate.client.Validatable;

@Validatable
@GroupSequence({Groups.Billable.class, Groups.BuyInOneClick.class})
public class User {
	
	public static class CreditCard{
		
	}
  
  @NotNull
  @NotEmpty
  @Size(min=8, max=200)
  private String name;
  
  @NotNull(groups={Groups.Billable.class})
  private Address address;
  
  @NotNull(groups={Groups.BuyInOneClick.class})
  @NotEmpty(groups={Groups.BuyInOneClick.class})
  private CreditCard defaultCreditCard;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
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
}
