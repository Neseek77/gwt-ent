package com.gwtent.client.uibinder.gxt.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import com.extjs.gxt.ui.client.data.ModelData;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

/**
 * This class adapter all POJO class to ModelData interface
 * Please NOTE: POJO must have reflection information
 * Which can be done by annotated by @Reflectionable or implements Reflection interface
 * See {@link ReflectionUtils.reflectionRequired} 
 * 
 * See {@link ModelData}
 * 
 * @author James Luo (JamesLuo.au@gmail.com)
 *
 * @param <M>
 */
public class ModelDataAdapter<M> implements ModelData {

  private final ClassType classType;
  private final M instance;
  private boolean ignoreIfFieldNotFound = true;
  

  public ModelDataAdapter(M instance) {
    this(instance, true);
  }
  
  public ModelDataAdapter(M instance, boolean ignoreIfFieldNotFound) {
    this(TypeOracle.Instance.getClassType(instance.getClass()), instance);
    this.ignoreIfFieldNotFound = ignoreIfFieldNotFound;
  }
  
  public ModelDataAdapter(ClassType classType, M instance) {
    assert(instance != null);
    
    if (classType == null)
      ReflectionUtils.reflectionRequired(instance.getClass());
    
    this.classType = classType;
    this.instance = instance;
  }
  
  public M getInstance(){
    return instance;
  }
  
  
  /**
   * override this function to support addtation field
   * @param <X>
   * @param propertyName
   * @return
   */
  protected <X> X getAddtionalProperty(String propertyName){
    if (this.isIgnoreIfFieldNotFound())
      return null;
    else
      throw new RuntimeException("Field (" + propertyName + ") of " + classType.getName() + " not found.");
  }

  public <X> X get(String arg0) {
    Field field = getField(arg0);
    if (field == null){
      return getAddtionalProperty(arg0);
    }else{
      return (X) getField(arg0).getFieldValue(instance);
    }
  }

  public Map<String, Object> getProperties() {
    Map<String, Object> properties = new HashMap<String, Object>();
    for (String str : getPropertyNames()) {
      properties.put(str, get(str));
    }

    return properties;
  }

  private Collection<String> propertyNames = null;

  /**
   * All functions start with "get" and without parameters
   */
  public Collection<String> getPropertyNames() {
    if (propertyNames != null) {
      Collection<String> result = new ArrayList<String>();
      for (Method method : classType.getMethods()) {
        if ((method.getName().startsWith("get"))
            && (method.getParameters().length <= 0)) {
          result.add(method.getName().substring(4));
        }
      }
    }
    return propertyNames;
  }

  public <X> X remove(String arg0) {
    // TODO what's this for?
    return null;
  }

  public <X> X set(String arg0, X arg1) {
    getField(arg0).setFieldValue(instance, arg1);
    //classType.invoke(instance, "set" + arg0, new Object[] {arg1});
    return get(arg0);
  }
  
  private Field getField(String fieldName){
   return classType.findField(fieldName); 
  }

  public void setIgnoreIfFieldNotFound(boolean ignoreIfFieldNotFound) {
    this.ignoreIfFieldNotFound = ignoreIfFieldNotFound;
  }

  public boolean isIgnoreIfFieldNotFound() {
    return ignoreIfFieldNotFound;
  }

}
