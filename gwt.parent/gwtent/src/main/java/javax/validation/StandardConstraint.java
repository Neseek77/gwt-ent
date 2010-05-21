package javax.validation;

/**
 *
 * Indicates the availability of standard constraint metadata.
 */
public interface StandardConstraint {
    /**
     *
     * @return A StandardConstraintDescriptor containing metadata
     *         for this constraint definition.
     */
    StandardConstraintDescriptor getStandardConstraintDescriptor();
}