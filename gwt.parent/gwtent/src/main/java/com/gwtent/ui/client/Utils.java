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


package com.gwtent.ui.client;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class Utils {
	public static boolean includeStr(String[] strs, String str){
		for (int i = 0; i < strs.length; i++){
			if (str.equals(strs[i])){
				return true;
			}
		}
		return false;
	}
	
	public static String excludeInvertedComma(String str){
		if (str.startsWith("\"", 0) && str.endsWith("\"")){
			str = str.substring(1, str.length() - 1);
		} 
		return str;
	}
	
	public static boolean testIncludeInInvertedComma(String str){
		return str.startsWith("\"", 0) && str.endsWith("\"");
	}
	
	public static void addListByStrIFNotExists(Collection srcList, List destList){
		Iterator iter = srcList.iterator();
		while (iter.hasNext()) {
			String element = (String) iter.next();
			
			if (!includeStr(destList, element)){
				destList.add(element);
			}
		}
	}
	
	public static boolean includeStr(List list, String str){
		Iterator iterator = list.iterator();
		while (iterator.hasNext()) {
			String element = (String) iterator.next();
			
			if (str.equals(element)){
				return true;
			}
		}
		return false;
	}
}
