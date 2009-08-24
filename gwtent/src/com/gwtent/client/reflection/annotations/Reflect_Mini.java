package com.gwtent.client.reflection.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gwtent.client.reflection.Reflectable;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Reflectable(classAnnotations=false, relationTypes=false, superClasses=false, assignableClasses=false)
public @interface Reflect_Mini {

}
