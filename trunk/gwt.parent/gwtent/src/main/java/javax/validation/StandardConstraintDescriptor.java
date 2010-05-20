package javax.validation;

/**
 * 
 * Describe how the current constraint influences the standard constraints
 *
 * @author Emmanuel Bernard
 */
public class StandardConstraintDescriptor {
    /**
     * Defines the object nullability.
     * TRUE means the object is nullable,
     * FALSE means the object is not nullable,
     * NULL means does not apply.
     */
    public Boolean getNullability() {
        return null;
    }

    /**
     * Defines the precision if the validated object is a number.
     * NULL means does not apply.
     */
    public Integer getPrecision() {
        return null;
    }

    /**
     * Defines the scale if the validated object is a number.
     * NULL means does not apply.
     */
    public Integer getScale() {
        return null;
    }

    /**
     * Defines the length if the validated object is a string.
     * NULL means does not apply.
     */
    public Integer getLength() {
        return null;
    }
}