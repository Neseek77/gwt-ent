package com.gwtent.validate.client;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;
import javax.validation.spi.BootstrapState;
import javax.validation.spi.ConfigurationState;
import javax.validation.spi.ValidationProvider;

import com.gwtent.reflection.client.annotations.Reflect_Full;
import com.gwtent.validate.client.impl.ConfigurationImpl;
import com.gwtent.validate.client.impl.ValidatorFactoryImpl;

/**
 * 
 * @author James Luo
 *
 * 21/07/2010 2:41:11 PM
 */

@Reflect_Full
public class GWTEntValidatorProvider implements ValidationProvider<GWTEntValidatorConfiguration> {

	public GWTEntValidatorConfiguration createSpecializedConfiguration(BootstrapState state) {
		return new ConfigurationImpl( state );
	}

	public Configuration<?> createGenericConfiguration(BootstrapState state) {
		return new ConfigurationImpl( state );
	}

	public ValidatorFactory buildValidatorFactory(ConfigurationState configurationState) {
		return new ValidatorFactoryImpl(configurationState);
	}
}