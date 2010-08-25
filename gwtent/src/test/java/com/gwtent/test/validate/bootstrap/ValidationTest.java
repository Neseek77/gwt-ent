package com.gwtent.test.validate.bootstrap;

import javax.validation.Configuration;
import javax.validation.ValidatorFactory;

import org.hibernate.validator.engine.ConfigurationImpl;
import org.hibernate.validator.engine.ValidatorFactoryImpl;
import org.junit.Test;

/**
 * 
 * @author James Luo
 *
 * 16/08/2010 3:27:06 PM
 */
public class ValidationTest extends
		com.gwtent.client.test.validate.bootstrap.ValidationTest {

	//Run as a normal test case
	public String getModuleName() {
		return null;
	}
	
	
	@Test
	public void testBootstrapAsServiceWithBuilder() {
//		GWTEntValidatorConfiguration configuration = Validation
//				.byProvider( GWTEntValidatorProvider.class )
//				.configure();
//		assertDefaultBuilderAndFactory( configuration );
//		
//		StartLessThanEndImpl a = new StartLessThanEndImpl();
//		a.isValid(new Interval(), null);
	}

	@Test
	public void testBootstrapAsServiceDefault() {
//		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//		assertDefaultFactory( factory );
	}

	@Test
	public void testCustomConstraintValidatorFactory() {
		super.testCustomConstraintValidatorFactory();
	}
	
	protected void assertDefaultBuilderAndFactory(Configuration configuration) {
		assertNotNull( configuration );
		assertTrue( configuration instanceof ConfigurationImpl );

		ValidatorFactory factory = configuration.buildValidatorFactory();
		assertDefaultFactory( factory );
	}

	protected void assertDefaultFactory(ValidatorFactory factory) {
		assertNotNull( factory );
		assertTrue( factory instanceof ValidatorFactoryImpl );
	}
}
