UIBinding framework from gwt-ent

# Introduction #

GWT-ENT offer a easy to use UI Binding framework for GWT users.
It's based on GWT-ENT reflection implement, and provide two way
to use it.

  1. Annotation with UIBinder in GWT 2.0
  1. Annotation with HTML Template
  1. Intergrate by Code

# Registe UI Bind #
> Before you can use ui binding, please registe your ui binder first, depends on which kind of UI library you are using, you need registe or create UI binder by your self.
> GWT-ENT provider GWT and GXT editor UI Binders.
```
  GWTUIBinderRegister.register();   //if you are using GWT UI stuff
  GxtUIBinderRegister.register();   //if you are using GXT framework
```

> You can register both or more.

> You can get more information about create UI Binders of your UI stuff in this document.

# The easier way, Annotation with UIBinder(GWT2.0) and HTML Template #

> HTML Template introduce a new Annotation "UIBind".
```
  @HTMLWidget
  @UIBind(path="testModel.firstName")
  protected TextBox txtFirstName = new TextBox();
```
> This is mean widget TextBox named txtFirstName will bind to testModel.firstName.

> UIBind support variable binding as well.
```
  @HTMLWidget
  @UIBind(path="varToBind")
  protected TextBox txtBindToVariable = new TextBox();
```

# Integrate UIBind via code #

```
    UIBinderManager uiBinderManager = new UIBinderManager();

    uiBinderManager.addBinder(txtBindToVariable, "", false, String.class,
        new ModelRootAccessor(){

          public Object getValue() {
            return varToBind;
          }

          public void setValue(Object value) {
            varToBind = (String)value;
          }});
    
    uiBinderManager.addBinder(txtFirstName, "firstName", false, TestModel.class,
        new ModelRootAccessor(){

          public Object getValue() {
            return testModel;
          }

          public void setValue(Object value) {
            //
          }});
```

# Tips #

> ### Using function call to reduce length of path ###
```
  @UIBind(path="getSomething().abc")

  For now just support function call with out parameters
```


# Upgrade HTMLTemplate from 0.7 #
> Normally you don't have to change any of your HTMLTemplate code, but if you have call uibinder related function in your HTMLTemplate constructor, instead, please override "doAfterBinderAllEditors()" function to do what you want to do in constructor.