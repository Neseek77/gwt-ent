package javax.validation.constraints;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 */
public class NotNullValidator implements ConstraintValidator<NotNull, Object>{
    public void initialize(NotNull constraintAnnotation) {
        // do nothing
    }

		public boolean isValid(Object value,
				ConstraintValidatorContext constraintValidatorContext) {
			return value != null;
		}
}
