package com.gwtent.uibinder.client;

/**
 * Bind some thing to editor 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public @interface UIBind {
  public String path();
  public boolean readonly() default false;
  
  /**
   * True if you want validate user input automatically.
   * Default is true
   * @return
   */
  public boolean autoValidate() default true;
  /**
   * If autoValidate, Which groups you want validate,
   * by default it's your Default.class group
   * @return
   */
  public Class<?>[] groups() default {};
  
  /**
   * Some times model object in other class which can be 
   * point by "path", you can using this property
   * then you can invoke getUIBinderManager.modelChanged(yourModel)
   * do bind model and editor
   * @return
   */
  public Class<?> modelClass() default Object.class;
}
