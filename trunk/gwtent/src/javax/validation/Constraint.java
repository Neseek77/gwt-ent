package javax.validation;

import java.lang.annotation.Annotation;
import java.util.Map;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 *
 * Define the logic to validate a given constraint
 **/
public interface Constraint<A extends Annotation> {
    /**
     * Initialize the constraint validator.
     * <p/>
     * This method is guaranteed to be called once right after the constraint is retrieved
     * from the <code>ConstraintFactory</code> and before the Bean Validation provider
     * starts using it.
     *
     * @param constraintAnnotation The constraint declaration
     */
    void initialize(A constraintAnnotation);
    
    
    /**
     * GWT extend here, GWT do not support annotation in client code
     * GWT-ETN reflection made a approach for it, but just store annotation
     * value as a string
     */
    void initialize(Map<String, String> constraintAnnotation);
    

    /**
     * Evaluates the constraint against a value. This method
     * must be thread safe.
     *
     * @param value The object to validate
     * @return false if the value is not valid, true otherwise
     * @throws IllegalArgumentException The value's type isn't understood
     *                                  by the constraint validator
     */
    boolean isValid(Object value);
}
