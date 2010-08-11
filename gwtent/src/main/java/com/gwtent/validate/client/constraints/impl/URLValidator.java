package com.gwtent.validate.client.constraints.impl;

import javax.validation.ConstraintValidator;

import com.gwtent.validate.client.constraints.RegularBasedValidator;
import com.gwtent.validate.client.constraints.URL;

public class URLValidator extends RegularBasedValidator<URL, Object> implements ConstraintValidator<URL, Object> {

	private final String regex = "^(http|https|ftp)\\:\\/\\/[a-z0-9\\-\\.]+\\.[a-z]{2,3}(:[a-z0-9]*)?\\/?([a-z0-9\\-\\._\\?\\,\\'\\/\\\\\\+&amp;%\\$#\\=~])*$";

	public void initialize(URL r) {
		
	}

	protected String getRegex() {
		return regex;
	}

}
