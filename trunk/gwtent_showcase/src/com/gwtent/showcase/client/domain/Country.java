package com.gwtent.showcase.client.domain;

public enum Country {
	Australia("au", "Australia"), UnitedStates("us", "UnitedStates"), China("cn", "China");
	private String code;
	private String desc;
	
	private Country(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}
	
	public String toString(){
		return desc;
	}
	
	public String getCode(){
		return code;
	}
}
