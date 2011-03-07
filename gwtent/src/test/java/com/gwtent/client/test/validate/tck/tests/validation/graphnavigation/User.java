// $Id: User.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.gwtent.client.test.validate.tck.tests.validation.graphnavigation;

import java.util.ArrayList;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * @author Hardy Ferentschik
 */
public class User {

	@NotNull
	private String firstName;

	@NotNull(groups = Default.class)
	private String lastName;

	@Valid
	private List<Address> addresses = new ArrayList<Address>();

	@Valid
	private List<User> knowsUser = new ArrayList<User>();

	public User() {
	}

	public User(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void addAddress(Address address) {
		addresses.add( address );
	}

	public void knows(User user) {
		knowsUser.add( user );
	}

	public List<User> getKnowsUser() {
		return knowsUser;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append( "User" );
		sb.append( "{addresses=" ).append( addresses );
		sb.append( ", lastName='" ).append( lastName ).append( '\'' );
		sb.append( ", firstName='" ).append( firstName ).append( '\'' );
		sb.append( '}' );
		return sb.toString();
	}
}
