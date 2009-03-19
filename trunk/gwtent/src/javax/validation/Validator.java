package javax.validation;

import java.util.Set;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 * <p/>
 * Validate a given object type
 *
 * @author Emmanuel Bernard
 */
public interface Validator<T> {
    /**
     * validate all constraints on object
     *
     * @param object object to validate
     * @param groups group name(s) targeted for validation (default to <code>default</code>)
     * @return invalid constrains or an empty Set if none
     * @throws IllegalArgumentException e if object is null
     */
    Set<InvalidConstraint<T>> validate(T object, String... groups);

    /**
     * validate all constraints on <code>propertyName</code> property of object
     *
     * @param object       object to validate
     * @param propertyName property to validate
     * @param groups       group name(s) targeted for validation (default to <code>default</code>)
     * @return invalid constrains or an empty Set if none
     * @throws IllegalArgumentException e if object is null
     */
    Set<InvalidConstraint<T>> validateProperty(T object, String propertyName, String... groups);

    /**
     * validate all constraints on <code>propertyName</code> property
     * if the property value is <code>value</code>
     * <p/>
     * TODO express limitations of InvalidConstraint in this case
     *
     * @param propertyName property to validate
     * @param value        property value to validate
     * @param groups       group name(s) targeted for validation (default to <code>default</code>)
     * @return invalid constrains or an empty Set if none
     */
    Set<InvalidConstraint<T>> validateValue(String propertyName, Object value, String... groups);

    //[...]
    /**
     * return true if at least one constraint declaration is present for the given bean
     * or if one property is marked for validation cascade
     */
    boolean hasConstraints();

    /** return the class level constraints */
    ElementDescriptor getBeanConstraints();

    /**
     * return the property level constraints for a given propertyName
     * or null if either the property does not exist or has no constraint
     */
    ElementDescriptor getConstraintsForProperty(String propertyName);

    /** return the property names having at least a constraint defined */
    Set<String> getValidatedProperties();

    void setMessageResolver(MessageResolver messageResolver);
}