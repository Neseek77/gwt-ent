package com.gwtent.validate.client.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.ConstraintValidatorFactory;
import javax.validation.MessageInterpolator;
import javax.validation.TraversableResolver;
import javax.validation.ValidationException;
import javax.validation.ValidationProviderResolver;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

import org.hibernate.validator.xml.ValidationBootstrapParameters;

import com.gwtent.validate.client.GWTEntValidatorConfiguration;

/**
 * 
 * @author James Luo
 *
 * 21/07/2010 2:44:58 PM
 */
public class ConfigurationImpl implements GWTEntValidatorConfiguration,
		ConfigurationState {

	//Convert message
	private final MessageInterpolator defaultMessageInterpolator = new MessageInterpolatorGWTImpl();
	
	//test a path accessible
	private final TraversableResolver defaultTraversableResolver = new TraversableResolverImpl();
	
	//For create the Validator
	private final ConstraintValidatorFactory defaultConstraintValidatorFactory = new ConstraintValidatorFactoryImpl();
	
	private final ValidationProviderResolver providerResolver;
	
	/**
	 * All settings already be resolved
	 *
	 */
	public static class ValidationBootstrapParameters {
		public ConstraintValidatorFactory constraintValidatorFactory;
		public MessageInterpolator messageInterpolator;
		public TraversableResolver traversableResolver;
		public ValidationProvider provider;
		public Class<? extends ValidationProvider<?>> providerClass = null;
		public final Map<String, String> configProperties = new HashMap<String, String>();
	}

	private ValidationBootstrapParameters validationBootstrapParameters;
	
	private boolean ignoreXmlConfiguration = false;

	public ConfigurationImpl(BootstrapState state) {
		if ( state.getValidationProviderResolver() == null ) {
			this.providerResolver = state.getDefaultValidationProviderResolver();
		}
		else {
			this.providerResolver = state.getValidationProviderResolver();
		}
		
		validationBootstrapParameters = new ValidationBootstrapParameters();
	}

	public ConfigurationImpl(ValidationProvider provider) {
		if ( provider == null ) {
			throw new ValidationException( "Assertion error: inconsistent ConfigurationImpl construction" );
		}
		this.providerResolver = null;
		
		validationBootstrapParameters = new ValidationBootstrapParameters();
		validationBootstrapParameters.provider = provider;
	}

	public GWTEntValidatorConfiguration ignoreXmlConfiguration() {
		ignoreXmlConfiguration = true;
		return this;
	}

	public ConfigurationImpl messageInterpolator(MessageInterpolator interpolator) {
		this.validationBootstrapParameters.messageInterpolator = interpolator;
		return this;
	}

	public ConfigurationImpl traversableResolver(TraversableResolver resolver) {
		this.validationBootstrapParameters.traversableResolver = resolver;
		return this;
	}

	public ConfigurationImpl constraintValidatorFactory(ConstraintValidatorFactory constraintValidatorFactory) {
		this.validationBootstrapParameters.constraintValidatorFactory = constraintValidatorFactory;
		return this;
	}


	public GWTEntValidatorConfiguration addProperty(String name, String value) {
		if ( value != null ) {
			validationBootstrapParameters.configProperties.put( name, value );
		}
		return this;
	}

	public ValidatorFactory buildValidatorFactory() {
		parseValidationXml();
		ValidatorFactory factory = null;
		if ( isSpecificProvider() ) {
			factory = validationBootstrapParameters.provider.buildValidatorFactory( this );
		}
		else {
			final Class<? extends ValidationProvider<?>> providerClass = validationBootstrapParameters.providerClass;
			if ( providerClass != null ) {
				for ( ValidationProvider provider : providerResolver.getValidationProviders() ) {
					//if ( providerClass.isAssignableFrom( provider.getClass() ) ) {
					//TODO GWT don't know if isAssignableFrom, need a way working on it, for now only if it's the same
					if (providerClass.getName().equals(provider.getClass().getName())){
						factory = provider.buildValidatorFactory( this );
						break;
					}
				}
				if ( factory == null ) {
					throw new ValidationException( "Unable to find provider: " + providerClass );
				}
			}
			else {
				List<ValidationProvider<?>> providers = providerResolver.getValidationProviders();
				assert providers.size() != 0; // I run therefore I am
				factory = providers.get( 0 ).buildValidatorFactory( this );
			}
		}

		// reset the param holder
		validationBootstrapParameters = new ValidationBootstrapParameters();
		return factory;
	}

	public boolean isIgnoreXmlConfiguration() {
		return ignoreXmlConfiguration;
	}

	public MessageInterpolator getMessageInterpolator() {
		return validationBootstrapParameters.messageInterpolator;
	}

	public ConstraintValidatorFactory getConstraintValidatorFactory() {
		return validationBootstrapParameters.constraintValidatorFactory;
	}

	public TraversableResolver getTraversableResolver() {
		return validationBootstrapParameters.traversableResolver;
	}

	public Map<String, String> getProperties() {
		return validationBootstrapParameters.configProperties;
	}

	public MessageInterpolator getDefaultMessageInterpolator() {
		return defaultMessageInterpolator;
	}

	public TraversableResolver getDefaultTraversableResolver() {
		return defaultTraversableResolver;
	}

	public ConstraintValidatorFactory getDefaultConstraintValidatorFactory() {
		return defaultConstraintValidatorFactory;
	}

	private boolean isSpecificProvider() {
		return validationBootstrapParameters.provider != null;
	}

	/**
	 * No xml support, we just support provider specific properties programmatically
	 */
	private void parseValidationXml() {
//		if ( ignoreXmlConfiguration ) {
			if ( validationBootstrapParameters.messageInterpolator == null ) {
				validationBootstrapParameters.messageInterpolator = defaultMessageInterpolator;
			}
			if ( validationBootstrapParameters.traversableResolver == null ) {
				validationBootstrapParameters.traversableResolver = defaultTraversableResolver;
			}
			if ( validationBootstrapParameters.constraintValidatorFactory == null ) {
				validationBootstrapParameters.constraintValidatorFactory = defaultConstraintValidatorFactory;
			}
//		}
//		else {
//			ValidationBootstrapParameters xmlParameters = new ValidationXmlParser().parseValidationXml();
//			applyXmlSettings( xmlParameters );
//		}
	}

}
