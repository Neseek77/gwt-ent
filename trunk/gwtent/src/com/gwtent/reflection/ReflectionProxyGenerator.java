package com.gwtent.reflection;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;

public class ReflectionProxyGenerator extends Generator {


	public String generate(TreeLogger logger, GeneratorContext context, String typeName) throws UnableToCompleteException {
		ReflectionCreator binder = new ReflectionCreator(logger, context, typeName);
		String className = binder.createWrapper();
		return className;
	}

}
