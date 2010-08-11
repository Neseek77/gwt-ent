package com.gwtent.client.test.validate;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.ReportAsSingleViolation;
import javax.validation.Valid;
import javax.validation.Validator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;
import javax.validation.metadata.BeanDescriptor;
import javax.validation.metadata.ConstraintDescriptor;
import javax.validation.metadata.ElementDescriptor;

import com.gwtent.client.test.validate.Groups.FirstLevelCheck;

/**
 * 
 * @author James Luo
 * 
 *         21/07/2010 11:37:03 AM
 */
public class ConstraintDescriptorTestCase {
	
	public void testConstraintDescriptor(){
		Validator validator = null;
		BeanDescriptor bookDescriptor = validator.getConstraintsForClass(Book.class);

		assert ! bookDescriptor.hasConstraints();

		assert bookDescriptor.isBeanConstrained();

		assert bookDescriptor.getConstraintDescriptors().size() == 0; //no bean-level constraint

		//more specifically "author" and "title"
		assert bookDescriptor.getConstrainedProperties().size() == 2;

		//not a property
		assert bookDescriptor.getConstraintsForProperty("doesNotExist") == null; 

		//property with no constraint
		assert bookDescriptor.getConstraintsForProperty("description") == null; 

		ElementDescriptor propertyDescriptor = bookDescriptor.getConstraintsForProperty("title");
		assert propertyDescriptor.getConstraintDescriptors().size() == 2;
		//TODO Which verion has this?
		//assert "title".equals( propertyDescriptor.getPropertyName() );

		//assuming the implementation returns the @NotEmpty constraint first
		ConstraintDescriptor<?> constraintDescriptor = propertyDescriptor.getConstraintDescriptors()
		                                                              .iterator().next();
		
		assert constraintDescriptor.getAnnotation().annotationType().equals( NotEmpty.class );
		assert constraintDescriptor.getGroups().size() == 2; //FirstLevelCheck and Default
		assert constraintDescriptor.getComposingConstraints().size() == 2;
		assert constraintDescriptor.isReportAsSingleViolation() == true;

		//@NotEmpty cannot be null
		boolean notNullPresence = false;
		for ( ConstraintDescriptor<?> composingDescriptor : constraintDescriptor.getComposingConstraints() ) {
		    if ( composingDescriptor.getAnnotation().annotationType().equals( NotNull.class ) ) {
		        notNullPresence = true;
		    }
		}
		assert notNullPresence; 

		//assuming the implementation returns the Size constraint second
		//TODO complete test cases
		//constraintDescriptor = propertyDescriptor.getConstraintDescriptors().iterator().next().next();
		assert constraintDescriptor.getAnnotation().annotationType().equals( Size.class );
		assert Integer.valueOf(constraintDescriptor.getAttributes().get("max").toString()) == 30; 
		assert constraintDescriptor.getGroups().size() == 1;

		propertyDescriptor = bookDescriptor.getConstraintsForProperty("author");
		assert propertyDescriptor.getConstraintDescriptors().size() == 1;
		////TODO complete test cases
		//assert propertyDescriptor.isCascaded();
	}
	

	@Documented
	@NotNull
	@Size(min = 1)
	@ReportAsSingleViolation
	@Constraint(validatedBy = ConstraintDescriptorTestCase.NotEmpty.NotEmptyValidator.class)
	@Target( { ElementType.METHOD, ElementType.FIELD,
			ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
			ElementType.PARAMETER })
	@Retention(RetentionPolicy.RUNTIME)
	public @interface NotEmpty {
		String message() default "{com.acme.constraint.NotEmpty.message}";

		Class<?>[] groups() default {};

		@Target( { ElementType.METHOD, ElementType.FIELD,
				ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR,
				ElementType.PARAMETER })
		@Retention(RetentionPolicy.RUNTIME)
		@Documented
		@interface List {
			NotEmpty[] value();
		}

		class NotEmptyValidator implements ConstraintValidator<NotEmpty, String> {
			public void initialize(NotEmpty constraintAnnotation) {
			}

			public boolean isValid(String value, ConstraintValidatorContext context) {
				return true;
			}
		}
	}

	public static class Author {
		private String firstName;

		@NotEmpty(message = "lastname must not be null")
		private String lastName;

		@Size(max = 30)
		private String company;

		public String getFirstName() {
			return firstName;
		}

		public void setFirstName(String firstName) {
			this.firstName = firstName;
		}

		public String getLastName() {
			return lastName;
		}

		public String getCompany() {
			return company;
		}

		public void setCompany(String company) {
			this.company = company;
		}
	}

	public static class Book {
		private String title;
		private String description;

		@Valid
		@NotNull
		private Author author;

		@NotEmpty(groups = { FirstLevelCheck.class, Default.class })
		@Size(max = 30)
		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public Author getAuthor() {
			return author;
		}

		public void setAuthor(Author author) {
			this.author = author;
		}

		public String getDescription() {
			return description;
		}

		public void setAuthor(String description) {
			this.description = description;
		}
	}
	
	
}
