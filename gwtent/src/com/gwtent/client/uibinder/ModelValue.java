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


package com.gwtent.client.uibinder;


/**
 * every type have their own edit type.
 * we have a registry record relation between type and edit type.
 * registry can override that other can control the edit type .
 * 
 * @author James Luo
 * 2007-12-25 下午08:30:11
 *
 */
public interface ModelValue<T> {
	public boolean getReadOnly();
	public void setReadOnly(boolean readOnly);
	
	public String getAsString();
	
	public UIBinderValidator getValidator();
	
	/**
	 * The Class of last level
	 * i.e: a.b.c.d, this is the class of d
	 * @return
	 */
	public Class<?> getValueClass();
	/**
	 * The Root class
	 * i.e: a.b.c.d, this is the class of a
	 * @return
	 */
	public Class<?> getRootClass();
	
	/**
	 * The full path, how to get value from RootClass
	 * @return
	 */
	public String getPropertyPath();
	
	public T getValue();
	public void setValue(T value);
	
	  /**
	   * Invoke when value changed by class, NOT by binding system.
	   * This is used for notice binding system something changed, please update editor.
	   */
	public void doValueChanged();
	
	 /**
	   * Invoke when value changed by class, NOT by binding system.
	   * This is used for notice binding system something changed, please update editor.
	   * 
	   * @see doValueChanged()
	   */
	public void addValueChangedListener(IValueChangedOutSideListener listener);
	public void removeValueChangedListener(IValueChangedOutSideListener listener);
	
	public void removeValueChangedByBindingListener(IValueChangedByBindingListener listener);
  public void addValueChangedByBindingListener(IValueChangedByBindingListener listener);
}
