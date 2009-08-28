package com.gwtent.showcase.client;

import com.gwtent.client.reflection.ReflectionUtils;

public class Utils {
	public static String getClassDescription(Class<?>... classes){
		StringBuilder sb = new StringBuilder();
		
		for (Class<?> clazz : classes){
			sb.append("start-------->>>>>>>>>>\n");
			sb.append("Class reflection information : " + clazz.getName()).append("\n");
			sb.append(ReflectionUtils.getDescription(clazz)).append("\n");
			sb.append("end----------<<<<<<<<<<\n");
		}
		
		return sb.toString().replace("\n", "<br>");
	}
}
