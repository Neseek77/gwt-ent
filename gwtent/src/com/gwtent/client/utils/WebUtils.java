package com.gwtent.client.utils;

import com.google.gwt.user.client.Random;

public class WebUtils {
	public static String getRandomElementID(){
		return "GWTElement" + Random.nextInt();
	}
}
