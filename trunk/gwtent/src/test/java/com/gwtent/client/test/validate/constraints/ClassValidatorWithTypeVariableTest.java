// $Id: ClassValidatorWithTypeVariableTest.java 19547 2010-05-19 15:40:07Z hardy.ferentschik $
/*
* JBoss, Home of Professional Open Source
* Copyright 2008, Red Hat, Inc. and/or its affiliates, and individual contributors
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
package com.gwtent.client.test.validate.constraints;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;

import org.junit.Test;


import com.gwtent.client.test.validate.VTestCase;
import com.gwtent.client.test.validate.util.TestUtil;
import com.gwtent.reflection.client.annotations.Reflect_Domain;

import static com.gwtent.client.test.validate.util.TestUtil.assertNumberOfViolations;
import static com.gwtent.client.test.validate.util.TestUtil.assertCorrectPropertyPaths;
import static com.gwtent.client.test.validate.util.TestUtil.assertCorrectConstraintTypes;

/**
 * HV-250
 */
public class ClassValidatorWithTypeVariableTest extends VTestCase{

	private Validator validator;

	public void gwtSetUp() {
		validator = TestUtil.getValidator();
	}

	@Test
	public void testOffersNull() {
		Batch batch = new Batch( null );

		Set<ConstraintViolation<Batch>> violations = validator.validate( batch );
		assertNumberOfViolations( violations, 1 );
		assertCorrectPropertyPaths( violations, "offers" );
		assertCorrectConstraintTypes( violations, NotNull.class );
	}

	@Test
	public void testOfferItemNull() {
		ItemAOffer offer = new ItemAOffer( null );
		Set<ItemOffer<? extends Item>> offers = new HashSet<ItemOffer<? extends Item>>();
		offers.add( offer );
		Batch batch = new Batch( offers );

		Set<ConstraintViolation<Batch>> violations = validator.validate( batch );
		assertNumberOfViolations( violations, 1 );
		assertCorrectPropertyPaths( violations, "offers[].item" );
		assertCorrectConstraintTypes( violations, NotNull.class );
	}

	@Test
	public void testOfferItemDateNull() {
		ItemA item = new ItemA( null );
		ItemOffer<? extends Item> offer = new ItemAOffer( item );
		Set<ItemOffer<? extends Item>> offers = new HashSet<ItemOffer<? extends Item>>();
		offers.add( offer );
		Batch batch = new Batch( offers );

		Set<ConstraintViolation<Batch>> violations = validator.validate( batch );
		assertNumberOfViolations( violations, 1 );
		assertCorrectPropertyPaths( violations, "offers[].item.date" );
		assertCorrectConstraintTypes( violations, NotNull.class );
	}

	@Reflect_Domain
	private class Batch {
		@NotNull
		@Valid
		private Set<ItemOffer<? extends Item>> offers = new HashSet<ItemOffer<? extends Item>>();

		public Batch(Set<ItemOffer<? extends Item>> offers) {
			this.offers = offers;
		}
	}

	@Reflect_Domain
	private abstract class Item {
		@NotNull
		private Date date;

		public Item(Date date) {
			this.date = date;
		}
	}

	@Reflect_Domain
	private abstract class ItemOffer<T extends Item> {
		@NotNull
		@Valid
		private T item;

		public ItemOffer(T item) {
			this.item = item;
		}
	}

	@Reflect_Domain
	private class ItemA extends Item {
		public ItemA(Date date) {
			super( date );
		}
	}

	@Reflect_Domain
	private class ItemAOffer extends ItemOffer<ItemA> {
		public ItemAOffer(ItemA item) {
			super( item );
		}
	}
}


