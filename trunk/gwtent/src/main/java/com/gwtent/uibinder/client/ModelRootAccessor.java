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
	/**
	 * <p>Get the ROOT object. 
	 * 
	 * <p>for example if the path is "user.firstName", this function should only return User object.
	 * <p>the reset will be accessed by reflection system.
	 * @return
	 */
  public Object getValue();
  
  /**
   * Only need this when you binder to a class level variable.
   * don't need if it's a domain object, for example you want bind to "user.firstName"
   * you only change the value of user.firstName, not user itself. Binder will using reflection 
   * to setup "firstName" 
   * @param value
   */
  public void setValue(Object value);
  
  /**
   * The path from ROOT object(Returned by getValue()) to the real property
   * 
   * @return
   */
  public String getRootPath();
}
