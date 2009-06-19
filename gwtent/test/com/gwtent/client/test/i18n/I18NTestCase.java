package com.gwtent.client.test.i18n;

import com.google.gwt.core.client.GWT;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.test.GwtEntTestCase;

public class I18NTestCase extends GwtEntTestCase{


  public void testMessageReflection(){
  	ErrorMessages errorMessages = GWT.create(ErrorMessages.class);
  	
  	//ClassType type = TypeOracle.Instance.getClassType(ErrorMessages.class);
  	ClassType type = TypeOracle.Instance.getClassType(errorMessages.getClass());
  	assertTrue(type.getMethods().length == 1);
  	
  	Method permissionDenied = type.findMethod("permissionDenied", "java.lang.String", "java.lang.String", "java.lang.String");
  	String result = (String)permissionDenied.invoke(errorMessages, "James", "guest", "delete");
  	assertTrue(result.equals(errorMessages.permissionDenied("James", "guest", "delete")));
  }
  
}
