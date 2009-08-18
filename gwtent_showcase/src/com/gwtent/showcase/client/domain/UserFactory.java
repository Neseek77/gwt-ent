package com.gwtent.showcase.client.domain;

import java.util.Date;

public class UserFactory {
	private static UserFactory userFactory = new UserFactory();
	
	
	public static UserFactory getInstance(){
		return userFactory;
	}
	
	public User getEmptyUser(){
		User user = new User();
		return user;
	}
	
	
	public User getJames(){
		return james;
	}
	
	public User getLei(){
		return lei;
	}
	
	private int userIndex = 0;
	public User getNextUser(){
		userIndex ++;
		if (userIndex % 2 == 1)
			return lei;
		else
			return james;
	}
	
	private User james = createJames();
	private User lei = createLei();
	
	public User createJames(){
		User user = getEmptyUser();
		
		user.setEmail("JamesLuo.au@gmail.com");
		user.setFirstName("James");
		user.setLastName("Luo");
		user.setDob(new Date(1980, 1, 1));
		
		CreditCard card = new CreditCard();
		card.setCreditCardNumber("111-111-111");
		card.setExpiresOn(new Date(2012, 1, 1));
		card.setIssuingBank("westpac");
		user.setDefaultCreditCard(card);
		
		Address address = new Address();
		address.setCountry(Country.UnitedStates);
		address.setPostCode("1234");
		address.setStreet("More time st");
		address.setSubTown("VIC");
		user.setAddress(address);
		
		return user;
	}
	
	public User createLei(){
		User user = getEmptyUser();
		
		user.setEmail("LeiYang.au@gmail.com");
		user.setFirstName("Lei");
		user.setLastName("Yang");
		user.setDob(new Date(1980, 1, 1));
		
		CreditCard card = new CreditCard();
		card.setCreditCardNumber("111-111-111");
		card.setExpiresOn(new Date(2012, 1, 1));
		card.setIssuingBank("westpac");
		user.setDefaultCreditCard(card);
		
		Address address = new Address();
		address.setCountry(Country.UnitedStates);
		address.setPostCode("1234");
		address.setStreet("More time st");
		address.setSubTown("VIC");
		user.setAddress(address);
		
		return user;
	}
	
}
