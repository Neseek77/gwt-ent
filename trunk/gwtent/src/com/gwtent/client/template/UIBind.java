package com.gwtent.client.template;

/**
 * Bind some thing to editor 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public @interface UIBind {
  public String path();
  public boolean readonly() default false;
  
  /**
   * Some times model object in other class which can be 
   * point by "path", you can using this property
   * then you can invoke getUIBinderManager.modelChanged(yourModel)
   * do bind model and editor
   * @return
   */
  public Class<?> modelClass() default Object.class;
}
