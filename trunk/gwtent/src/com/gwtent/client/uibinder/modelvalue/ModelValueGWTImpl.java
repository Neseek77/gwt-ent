package com.gwtent.client.uibinder.modelvalue;


import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.reflection.pathResolver.PathResolver;
import com.gwtent.client.uibinder.ModelRootAccessor;
import com.gwtent.client.uibinder.ModelValue;

public class ModelValueGWTImpl extends ModelValueImpl implements ModelValue<Object> {

  final Field field;
  final Method method;
  private final Class<?> root;
  final String fullPath;
//ClassType of the root
  private final ClassType instanceClassType;
  
  
  public ModelValueGWTImpl(Class<?> root, String fullPath, boolean readOnly, ModelRootAccessor rootAccessor){
    super(readOnly, rootAccessor);
    
    this.root = root;
    this.fullPath = fullPath;
    this.readOnly = readOnly;
    
    instanceClassType = TypeOracle.Instance.getClassType(root);
    
    ClassType lastLevelClassType = PathResolver.getLastClassTypeByPath(root, fullPath);
    
    String lastPath = PathResolver.getLastElementByPath(fullPath);
    
    this.field = lastLevelClassType.getField(lastPath);
    if (field == null){
      this.method = lastLevelClassType.getMethod(lastPath, null);
    }else{
      this.method = null;
    }
  }
  
  public boolean getReadOnly() {
    return super.getReadOnly() || (method != null);
  }
  
  
  public Object getValue() {
    Object instance = super.getValue();
    if (instance != null){
      Object lastLevel = PathResolver.getInstanceLastLevelByPath(instance, fullPath);
      if (lastLevel != null){
        if (field != null)
          return field.getFieldValue(lastLevel);
        else if (method != null){
          return method.invoke(lastLevel, null);
        }
      }
    }
    
    return null;
  }
  
  
  public void setValue(Object value) {
    Object instance = super.getValue();
    
    if (field != null){
      Object lastLevel = PathResolver.getInstanceLastLevelByPath(instance, fullPath);
      if (lastLevel != null){
        field.setFieldValue(lastLevel, value);
      }else {
        //throw new RuntimeException("Instance is null, please check root model class(" + root.getName() + "), path(" + fullPath + ")");
      }
    }
  }

}
