

# Introduction #

For maximum efficiency, GWT compiles your Java source into a monolithic script. Now gwt-ent provide a reflection implement by gwt code generator mechanism.

This Framework support
  1. Class http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/ClassType.java
  1. Field http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/Field.java
  1. Method http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/Method.java
  1. Constructor http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/Constructor.java
  1. Metedata http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/HasMetaData.java
  1. Annotation http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/HasAnnotations.java

# How to use #

# add Annotation Or Reflection Marker interface into your pojo class
```
@Reflectable
public class TestReflection{
}

public class TestReflection implements Reflection{
}

@Reflect_Domain
public class TestReflection{
}

```

# get ClassType.
```
ClassType classType = TypeOracle.Instance.getClassType(TestReflection.class);
```

For example:
```
public void testReflection(){
    TestReflection test = new TestReflection();
    ClassType classType = TypeOracle.Instance.getClassType(TestReflection.class);
    
    test.setString("set by code");
    System.out.println("after SetByCode:" + test.getString());
    System.out.println("after SetByCode:" + classType.invoke(test, "getString", null));
    
    classType.invoke(test, "setString", new Object[]{"set by invoke"});
    System.out.println("after SetByInvoke:" + classType.invoke(test, "getString", null));
  }
}
```

The output is :
```
after SetByCode:set by code
after SetByCode:set by code
after SetByInvoke:set by invoke
```

## Control you javascript size ##
GWTENT provide you more power to control how much reflection information created.

### The Class Scope ###
> You can control whether have:
  1. Class Annotation
  1. Fields
  1. Methods
  1. Constructors
  1. Field and Method Annotations
  1. Relation Types(The field type, the return type of method, the type of Parameters)
  1. Super Classes ( all the super classes of this class will generate reflection information)
  1. Assignable Classes (all assignable classes. if annotate to a class, this is mean All subClasses will generate reflection information; if annotate to a interface, then all implement classes will generate reflection information; if annotate to an annotation, then all types annotated by this annotation will generate reflection information)

> [Click Here For more Information](http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/Reflectable.java)

### The Field/Method Scope ###
> You can control whether have:
  1. Annotations
  1. Field Type
  1. Method Return Type
  1. Method Parameter Type

> [Click Here For more Information](http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/HasReflect.java)

## The TestCase ##
[ReflectionTestCase](http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/test/java/com/gwtent/client/test/reflection/ReflectionTestCase.java)

# Understand Reflection Classes #

http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent/src/main/java/com/gwtent/reflection/client/

![http://gwt-ent.googlecode.com/svn/images/ReflectionInherited.jpg](http://gwt-ent.googlecode.com/svn/images/ReflectionInherited.jpg)

![http://gwt-ent.googlecode.com/svn/images/ClassType.jpg](http://gwt-ent.googlecode.com/svn/images/ClassType.jpg)