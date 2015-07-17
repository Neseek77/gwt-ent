# Introduction #

To avoid the error in shell model like :Failed to create an instance of 'com.gwtent.client.reflection.TypeOracle' via deferred binding
java.lang.NoClassDefFoundError: javax/validation/ConstraintValidator


# Details #

GWT 1.5x shell class loader never load "System" classes, i.e, the package name start with "java" or "javax". The gwtent-gwt15patch.jar is created for this.

Put "gwtent-gwt15patch.jar" in your classpath, and make sure "gwtent-gwt15patch.jar" is before your "gwt-dev-windows.jar", that's all.