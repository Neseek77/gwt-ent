package javax.validation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
* Define a group sequence
* The interface hosting @GroupSequence is representing the group sequence.
* When hosted on a class, represents the <code>Default</code> group
* for that class.
*
* @author Emmanuel Bernard
* @author Hardy Ferentschik
*/
@Target({ TYPE })
@Retention(RUNTIME)
public @interface GroupSequence {
	Class<?>[] value();
}