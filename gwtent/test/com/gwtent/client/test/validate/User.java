package com.gwtent.client.test.validate;

import javax.validation.GroupSequence;
import javax.validation.constraints.Length;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.gwtent.client.validate.Validatable;

@Validatable
@GroupSequence({Groups.Billable.class, Groups.BuyInOneClick.class})
public class User {
	
	public static class CreditCard{
		
	}
  
  @NotNull
  @NotEmpty
  @Length(min=8)
  private String name;
  
  @NotNull(groups={Groups.Billable.class})
  @NotEmpty(groups={Groups.Billable.class})
  @Length(min=8, max=90, groups={Groups.Billable.class})
  private String address;
  
  @NotNull(groups={Groups.BuyInOneClick.class})
  @NotEmpty(groups={Groups.BuyInOneClick.class})
  private CreditCard defaultCreditCard;

  public void setName(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public String getAddress() {
    return address;
  }

	public void setDefaultCreditCard(CreditCard defaultCreditCard) {
		this.defaultCreditCard = defaultCreditCard;
	}

	public CreditCard getDefaultCreditCard() {
		return defaultCreditCard;
	}
}
