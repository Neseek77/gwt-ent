package com.gwtent.client.serialization;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.gwtent.client.reflection.Reflectable;
import com.gwtent.client.reflection.annotations.Reflect_Full;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Reflect_Full
public @interface DataContract {
	
	/**
	 * if tagart class is a container, ie, List, Set
	 * This return the contained object class
	 * TList<Person>, here is Person.class
	 * @return
	 */
	Class<?> type() default Object.class;
}
