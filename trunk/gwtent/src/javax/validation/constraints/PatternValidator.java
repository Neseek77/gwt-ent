package javax.validation.constraints;

import java.util.Map;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * <pre>
 * --
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --
 * </pre>
 */
public class PatternValidator implements ConstraintValidator<Pattern, String> {

	private String regex;
	private int flags;

	public void initialize(Pattern params) {
		this.regex = params.regex();
		this.flags = params.flags();
	}

	public boolean isValid(Object ovalue) {
		return true;
		// return isValid((String) ovalue);
	}

	// private native boolean isValid(String s); /*-{
	// return new RegExp(regex).test(s);
	// }-*/;

	public void initialize(Map<String, String> params) {
		regex = params.get("regex");
		flags = Integer.parseInt(params.get("flags"));
	}

	public boolean isValid(String value,
			ConstraintValidatorContext constraintValidatorContext) {
		return true;
		// return isValid((String) ovalue);
	};
}
