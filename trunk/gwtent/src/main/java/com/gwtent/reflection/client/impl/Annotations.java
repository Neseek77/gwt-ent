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
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.gwtent.reflection.client.AnnotationType;
import com.gwtent.reflection.client.ClassHelper;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Constructor;
import com.gwtent.reflection.client.HasAnnotations;
import com.gwtent.reflection.client.TypeOracle;

/**
 * Default implementation of the {@link HasAnnotations} interface.
 */
class Annotations implements HasAnnotations {
	public static class AnnotationBean {
		AnnotationBean(ClassType<? extends Annotation> type,
				AnnotationValues ann) {
			this.type = type;
			this.ann = ann;

		}

		ClassType<? extends Annotation> type;
		AnnotationValues ann;
	}

	/**
	 * All annotations declared on the annotated element.
	 */
	private Map<Class<?>, Annotation> declaredAnnotations = new HashMap<Class<?>, Annotation>();

	private List<AnnotationBean> annotationBeans = new ArrayList<AnnotationBean>();
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

	Annotations(Map<Class<? extends Annotation>, Annotation> declaredAnnotations) {
		this.declaredAnnotations.putAll(declaredAnnotations);
	}

	public <T extends Annotation> T getAnnotation(Class<T> annotationClass) {
		initializeAnnotations();
		return (T) lazyAnnotations.get(annotationClass);
	}

	public Annotation[] getAnnotations() {
		initializeAnnotations();
		Collection<Annotation> values = lazyAnnotations.values();
		return values.toArray(new Annotation[values.size()]);
	}

	public Annotation[] getDeclaredAnnotations() {
		initializeAnnotations();
		Collection<Annotation> values = declaredAnnotations.values();
		return values.toArray(new Annotation[values.size()]);
	}

	public boolean isAnnotationPresent(
			Class<? extends Annotation> annotationClass) {
		return getAnnotation(annotationClass) != null;
	}

	public void addAnnotation(ClassType<? extends Annotation> type,
			AnnotationValues ann) {
		annotationBeans.add(new AnnotationBean(type, ann));
		// if (ann != null)
		// this.declaredAnnotations.put(ann.annotationType(), ann);
	}

	void setParent(HasAnnotations parent) {
		this.parent = parent;
	}

	private void initializeAnnotations() {
		if(annotationBeans.size()>0){
			for (int i = 0; i < annotationBeans.size(); i++) {
				AnnotationBean annotationBean = annotationBeans.get(i);
				ClassType<? extends Annotation> type = annotationBean.type;
				AnnotationValues ann = annotationBean.ann;
				if (type.isAnnotation() != null){
					AnnotationType<? extends Annotation> antoType = (AnnotationType<? extends Annotation>) type;
					Annotation result = antoType.createAnnotation(ann.getValues());
					declaredAnnotations.put(result.annotationType(), result);
				}
			}
			annotationBeans.clear();
		}
		if (lazyAnnotations != null) {
			return;
		}

		if (parent != null) {
			lazyAnnotations = new HashMap<Class<?>, Annotation>();
			// ((Annotations)parent).initializeAnnotations();
			// for (Entry<Class<?>, Annotation> entry :
			// ((Annotations)parent).lazyAnnotations.entrySet()) {
			// if
			// (entry.getValue().annotationType().isAnnotationPresent(Inherited.class))
			// {
			// lazyAnnotations.put(entry.getKey(), entry.getValue());
			// }
			// }

			for (Annotation a : parent.getAnnotations()) {
				if (ClassHelper.AsClass(a.annotationType())
						.isAnnotationPresent(Inherited.class)) {
					lazyAnnotations.put(a.annotationType(), a);
				}
			}

			lazyAnnotations.putAll(declaredAnnotations);
		} else {
			lazyAnnotations = declaredAnnotations;
		}
	}
}
