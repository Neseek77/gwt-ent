package com.gwtent.showcase.client.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.reflection.client.HasReflect;
import com.gwtent.reflection.client.Reflectable;
import com.gwtent.reflection.client.annotations.Reflect_Domain;
import com.gwtent.reflection.client.annotations.Reflect_Full;
import com.gwtent.reflection.client.annotations.Reflect_Mini;


@HTMLTemplate("com/gwtent/showcase/public/reflection/ReflectionBasicPage.html")
public class ReflectControlSizePage extends AbsReflectionPage {

	@Retention(RetentionPolicy.RUNTIME)
	@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
	public static @interface Anno{
		
	}

	@Reflect_Domain
	@Anno
	public static class Class_Domain {
		@Anno
		private String str;
		private Date date;
		
		@Anno
		public void setDate(Date date) {
			this.date = date;
		}
		public Date getDate() {
			return date;
		}
		public void setStr(String str) {
			this.str = str;
		}
		public String getStr() {
			return str;
		}
	}
	
	@Anno
	@Reflect_Full
	public static class Class_Full {
		@Anno
		private String str;
		private Date date;
		
		@Anno
		public void setDate(Date date) {
			this.date = date;
		}
		public Date getDate() {
			return date;
		}
		public void setStr(String str) {
			this.str = str;
		}
		public String getStr() {
			return str;
		}
	}
	
	@Anno
	@Reflect_Mini
	public static class Class_Mini {
		@Anno
		private String str;
		private Date date;
		
		@Anno
		public void setDate(Date date) {
			this.date = date;
		}
		public Date getDate() {
			return date;
		}
		public void setStr(String str) {
			this.str = str;
		}
		public String getStr() {
			return str;
		}
	}
	
	@Anno
	@Reflectable(classAnnotations = true, fields = false, methods = false, constructors = false)
	public static class Class_Custom1 {
		@Anno
		private String str;
		private Date date;
		
		@Anno
		@HasReflect(parameterTypes = true)
		public void setDate(Date date) {
			this.date = date;
		}
		public Date getDate() {
			return date;
		}
		public void setStr(String str) {
			this.str = str;
		}
		
		@HasReflect(resultType = true)
		public String getStr() {
			return str;
		}
	}
	
	
	@Anno
	@Reflectable(classAnnotations = true, fields = true, methods = false, fieldAnnotations = true, constructors = false)
	public static class Class_Custom2 {
		@Anno
		private String str;
		private Date date;
		
		@Anno
		public void setDate(Date date) {
			this.date = date;
		}
		public Date getDate() {
			return date;
		}
		public void setStr(String str) {
			this.str = str;
		}
		
		@HasReflect(resultType = true)
		public String getStr() {
			return str;
		}
	}
	
	public ReflectControlSizePage(String html) {
		super(html);
	}

	
	public String[] getResourceFileNames() {
		return new String[]{"ReflectControlSizePage.java", "ReflectionBasicPage.html"};
	}

	public String getCaseName() {
		return "Basic Reflection Page";
	}

	@Override
	protected Class<?>[] getReflectionClasses() {
		return new Class<?>[]{Class_Domain.class, Class_Full.class, Class_Mini.class, Class_Custom1.class, Class_Custom2.class};
	}
}
