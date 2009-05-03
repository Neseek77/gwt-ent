package com.gwtent.client.uibinder;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.TextBoxBase;
import com.gwtent.client.common.ObjectFactory;
import com.gwtent.client.uibinder.binder.RichTextAreaBinder;
import com.gwtent.client.uibinder.binder.TextBoxBinder;
import com.gwtent.client.uibinder.modelvalue.ModelValueGWTImpl;
import com.gwtent.client.uibinder.modelvalue.ModelValueImpl;

public class UIBinderGWTFactory {
  
  public class UIBinderGWTRegister{
    private Map<Class<?>, ObjectFactory<UIBinder>> map = new HashMap<Class<?>, ObjectFactory<UIBinder>>();
    
    public void register(Class<?> clazz, ObjectFactory<UIBinder> factory){
      map.put(clazz, factory);
    }
    
    public void register(Class<?>[] classes, ObjectFactory<UIBinder> factory){
      for (Class<?> clazz : classes){
        register(clazz, factory);
      }
    }
    
    public void register(IBinderMetaData supportedEditors){
      register(supportedEditors.getSupportedEditors(), supportedEditors.getFactory());
    }
    
    
    
    public ObjectFactory<UIBinder> getFactory(Class<?> clazz){
      return map.get(clazz);
    }
    
     
  }
  
  public UIBinderGWTFactory(){
    registeBinders();
  }
  
//  private static UIBinderGWTRegister uiBinderGWTRegister = new UIBinderGWTRegister();
//  
//  public UIBinderGWTRegister getUIBinderGWTRegister(){
//    return uiBinderGWTRegister;
//  }

  /**
   * Get UI Binder through a widget(or any object you defined?)
   * @param widget
   * @return
   */
  public <T extends Object, D extends Object> UIBinder<T, D> getUIBinder(Class<T> clazz){
    assert(clazz != null);
    
    ObjectFactory<UIBinder> factory = getUIBinderGWTRegister().getFactory(clazz);
    if (factory != null){
      return factory.getObject();
    }
    else
      throw new RuntimeException("Can not find UIBinder for widget(" + clazz.getName() + "), please make sure you already registed the UIBinder via UIBinderRegister.register().");
  }
  
  public static ModelValue getModelValue(Class<?> root, String path, boolean readOnly, ModelRootAccessor valueAccessor){
    if (path == null || path.length() <= 0)
      return new ModelValueImpl(readOnly, valueAccessor);
    else
      return new ModelValueGWTImpl(root, path, readOnly, valueAccessor);
  }
  
  protected UIBinderGWTRegister createUIBinderGWTRegister(){
    return new UIBinderGWTRegister();
  }
  
  private UIBinderGWTRegister uiBinderGWTRegister;
  public UIBinderGWTRegister getUIBinderGWTRegister(){
    if (uiBinderGWTRegister == null)
      uiBinderGWTRegister = createUIBinderGWTRegister();
    
    return uiBinderGWTRegister;
  }
  
  /**
   * override this function to create your 
   * @return
   */
  protected UIBinderGWTFactory createUIBinderGWTFactory(){
    return new UIBinderGWTFactory();
  }
  
  private static UIBinderGWTFactory factory;
  public static UIBinderGWTFactory getUIBinderGWTFactory(){
    if (factory == null)
      factory = new UIBinderGWTFactory();
    
    return factory;
  }
  
  /**
   * override this function to register more functions
   */
  protected void registeBinders(){
    
  }
}
