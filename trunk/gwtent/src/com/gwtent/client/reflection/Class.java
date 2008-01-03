package com.gwtent.client.reflection;


public interface Class {
	public Object getContent();
	public void setContent(Object content);
	
	/**
	 * 对应一般性方法
	 * @param methodName
	 * @param args
	 * @return
	 */
	public Object invoke(String methodName, Object[] args);
	
	public ClassType getClassType();
	//public Object getFieldValue(String fieldName);
	//public void setFieldValue(String fieldName, Object arg);
}
