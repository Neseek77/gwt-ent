package javax.validation;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 *
 * Describe a constraint validation defect
 * <p/>
 * TODO add pointers to the metadata?
 *
 * @author Emmanuel Bernard
 */
public interface InvalidConstraint<T> {
    /** Error message */
    String getMessage();

    /** Root bean being validated validated */
    T getRootBean();

    /** Bean type being validated */
    Class getBeanClass();

    /** The value failing to pass the constraint */
    Object getValue();

    /**
     * the property path to the value from <code>rootBean</code>
     * Null if the value is the rootBean itself
     */
    String getPropertyPath();

    /**
     * return the list of groups that the triggered constraint applies on and witch also are
     * within the list of groups requested for validation
     * (directly or through a group sequence)
     * TODO: considering removal, if you think it's important, speak up
     */
    String[] getGroups();
}