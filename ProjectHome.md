# PLEASE READ ME FIRST #
There is **no further development plan** for this project.

For Validation(JSR-303) Implementation, GWT has [this support](https://developers.google.com/web-toolkit/doc/latest/DevGuideValidation) built in now, it's better.

For [HTML Template](http://code.google.com/p/gwt-ent/wiki/HTMLTemplate) feature, GWT has [UIBinder](https://developers.google.com/web-toolkit/doc/latest/DevGuideUiBinder) built in now.

For Reflection and AOP implementation, you are welcome download source code and have a look how it been implemented, the key is code generation feature provided by GWT, once you know how code generation works, you will be able to implement your version of reflection and AOP, very easy :)

Good luck.

# GWT ENT #
A Reflection, HTML template, Data Binding, Validate, AOP framework for GWT.

This project aims to make it easier to do what you want to do with GWT, now, the fllowing things have been contained in our project:

> ## Reflection ##
> Reflection for GWT, Support reflection of the following elements:
    1. annotation
    1. class
    1. field
    1. method
    1. constructor(constructor.newInstance())
    1. meta data of class(Going to be deleted, please using annotation instead)

> ## UIBinding ##
> UI Binding, UIBinding framework from gwt-ent, Annotation or code based, support only code/GWTUIBdiner(GWT2.0)/HTMLTemplate([UIBinding](http://code.google.com/p/gwt-ent/wiki/UIBinding)).

> ## Validate ##
> Support JSR-303, easy to create your own validator and the GWT way to support I18N

> ## HTML Template ##
> HTML template, Annotation based HTML template framework for GWT([HTML Template](http://code.google.com/p/gwt-ent/wiki/HTMLTemplate)).
> With this function you can collaborate with your UI designer

> ## Aspect-Oriented Programming for GWT ##
> Support AOP in Client side, coded by JAVA(not based javascript library).

> Support both aspectj annotations and @pointcut/Google-guice matcher class, Please see [here](http://gwt-ent.googlecode.com/svn/trunk/gwtent-sample/src/com/gwtent/sample/client/Interceptors.java) for all interceptors in sample project.
    1. @Around
    1. @Before
    1. @After
    1. @AfterReturning
    1. @AfterThrowing


> ## JPA Annotations Emulation ##
> Give you ability to share domain classes between server side and client side.

> ## Others ##
> gwt acegi integration([IntegrationGWTWithAcegi](http://code.google.com/p/gwt-ent/wiki/IntegrationGWTWithAcegi))

Please note that, all source code available at: http://code.google.com/p/gwt-ent/source

# Showcase #
> Showcase available at: [Gwtent\_Showcase](http://gwtent-showcase.appspot.com/)

> Please have a look the showcase video.

> Part1: http://www.screencast.com/users/JamesLuo.au/folders/Jing/media/d4ff284b-14ab-4fbc-a1c5-1717028209b5

> Part2: http://www.screencast.com/users/JamesLuo.au/folders/Jing/media/3ed91f56-da09-48f2-9f87-c5b3d14dc6dd

> The source code of showcase available at: [SourceCode](http://code.google.com/p/gwt-ent/source/browse/#svn/trunk/gwtent_showcase)

> Please check this file to see **how easy** to create the following page: [HTMLTplUIBindPage.java](http://code.google.com/p/gwt-ent/source/browse/trunk/gwtent_showcase/src/com/gwtent/showcase/client/htmltemplate/HTMLTplUIBindPage.java)
http://gwt-ent.googlecode.com/svn/images/showcase-htmltemplate.JPG

# Need you help #
> We need you join us, we need more function, better performance, more test case and more document.
> And if you got any issues, please log it at http://code.google.com/p/gwt-ent/issues/list

> Thank you.


# Change History #
> ### 20090620 ###
    1. Reflection talk to @Reflectable all the time and provide The ability to "fine-tune", you can setup if you need "relationTypes", "superClasses", "assignableClasses", this will help you reduce your final javascript size.
    1. meta data deleted from Reflection
    1. More features under test and coming soon!

> ### 20090503 ###
    1. Reflection support Annotation in a more nature way

> ### 20090405 ###
    1. gwtent-0.6 now available for download
    1. Annotation based HTML Template for GWT
    1. Reflection bug fixes

> ### 20081122 ###
    1. gwtent-0.5 now available for download
    1. The AOP framework in GWT
    1. Thanks to my wife, today is her birthday
> ### 20081104 ###
    1. gwtent-0.3 now available for download
    1. Reflection now support inherited
    1. Full support for inner Class
    1. Full refactor of com.gwtent.client.reflection.