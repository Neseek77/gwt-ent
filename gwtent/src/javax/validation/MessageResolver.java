package javax.validation;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 *
 * Interpolate a given validation message
 *
 * @author Emmanuel Bernard
 */
public interface MessageResolver {
    /** Generate a display the message based on the constraint and its parameters. */
    String interpolate(String message, ConstraintDescriptor constraintDescriptor, Object value);
}