package javax.validation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

import com.gwtent.reflection.client.annotations.Reflect_Full;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 */
@Target({TYPE})
@Retention(RUNTIME)
@Reflect_Full
public @interface GroupSequences {
	Class<?>[] value();
}