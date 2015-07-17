# Introduction #

gwt use source.println() to generator code, this is very hard
to read what your print to, for example the fllowing code:
```
for (int i = 0; i < methods.length; i++) {
	String methodName = methods[i].getName();
	JParameter[] methodParameters = methods[i].getParameters();
	JType returnType = methods[i].getReturnType();

	source.println("if (methodName.equals(\""
			+ methodName
			+ "\")) {");
	source.indent();
	source.println("checkInvokeParams(methodName, " + methodParameters.length + ", args);");
	
	if (!returnType.getSimpleSourceName().equals("void")){
		source.println("return " + boxIfNeed(returnType.getSimpleSourceName(), 
				"content." + methodName	+ "(" + getInvokeParams(methodParameters, "args") + ")") + ";");
	}else{
		source.println("content." + methodName	+ "(" + getInvokeParams(methodParameters, "args") + ")" + ";");
		source.println("return null;");
	}
	
	source.outdent();
	source.print("} else ");

}
```


# Our approach #
**Display in treelog and compile output** Can expand

![http://gwt-ent.googlecode.com/svn/images/sourcelogger.jpg](http://gwt-ent.googlecode.com/svn/images/sourcelogger.jpg)
![http://gwt-ent.googlecode.com/svn/images/sourceloginconsole.jpg](http://gwt-ent.googlecode.com/svn/images/sourceloginconsole.jpg)


# How to use #

## The first method ##
we provide a class named "SourceWriterLogDecorator", a LogDecorator
for SourceWriter class, you just create your SourceWriter object
normally and pass it to SourceWriterLogDecorator's constructor,
for example:
```
sourceWriter = doGetSourceWriter(classType);  // your normally SourceWriter 
if (sourceWriter != null)
	sourceWriter = new SourceWriterLogDecorator(sourceWriter, this.logger, useLog, 5);
```
SourceWriterLogDecorator use the same interface, so all of your other code not need
any change.

## The second method ##
we provide LogableSourceCreator abstract class, your just inherited from
this class and implement the abstract method:
`protected abstract SourceWriter doGetSourceWriter(JClassType classType);`

# How to implement #
The implementation uses a simplified decorator pattern