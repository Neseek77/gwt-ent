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


package com.gwtent.reflection.client;

import java.lang.annotation.Annotation;

import com.gwtent.reflection.client.impl.TypeOracleImpl;

/**
 * Interface implemented by elements that can have annotations. This interface
 * is a departure for GWT in that it used types declared in the
 * java.lang.annotation package instead of types declared as part of this
 * typeinfo package. This reflects a compromise between a pure
 * {@link TypeOracleImpl} model and one that is more useful to developers.
 */
public interface HasAnnotations extends AnnotatedElement{
	
	public void addAnnotation(Annotation ann);

}
