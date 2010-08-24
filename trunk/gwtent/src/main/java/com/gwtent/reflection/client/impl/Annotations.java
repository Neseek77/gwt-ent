/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.reflection.client.impl;

import java.lang.annotation.Annotation;
import java.lang.annotation.Inherited;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gwtent.reflection.client.ClassHelper;
import com.gwtent.reflection.client.HasAnnotations;

/**
 * Default implementation of the {@link HasAnnotations} interface.
 */
class Annotations implements HasAnnotations {
  /**
   * All annotations declared on the annotated element.
   */
  private final Map<Class<?>, Annotation> declaredAnnotations = new HashMap<Class<?>, Annotation>();

  /**
   * Lazily initialized collection of annotations declared on or inherited by
   * the annotated element.
   */
  private Map<Class<?>, Annotation> lazyAnnotations = null;

  /**
   * If not <code>null</code> the parent to inherit annotations from.
   */
  private HasAnnotations parent;

  Annotations() {
  }
  
  Annotations(Annotations otherAnnotations) {
    if (otherAnnotations != null) {
      Annotation[] otherDeclaredAnnotations = otherAnnotations.getDeclaredAnnotations();
      for (Annotation otherDeclaredAnnotation : otherDeclaredAnnotations) {
        addAnnotation(otherDeclaredAnnotation.annotationType(),
            otherDeclaredAnnotation);
      }
    }
  }

  Annotations(Map<Class<? extends Annotation>, Annotation> declaredAnnotations) {
    this.declaredAnnotations.putAll(declaredAnnotations);
  }

  public void addAnnotations(
      List<Annotation> annotations) {
    if (annotations != null) {
      for (Annotation store : annotations) {
        declaredAnnotations.put(store.annotationType(), store);
      }
      //declaredAnnotations.putAll(annotations);
    }
  }

  public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
    initializeAnnotations();
    return (T) lazyAnnotations.get(annotationClass);
    //return annotationClass.cast(lazyAnnotations.get(annotationClass));
  }

  public Annotation[] getAnnotations() {
    initializeAnnotations();
    Collection<Annotation> values = lazyAnnotations.values();
    return values.toArray(new Annotation[values.size()]);
  }

  public Annotation[] getDeclaredAnnotations() {
    Collection<Annotation> values = declaredAnnotations.values();
    return values.toArray(new Annotation[values.size()]);
  }

  public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
    return getAnnotation(annotationClass) != null;
  }

  void addAnnotation(Class<?> annotationClass,
      Annotation annotationInstance) {
    assert (annotationClass != null);
    assert (annotationInstance != null);
    assert (!declaredAnnotations.containsKey(annotationClass));

    declaredAnnotations.put(annotationClass, annotationInstance);
  }

  void setParent(HasAnnotations parent) {
    this.parent = parent;
  }

  private void initializeAnnotations() {
    if (lazyAnnotations != null) {
      return;
    }

    if (parent != null) {
      lazyAnnotations = new HashMap<Class<?>, Annotation>();
//      ((Annotations)parent).initializeAnnotations();
//      for (Entry<Class<?>, Annotation> entry : ((Annotations)parent).lazyAnnotations.entrySet()) {
//        if (entry.getValue().annotationType().isAnnotationPresent(Inherited.class)) {
//          lazyAnnotations.put(entry.getKey(), entry.getValue());
//        }
//      }
      
      for (Annotation a : parent.getAnnotations()){
      	if (ClassHelper.AsClass(a.annotationType()).isAnnotationPresent(Inherited.class)){
      		lazyAnnotations.put(a.annotationType(), a);
      	}
      }

      lazyAnnotations.putAll(declaredAnnotations);
    } else {
      lazyAnnotations = declaredAnnotations;
    }
  }
}
