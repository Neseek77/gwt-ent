package com.gwtent.client.reflection.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gwtent.client.reflection.Reflectable;

/**
 * Annotation for a Domain Class
 * All reflection information will be generated.
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Reflectable(relationTypes=true, superClasses=true, assignableClasses=true)
public @interface Reflect_Domain {

}
