/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.client.ui;

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
