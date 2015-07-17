# Introduction #

FAQ


# Details #

# ClassNotFoundException #
Error java.lang.ClassNotFoundException: abc.def.BaseHTMLTemplate
Resolving annotation '@BaseHTMLTemplate("folder/html1.html")

> Please check your -cp parameter, make sure it pointer to somewhere which contains your annotations **classes**, ie, you think your classes in "%-dp0\bin", but actually it's in "%~dp0\build\classes"


# Can't match AspectJ express @annotation #
> for example, @annotation(com.gwtent.client.test.aop.Interceptors.AOPTestAnnotation)
> please make sure you have "@Retention(RetentionPolicy.RUNTIME)" on your annotation