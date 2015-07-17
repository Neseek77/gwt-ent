# Introduction #

GWTENT provide the following useful modules, and most of this modules are designed for easy to extend.

# All-In-One #
```
  <inherits name="com.gwtent.GwtEnt" />
```
> To make it easy, You can using this module to include all GWTENT main modules to your project.
> Please note, this is not include "(JPA) Emulation" and "Gxt binders" cause those modules are in different jar files.

# Basic Modules #
> ## Common ##
```
  <inherits name="com.gwtent.common.Common" />
```
> The common codes in gwtent, nearly all gwtent modules are inherited from this modules.
> It defines common utilities and common interfaces such as:
```
  public interface ObjectFactory<T extends Object> {
    public T getObject();
  }
```
```
  public interface NotifyListener<T extends Object> {
	public void onNotify(T sender);
  }
```

> ## Javax Validation Emulation ##
```
  <inherits name="javax.validation.Validation"/>
```

> ## Javax Persistence (JPA) Emulation ##
```
  <inherits name="javax.persistence.Persistence"/>
```
> This need you include "**gwtent-orm.jar**" to your classpath

> ## PageBus integration ##
```
  <inherits name="com.gwtent.pagebus.PageBus" />
```

> ## AspectJ Annotations Emulation ##
```
  <inherits name="org.aspectj.lang.annotation.AspectJAnnotation" />
```

# Reflection #

```
  <inherits name="com.gwtent.reflection.Reflection" />
```
> Provide the reflection implement, please see this [link](http://code.google.com/p/gwt-ent/wiki/UseReflection) for more information

# UIBinder #
```
  <inherits name="com.gwtent.uibinder.UIBinder" />
```

> ## GXT Binder ##
```
  <inherits name="com.gwtent.uibinder.client.gxt.Gxt" />
```
> Bind your objects to GXT components, include the binder of Fields, Grids.
> You need include "**gwtent-gxt.jar**" to your class path

# Validation #
```
  <inherits name="com.gwtent.validate.Validate" />
```

# HTMLTemplate #
```
  <inherits name="com.gwtent.htmltemplate.HTMLTemplate" />
```

# Other Modules #
> ## Widget ##
```
  <inherits name="com.gwtent.widget.Widget" />
```

> ## Serialization - JSON or XML ##
```
  <inherits name="com.gwtent.serialization.Serialization" />
```
> Persist your domain object to JSON/XML or read it back. This project is based on Reflection and still in lab, please feedback if you need this feature.

> ## AOP ##
```
  <inherits name="com.gwtent.aop.AOP" />
```
> The AOP implement. please feedback if you got any idea about this.