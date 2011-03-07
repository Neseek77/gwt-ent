// $Id: Address.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.groups;

import javax.validation.GroupSequence;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * @author Emmanuel Bernard
 */
@GroupSequence({ Address.class, Address.HighLevelCoherence.class })
@ZipCodeCoherenceChecker(groups = Address.HighLevelCoherence.class)
public class Address {
	@NotNull(groups = Default.class)
	@Size(max = 50, message = "Street names cannot have more than {max} characters.")
	private String street;

	@NotNull(groups = Default.class, message = "Zipcode may not be null")
	@Size(max = 5, message = "Zipcode cannot have more than {max} characters.")
	private String zipcode;

	@NotNull(groups = Default.class)
	@Size(max = 30, message = "City cannot have more than {max} characters.")
	private String city;

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * Check coherence on the overall object
	 * Needs basic checking to be green first
	 */
	public interface HighLevelCoherence {
	}

	/**
	 * Check both basic constraints and high level ones.
	 * High level constraints are not checked if basic constraints fail.
	 */
	@GroupSequence(value = { Default.class, HighLevelCoherence.class })
	public interface Complete {
	}
}
