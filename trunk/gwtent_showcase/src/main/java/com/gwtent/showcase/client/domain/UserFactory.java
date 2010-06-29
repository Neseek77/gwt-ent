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
	
	public User getEmpty(){
		return empty;
	}
	
	private int userIndex = 0;
	public User getNextUser(){
		userIndex ++;
		if (userIndex % 3 == 1)
			return lei;
		else if (userIndex % 3 == 2)
			return james;
		else 
			return empty;
	}
	
	private User james = createJames();
	private User lei = createLei();
	private User empty = createEmpty();
	
	public User createJames(){
		User user = getEmptyUser();
		
		user.setEmail("JamesLuo.au_gmail.com");
		user.setFirstName("James");
		user.setLastName("");
		user.setDob(new Date(1980-1900, 1, 1));
		
		CreditCard card = new CreditCard();
		card.setCreditCardNumber("111-111-111");
		card.setExpiresOn(new Date(2012-1900, 1, 1));
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
		user.setDob(new Date(1980-1900, 1, 1));
		
		CreditCard card = new CreditCard();
		card.setCreditCardNumber("111-111-111");
		card.setExpiresOn(new Date(2012-1900, 1, 1));
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
	
	public User createEmpty(){
		User user = getEmptyUser();

		CreditCard card = new CreditCard();
		user.setDefaultCreditCard(card);
		
		Address address = new Address();
		user.setAddress(address);
		
		return user;
	}
	
}
