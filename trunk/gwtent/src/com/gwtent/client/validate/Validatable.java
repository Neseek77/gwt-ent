package com.gwtent.client.validate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gwtent.client.reflection.annotations.Reflect_Full;

/**
 * Identify the object is Validate able
 * 
 * Actually validate implement for GWT just request Reflection.
 * So if some class already marked with kink of @Reflection
 * It's ready to validation 
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Reflect_Full
public @interface Validatable {

}
