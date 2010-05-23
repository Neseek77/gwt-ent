package com.gwtent.showcase.client.reflection;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.reflection.client.annotations.Reflect_Domain;
import com.gwtent.validate.client.constraints.Regular;


@HTMLTemplate("com/gwtent/showcase/public/reflection/ReflectionBasicPage.html")
public class ReflectBasicPage extends AbsReflectionPage {


	@Entity(name="TestReflection")
	@Table(name="Table_Test")
	@Reflect_Domain
	public static class TestReflection<T> {
		private Date date;
		
		@Regular(regex="[0-9].[0-9]")
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
	
	public ReflectBasicPage(String html) {
		super(html);
	}

	
	public String[] getResourceFileNames() {
		return new String[]{"ReflectBasicPage.java", "ReflectionBasicPage.html"};
	}

	public String getCaseName() {
		return "Basic Reflection Page";
	}


	@Override
	protected Class<?>[] getReflectionClasses() {
		return new Class<?>[]{TestReflection.class};
	}

}
