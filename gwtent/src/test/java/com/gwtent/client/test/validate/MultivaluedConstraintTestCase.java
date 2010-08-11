package com.gwtent.client.test.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.groups.Default;

import com.google.gwt.junit.client.GWTTestCase;

/**
 * 
 * @author James Luo
 *
 * 09/08/2010 4:52:06 PM
 */
public class MultivaluedConstraintTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.gwtent.client.test.validate.Validate";
	}
	
	public interface SuperUser extends Default {
		
	}
	
	public class Address {
    @ZipCode.List( {
            @ZipCode(countryCode="fr", groups=Default.class,
                     message = "zip code is not valid"),
            @ZipCode(countryCode="fr", groups=SuperUser.class,
                     message = "zip code invalid. Requires overriding before saving.")
            } )
    private String zipcode;
	}

	
	/**
	 * Validate a zipcode for a given country 
	 * The only supported type is String
	 */
	@Documented
	//@Constraint(validatedBy = ZipCodeValidator.class)
	@Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ZipCode {

	    String countryCode();

	    String message() default "{com.acme.constraint.ZipCode.message}";

	    Class<?>[] groups() default {};

	    Class<? extends Payload>[] payload() default {};

	    /**
	     * Defines several @ZipCode annotations on the same element
	     * @see (@link ZipCode}
	     */
	    @Target({ ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER })
	    @Retention(RetentionPolicy.RUNTIME)
	    @Documented
	    @interface List {
	        ZipCode[] value();
	    }    
	}
}
