/**
 * FileName: Reflection.java Date: 09/09/2008 4:26:18 PM Author:
 * JamesLuo.au@gmail.com purpose:
 * 
 * History:
 * 
 */

package com.gwtent.client.test;

import com.google.gwt.core.client.GWT;
import com.google.gwt.junit.client.GWTTestCase;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Field;
import com.gwtent.client.test.annotations.Entity;
import com.gwtent.client.test.annotations.Id;
import com.gwtent.client.test.annotations.Table;

public class ReflectionTestCase extends GWTTestCase {

  @Override
  public String getModuleName() {
    return "com.gwtent.GwtEnt";
  }

  public void testFields() {
    TestReflection test = new TestReflection();
    test.setString("username");
    assertTrue(test.getString().equals("username"));
    
    ClassType classType = (ClassType)GWT.create(TestReflection.class);
    Field[] fields = classType.getFields();
    assertTrue(fields.length == 4);
    assertTrue(classType.findField("bool").getTypeName().equals("boolean"));
  }

  public void testAnnotations(){
    TestReflection test = new TestReflection();
    test.setString("username");
    assertTrue(test.getString().equals("username"));
    
    ClassType classType = (ClassType)GWT.create(TestReflection.class);
    //Class Annotations
    assertNotNull(classType.getAnnotation(Entity.class));
    assertTrue(classType.getAnnotation(Entity.class).getValue("name").equals("TestReflection"));
    assertTrue(classType.getAnnotation(Table.class).getValue("name").equals("Table_Test"));
    
    //Method Annotations
    assertNotNull(classType.findMethod("getId", new String[]{}).getAnnotation(Id.class));
    
    //Field Annotations
    assertNotNull(classType.findField("id").getAnnotation(Id.class));
  }

  public void testMethods() {
    
  }

  public void testInvokeMethod() {
    TestReflection account = new TestReflection();
    account.setString("username");
    assertTrue(account.getString().equals("username"));
    
    ClassType classType = (ClassType)GWT.create(TestReflection.class);
    assertTrue(classType.invoke(account, "getString", null).equals("username"));
    classType.invoke(account, "setString", new String[]{"username set by reflection"});
    assertTrue(account.getString().equals("username set by reflection"));
  }

}
