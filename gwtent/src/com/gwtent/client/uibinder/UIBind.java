package com.gwtent.client.uibinder;

/**
 * Bind some thing to editor 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 */
public @interface UIBind {
  public String path() default "";
  public boolean readonly() default false;
}
