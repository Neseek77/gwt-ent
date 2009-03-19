package javax.validation;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 */
public class NotNullConstraint implements Constraint<NotNull>{
    public void initialize(NotNull constraintAnnotation) {
        // do nothing
    }

    public boolean isValid(Object value) {
        return value != null;
    }
}
