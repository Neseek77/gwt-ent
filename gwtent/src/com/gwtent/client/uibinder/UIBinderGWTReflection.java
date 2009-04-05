package com.gwtent.client.uibinder;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

public abstract class UIBinderGWTReflection implements UIBinder {

  private Widget widget;
  private ClassType classTypeModel;
  private Object model;
  private String path;
  
  private boolean isMethod(String path){
    return (path.indexOf("()") >= 0);
  }
  
  private void pathNotFound(){
    throw new RuntimeException("Path("+ path +") not found, please make sure its exists and can be access by subclass.");
  }
  
  private Object getClassTypeByPath(Object instance, String path){
    Object object = null;
    ClassType parent = TypeOracle.Instance.getClassType(instance.getClass());
    if (! isMethod(path)){
      Field field = parent.findField(path);
      if (field == null)
        pathNotFound();
      
      object = field.getFieldValue(instance);
    }else{
      Method method = parent.findMethod(path, new String[0]);
      object = method.invoke(instance, null);
    }
    
    if (object == null)
      throw new RuntimeException("Path returns null, full path: " + this.path + "current path: " + path);
    
    ReflectionUtils.checkReflection(object.getClass());
    return object;
  }
  

  protected abstract void doInit(Widget widget, Object model, Field field, ClassType classType);
  
  public void init(Widget widget, Object model, String path) {
    this.widget = widget;
    this.path = path;
    ReflectionUtils.checkReflection(model.getClass());
    
    String[] paths = path.split(".");
    Object parentModel = model;
    for (int i = 0; i < paths.length - 1; i ++){
      parentModel = getClassTypeByPath(parentModel, paths[i]);
    }
    this.model = parentModel;
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
