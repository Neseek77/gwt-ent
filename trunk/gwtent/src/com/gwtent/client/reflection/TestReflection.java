package com.gwtent.client.reflection;

import java.util.Date;

import com.gwtent.client.ui.model.Fields;
import com.gwtent.client.ui.model.impl.UIFriendlyDefaultImpl;
import com.gwtent.client.ui.validate.ValidateException;

public class TestReflection implements Reflection {
	
	private UIFriendlyDefaultImpl uiFriendly;
	
	/**
	 * @Caption 出生日期
	 */
	private Date date;
	private String string;
	private boolean bool;
	
	public TestReflection(){
		

//		uiFriendly = new UIFriendlyDefaultImpl();
//		
//		uiFriendly.getFields().addFieldInfo("string", "姓名", true, "请输入姓名", new Validate(){
//
//			public void validate(Object value) throws ValidateException {
//				// server validate is there the same name
//				
//				
//			}
//			
//		});
//		uiFriendly.getFields().addFieldInfo("bool", "性别", false, "请输入性别,只能够是男或者女");
//		uiFriendly.getFields().addFieldInfo("date", "日期", false, "请输入出生日期", new Validate(){
//			public void validate(Object value) throws ValidateException {
//				if (value instanceof Date){
//					throw new ValidateException("出错了,只能够是日期.");
//				}
//				
//			}
//		});
	}
	
	public boolean getBool() {
		return bool;
	}
	public void setBool(boolean bool) {
		this.bool = bool;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getString() {
		return string;
	}
	public void setString(String string) {
		this.string = string;
	}
	
	
	public Fields getFields() {
		return uiFriendly.getFields();
	}
	
	public void wholeValidate(Object value) throws ValidateException {
		// TODO Auto-generated method stub
		
	}

}
