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
