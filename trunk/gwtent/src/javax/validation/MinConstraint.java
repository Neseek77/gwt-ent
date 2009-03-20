package javax.validation;

import java.util.Map;

/**
 * <pre>--
 * As soon as the classes in javax.validation are available from official sites, this
 * class will be removed from this compilation unit.
 * --</pre>
 */
public class MinConstraint implements Constraint<Min>{
    private int min;

    public void initialize(Min constraintAnnotation) {
        min = constraintAnnotation.value();
    }

    public boolean isValid(Object value) {
        return ((Number)value).intValue() >= min;
    }

    public void initialize(Map<String, String> constraintAnnotation) {
      // TODO Auto-generated method stub
      
    }
}
