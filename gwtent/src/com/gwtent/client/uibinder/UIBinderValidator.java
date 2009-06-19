package com.gwtent.client.uibinder;

import java.util.Set;

import javax.validation.ConstraintViolation;

public interface UIBinderValidator {
	Set<ConstraintViolation<?>> validateValue(Object value);
}
