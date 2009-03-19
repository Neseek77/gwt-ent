package javax.validation;

import java.lang.annotation.Annotation;
import java.util.Map;
import java.util.Set;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 *
 * describes a single constraint
 *
 * @author Emmanuel Bernard
 */
public interface ConstraintDescriptor {
    /** Constraint declaration annotation */
    Annotation getAnnotation();

    /** constraint parameters */
    Map<String, Object> getParameters();

    /** What are the groups the constraint is applied on */
    Set<String> getGroups();

    /** Return the constraint implementation routine */
    Constraint getConstraintImplementation();
}
