package javax.validation;

import java.lang.annotation.ElementType;
import java.util.Set;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 *
 * Describes a validated element (class, field or property)
 *
 * @author Emmanuel Bernard
 */
public interface ElementDescriptor {
  /** Element type */
  ElementType getElementType();

  /**
   * statically defined returned type
   * //TODO should it be Type or even completly removed
   */
  Class getReturnType();

  /** is the association cascaded */
  boolean isCascaded();

  /** all the constraint descriptors for the given element */
  Set<ConstraintDescriptor> getConstraintDescriptors();

  /**
   * Property path from the root entity validated.
   * //FIXME there is a problem in describing cyclic dependencies
   */
  String getPropertyPath();
}