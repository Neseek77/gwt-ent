/*
 * Copyright 2007 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Default implementation of the {@link HasAnnotations} interface.
 */
class Annotations implements HasAnnotations {
  /**
   * All annotations declared on the annotated element.
   */
  private final Map<Class<? extends Annotation>, AnnotationStore> declaredAnnotations = new HashMap<Class<? extends Annotation>, AnnotationStore>();

  /**
   * Lazily initialized collection of annotations declared on or inherited by
   * the annotated element.
   */
  private Map<Class<? extends Annotation>, AnnotationStore> lazyAnnotations = null;

  /**
   * If not <code>null</code> the parent to inherit annotations from.
   */
  private Annotations parent;

  Annotations() {
  }
  
  Annotations(Annotations otherAnnotations) {
    if (otherAnnotations != null) {
      AnnotationStore[] otherDeclaredAnnotations = otherAnnotations.getDeclaredAnnotations();
      for (AnnotationStore otherDeclaredAnnotation : otherDeclaredAnnotations) {
        addAnnotation(otherDeclaredAnnotation.annotationType(),
            otherDeclaredAnnotation);
      }
    }
  }

  Annotations(Map<Class<? extends Annotation>, AnnotationStore> declaredAnnotations) {
    this.declaredAnnotations.putAll(declaredAnnotations);
  }

  public void addAnnotations(
      List<AnnotationStore> annotations) {
    if (annotations != null) {
      for (AnnotationStore store : annotations) {
        declaredAnnotations.put(store.annotationType(), store);
      }
      //declaredAnnotations.putAll(annotations);
    }
  }

  public <T extends Annotation> AnnotationStore getAnnotation(Class<T> annotationClass) {
    initializeAnnotations();
    return lazyAnnotations.get(annotationClass);
    //return annotationClass.cast(lazyAnnotations.get(annotationClass));
  }

  public AnnotationStore[] getAnnotations() {
    initializeAnnotations();
    Collection<AnnotationStore> values = lazyAnnotations.values();
    return values.toArray(new AnnotationStore[values.size()]);
  }

  public AnnotationStore[] getDeclaredAnnotations() {
    Collection<AnnotationStore> values = declaredAnnotations.values();
    return values.toArray(new AnnotationStore[values.size()]);
  }

  public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
    return getAnnotation(annotationClass) != null;
  }

  void addAnnotation(Class<? extends Annotation> annotationClass,
      AnnotationStore annotationInstance) {
    assert (annotationClass != null);
    assert (annotationInstance != null);
    assert (!declaredAnnotations.containsKey(annotationClass));

    declaredAnnotations.put(annotationClass, annotationInstance);
  }

  void setParent(Annotations parent) {
    this.parent = parent;
  }

  private void initializeAnnotations() {
    if (lazyAnnotations != null) {
      return;
    }

    if (parent != null) {
      lazyAnnotations = new HashMap<Class<? extends Annotation>, AnnotationStore>();
      parent.initializeAnnotations();
      for (Entry<Class<? extends Annotation>, AnnotationStore> entry : parent.lazyAnnotations.entrySet()) {
//        if (entry.getValue().annotationType().isAnnotationPresent(
//            Inherited.class)) {
//          lazyAnnotations.put(entry.getKey(), entry.getValue());
//        }
      }

      lazyAnnotations.putAll(declaredAnnotations);
    } else {
      lazyAnnotations = declaredAnnotations;
    }
  }
}
