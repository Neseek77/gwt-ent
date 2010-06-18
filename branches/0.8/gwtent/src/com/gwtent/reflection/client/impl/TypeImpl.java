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

import com.gwtent.reflection.client.ArrayType;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.PrimitiveType;
import com.gwtent.reflection.client.Type;



public abstract class TypeImpl implements Type {
	public TypeImpl(){
		
	}
	
	  public abstract String getJNISignature();

	  public Type getLeafType() {
	    return this;
	  }

	  public String getParameterizedQualifiedSourceName() {
	    return getQualifiedSourceName();
	  }

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#getQualifiedSourceName()
	 */
	public abstract String getQualifiedSourceName();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#getSimpleSourceName()
	 */
	public abstract String getSimpleSourceName();

	public abstract ArrayType isArray();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isClass()
	 */
	public abstract ClassType isClass();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isClassOrInterface()
	 */
	public ClassType isClassOrInterface() {
	    ClassType type = isClass();
	    if (type != null) {
	      return type;
	    }
	    return isInterface();
	  }

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isInterface()
	 */
	public abstract ClassType isInterface();

	  //public abstract ParameterizedType isParameterized();

	  /* (non-Javadoc)
	 * @see com.gwtent.client.reflection.Type#isPrimitive()
	 */
	public abstract PrimitiveType isPrimitive();
}