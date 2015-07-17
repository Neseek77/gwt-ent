# Why not direct emulate java.lang.Class? #

GWT has a version of java.lang.Class, that's why we can't emulate java.lang.Class.

We provide ClassHelper to emulate the most of functions in java.lang.Class


# Instantiation ClassHelper #

```
  ClassHelper.AsClass(Class<?> clazz);
```

# Usage #
Your code:
```
Object.class.getAnnotation(Constraint.class);
```

Just needs changed to :
```
ClassHelper.AsClass(Object.class).getAnnotation(Constraint.class);
```