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

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintValidator;
import javax.validation.constraints.NotNull;

import com.gwtent.client.reflection.HasReflect;
import com.gwtent.client.reflection.Reflectable;
import com.gwtent.client.test.annotations.Entity;
import com.gwtent.client.test.annotations.Id;
import com.gwtent.client.test.annotations.Table;
import com.gwtent.client.validate.constraints.Regular;


@Entity(name="TestReflection")
@Table(name="Table_Test")
@Reflectable(fields = false, methods = false)
public class ReflectionSaveSize<T> extends ReflectParent{
	
	public static class ThisShouldNotThere{
		
	}
	
	public static class ThisShouldThere{
		
	}
	
	public static @interface Anno{
		public Class<?>[] validatedBy();
	} 
	
	public static class ClassRefereceByAnno{
		
	}
	
	private Date date;
	
	@HasReflect
	@Regular(regex="[0-9]\\.[0-9]")
	private String string;
	private boolean bool;
	private List<String> names;
	private Set<String>	sets;
	private T t;
	
	private ThisShouldNotThere shouldNotThere;
	
	@HasReflect
	@Anno(validatedBy = { ClassRefereceByAnno.class })
	private ThisShouldThere thisShouldThere;
	
	@Id
	@NotNull
  private String id;
	
	public ReflectionSaveSize(){
		
	}
	
	@Id
	@HasReflect
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

	public void setShouldNotThere(ThisShouldNotThere shouldNotThere) {
		this.shouldNotThere = shouldNotThere;
	}

	public ThisShouldNotThere getShouldNotThere() {
		return shouldNotThere;
	}

	public void setThisShouldThere(ThisShouldThere thisShouldThere) {
		this.thisShouldThere = thisShouldThere;
	}

	public ThisShouldThere getThisShouldThere() {
		return thisShouldThere;
	}

}
