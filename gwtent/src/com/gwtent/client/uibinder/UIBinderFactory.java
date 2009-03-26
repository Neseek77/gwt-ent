package com.gwtent.client.uibinder;

import java.util.HashMap;
import java.util.Map;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.common.ObjectFactory;

public class UIBinderFactory {
  
  public static class UIBinderRegister{
    private static Map<Widget, ObjectFactory<UIBinder>> map = new HashMap<Widget, ObjectFactory<UIBinder>>();
    
    public static void register(Widget widget, ObjectFactory<UIBinder> factory){
      map.put(widget, factory);
    }
    
    public static ObjectFactory<UIBinder> getFactory(Widget widget){
      return map.get(widget);
    }
  }
  
  public static UIBinder getUIBinder(Widget widget){
    assert(widget != null);
    
    ObjectFactory<UIBinder> factory = UIBinderRegister.getFactory(widget);
    if (factory != null)
      return factory.getObject();
    else
      throw new RuntimeException("Can not find UIBinder for widget(" + widget.getClass().getName() + "), please make sure you already registed the UIBinder via UIBinderRegister.register().");
  }
}
