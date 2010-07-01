package com.gwtent.showcase.client.domain;

import java.util.Date;

import com.gwtent.serialization.client.DataContract;
import com.gwtent.serialization.client.DataMember;

@DataContract
public class CreditCard {
	@DataMember
	private String issuingBank;
	 private String creditCardNumber;
	 @DataMember
	 private Date expiresOn;
	 
	public void setIssuingBank(String issuingBank) {
		this.issuingBank = issuingBank;
	}
	public String getIssuingBank() {
		return issuingBank;
	}
	public void setCreditCardNumber(String creditCardNumber) {
		this.creditCardNumber = creditCardNumber;
	}
	public String getCreditCardNumber() {
		return creditCardNumber;
	}
	public void setExpiresOn(Date expiresOn) {
		this.expiresOn = expiresOn;
	}
	public Date getExpiresOn() {
		return expiresOn;
	}
}
