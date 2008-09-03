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
package com.gwtent.client.ui.transition.example;

import java.util.Date;

import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.ui.validate.ValidateCallBack;

/*
 * usage:
 * 
 * 1, Class Level
 * 		@List indication which field will be show in UI
 * 			support field name and function that have return value
 * 			this metatag also indication the field's order 
 * 
 * 2, Field Level
 * 		@Caption 	the caption of this field, can be a String or a Function
 * 			Example: "Caption" "Name" getCaption getNameCaption
 * 
 * 		@Desc 		the description of this field
 * 			Example: "This is desc of field" getNameDesc
 * 
 * 		@Validates 	Validates of this field,can be a String or a Function
 * 					support locate validate and remote validate.
 * 					LocateValidate: if function looks like this: nameValidate(Object Value)
 * 					it means LocateValidate
 * 					RemoteValidate: if function have two params and looks like this: 
 * 						nameValidate(Object Value, ValidateCallBack callBack), it means RemoteValidate
 * 			Example: NotNULL, checkNameValidate, checkNameValidateRemote
 */

/**
 * 
 * @author James Luo
 * 2007-12-27 下午08:56:15
 *
 * @List name email sex getDesc
 * @Caption "Person"
 */
public class Person implements Reflection {
	/**
	 * 
	 * @Caption "名称:"
	 * @Desc "Name should use A-Z a-z 0-9"
	 * @Validate checkNameValidate checkNameValidateRemote
	 */
	private String name;
	private String email;
	private Sex sex;
	private Date birthday;
	
	public String getDesc(){
		return "Name:" + name + "  Email: " + email;
	}
	
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Sex getSex() {
		return sex;
	}
	public void setSex(Sex sex) {
		this.sex = sex;
	}
	
		
}
