package javax.validation.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class MinValidator implements ConstraintValidator<Min, Integer>{
    private int min;

    public void initialize(Min constraintAnnotation) {
        min = constraintAnnotation.value();
    }

		public boolean isValid(Integer value,
				ConstraintValidatorContext constraintValidatorContext) {
      return ((Number)value).intValue() >= min;
		}
}
