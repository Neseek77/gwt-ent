package com.gwtent.uibinder.client;

/**
 * To make the final js as small as possible
 * I don't want the UI get involved into Reflection system
 * That's why we need ModelValueAccessor to help us
 * Get or Set values to model.
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public interface ModelRootAccessor {
  public Object getValue();
  public void setValue(Object value);
  public String getRootPath();
}
