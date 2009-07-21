package com.gwtent.showcase.client.domain;

import javax.validation.constraints.NotNull;

import com.gwtent.client.reflection.annotations.Reflect_Domain;

@Reflect_Domain
public class Address {
	private String street;
	private String subTown;
	
	@NotNull
	private String postCode;
	
	private Country country;
	
	
	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getSubTown() {
		return subTown;
	}

	public void setSubTown(String subTown) {
		this.subTown = subTown;
	}

	public String getPostCode() {
		return postCode;
	}

	public void setPostCode(String postCode) {
		this.postCode = postCode;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

}
