package com.gwtent.client.uibinder;

import java.util.HashMap;
import java.util.Map;

import com.gwtent.client.common.ObjectFactory;

public class UIBinderFactory {
  
  public static class UIBinderRegister{
    private static Map<Class<?>, ObjectFactory<UIBinder>> map = new HashMap<Class<?>, ObjectFactory<UIBinder>>();
    
    public static void register(Class<?> clazz, ObjectFactory<UIBinder> factory){
      map.put(clazz, factory);
    }
    
    public static ObjectFactory<UIBinder> getFactory(Class<?> clazz){
      return map.get(clazz);
    }
  }

  /**
   * Get UI Binder through a widget
   * @param widget
   * @return
   */
  public static UIBinder getUIBinder(Class<?> clazz){
    assert(clazz != null);
    
    ObjectFactory<UIBinder> factory = UIBinderRegister.getFactory(clazz);
    if (factory != null)
      return factory.getObject();
    else
      throw new RuntimeException("Can not find UIBinder for widget(" + clazz.getName() + "), please make sure you already registed the UIBinder via UIBinderRegister.register().");
  }
}
