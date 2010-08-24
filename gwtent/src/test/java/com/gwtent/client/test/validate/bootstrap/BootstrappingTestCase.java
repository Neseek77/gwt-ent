package com.gwtent.client.test.validate.bootstrap;

import java.lang.annotation.ElementType;

import javax.validation.Path;
import javax.validation.TraversableResolver;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.validation.Path.Node;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.Messages;
import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.validate.client.GWTEntValidatorProvider;
import com.gwtent.validate.client.message.ValidationMessages;

/**
 * 
 * @author James Luo
 * 
 *         29/07/2010 2:40:35 PM
 */
public class BootstrappingTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.gwtent.client.test.validate.Validate";
	}

	public void testDefaultProvider() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

		Validator validator = factory.getValidator();

		assert validator.getClass().getName().equals(
				"com.gwtent.validate.client.impl.ValidatorGWT");
	}

	public void testDefaultProviderWithConfig() {
		ValidatorFactory factory = Validation.byProvider(GWTEntValidatorProvider.class).configure()
				.addI18NMessages(GWT.create(ValidationMessages.class), ValidationMessages.class)
				.buildValidatorFactory();

		Validator validator = factory.getValidator();

		assertTrue(validator != null);
		// some customization from a container
		// ValidatorFactory factory = Validation
		// .byDefaultProvider().configure()
		// .messageInterpolator( new ContainerMessageInterpolator() )
		// .constraintValidatorFactory( new
		// ContainerComponentConstraintValidatorFactory() )
		// .traversableResolver( new JPAAwareTraversableResolver() )
		// .buildValidatorFactory();
		//
		// //cache the factory somewhere
		// Validator validator = factory.getValidator();
	}

	public void testGWTEntProvider() {
		// ValidatorFactory factory = Validation
		// .byProvider( GWTEntValidatorProvider.class ) //chose a specific provider
		// .configure()
		// .messageInterpolator( new MessageInterpolator() ) //default configuration
		// option
		// .addConstraint(Address.class, customConstraintDescriptor) //ACME specific
		// method
		// .buildValidatorFactory();
		//
		// //same initialization decomposing calls
		// ACMEConfiguration acmeConfiguration = Validation
		// .byProvider( ACMEProvider.class )
		// .configure();
		//		
		// ValidatorFactory factory = acmeConfiguration
		// .messageInterpolator( new ContainerMessageInterpolator() ) //default
		// configuration option
		// .addConstraint(Address.class, customConstraintDescriptor) //ACME specific
		// method
		// .buildValidatorFactory();

	}

	private class TraversableResolverImpl implements TraversableResolver {

		public boolean isCascadable(Object traversableObject,
				Node traversableProperty, Class<?> rootBeanType,
				Path pathToTraversableObject, ElementType elementType) {
			// TODO Auto-generated method stub
			return false;
		}

		public boolean isReachable(Object traversableObject,
				Node traversableProperty, Class<?> rootBeanType,
				Path pathToTraversableObject, ElementType elementType) {
			// TODO Auto-generated method stub
			return false;
		}

	}

	public void testContext() {
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		Validator validatorUsingDefaults = factory.getValidator();
		Validator validatorUsingCustomTraversable = factory.usingContext()
				.traversableResolver(new TraversableResolverImpl()).getValidator();

		assert validatorUsingDefaults != null;
		assert validatorUsingCustomTraversable != null;
	}

}
