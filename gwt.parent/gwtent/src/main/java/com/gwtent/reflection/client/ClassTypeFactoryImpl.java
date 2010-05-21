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

import java.util.HashMap;
import java.util.Map;

public class ClassTypeFactoryImpl implements ClassTypeFactory {

	private static Map map = new HashMap();
	private static ClassTypeFactory factory = new ClassTypeFactoryImpl();
	
	private ClassTypeFactoryImpl(){
		
	}
	
	public ClassType getClassType(String className) {
		ClassType type = (ClassType)map.get(className);
		if (type == null){
			
		}
		return type;
	}
	
	public static void setClassType(String className, ClassType type){
		map.put(className, type);
	}
	
	public static ClassTypeFactory getInstance(){
		return factory;
	}

}
