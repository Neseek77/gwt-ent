package javax.validation;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 * This class manages the creation of constraint validators.
 */
public interface ConstraintFactory {
  /**
   * Instantiate a Constraint.
   *
   * @return Returns a new Constraint instance
   *         The ConstraintFactory is <b>not</b> responsible for calling Constraint#initialize
   */
  <T extends Constraint> T getInstance(Class<T> constraintClass);
}
