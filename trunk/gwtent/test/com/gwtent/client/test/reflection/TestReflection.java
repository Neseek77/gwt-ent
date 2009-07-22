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


package com.gwtent.client.test.reflection;

import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.test.annotations.*;
import com.gwtent.client.validate.constraints.Regular;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.constraints.NotNull;


@Entity(name="TestReflection")
@Table(name="Table_Test")
public class TestReflection<T> implements Reflection {
	private Date date;
	
	@Regular(regex="[0-9]\\.[0-9]")
	private String string;
	private boolean bool;
	private List<String> names;
	private Set<String>	sets;
	private T t;
	
	@Id
	@NotNull
  private String id;
	
	public TestReflection(){
		
	}
	
	@Id
  public String getId() {
    return id;
  }

  public void setId(String Id) {
    this.id = Id;
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

	public void setNames(List<String> names) {
		this.names = names;
	}

	public List<String> getNames() {
		return names;
	}

	public void setT(T t) {
		this.t = t;
	}

	public T getT() {
		return t;
	}

	public void setSets(Set<String> sets) {
		this.sets = sets;
	}

	public Set<String> getSets() {
		return sets;
	}

}
