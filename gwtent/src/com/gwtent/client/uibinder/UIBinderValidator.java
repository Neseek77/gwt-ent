package com.gwtent.client.uibinder;

import java.util.Set;

import javax.validation.InvalidConstraint;

public interface UIBinderValidator {
	Set<InvalidConstraint<Object>> validateValue(Object value);
}
