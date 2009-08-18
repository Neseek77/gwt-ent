package com.gwtent.client.validate.constraints;

import javax.validation.ConstraintValidator;

public class URLValidator extends RegularBasedValidator<URL, Object> implements ConstraintValidator<URL, Object> {

	private final String regex = "^(http|https|ftp)\\:\\/\\/[a-z0-9\\-\\.]+\\.[a-z]{2,3}(:[a-z0-9]*)?\\/?([a-z0-9\\-\\._\\?\\,\\'\\/\\\\\\+&amp;%\\$#\\=~])*$";

	public void initialize(URL r) {
		
	}

	protected String getRegex() {
		return regex;
	}

}
