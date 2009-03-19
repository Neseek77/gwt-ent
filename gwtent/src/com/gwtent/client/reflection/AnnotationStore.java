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


/**
 * FileName: Annotation.java
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.client.reflection;

import java.lang.annotation.Annotation;

public interface AnnotationStore {
  Class<? extends Annotation> annotationType();
  
  /**
   * Just support string value for now, all other type is a string here(object.toString())
   */
  public String getValue(String name);
  
}
