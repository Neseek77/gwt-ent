package com.gwtent.uibinder.client;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Field;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.reflection.client.TypeOracle;
import com.gwtent.reflection.client.pathResolver.PathResolver;

public abstract class UIBinderGWTReflection implements UIBinder {

  private Widget widget;
  private ClassType classTypeModel;
  private Object model;
  private String path;

  protected abstract void doInit(Widget widget, Object model, Field field, ClassType classType);
  
  public void init(Widget widget, Object model, String path) {
    this.widget = widget;
    this.path = path;
    ReflectionUtils.checkReflection(model.getClass());
    
    this.model = PathResolver.getInstanceLastLevelByPath(model, path);
    this.classTypeModel = TypeOracle.Instance.getClassType(model.getClass());

    //doInit(widget, this.model, )
  }


  public void binder() {
    
  }


  public Widget getWidget() {
    return widget;
  }

  /**
   * The model
   * ie: path = a.b.c.d
   * this model is the object instance of "c"
   * @return
   */
  public Object getModel() {
    return model;
  }


  public ClassType getClassTypeModel() {
    return classTypeModel;
  }

}
