package com.gwtent.showcase.client.reflection;

import com.gwtent.htmltemplate.client.HTMLTemplate;
import com.gwtent.reflection.client.annotations.Reflect_Domain;


@HTMLTemplate("com/gwtent/showcase/public/reflection/ReflectionBasicPage.html")
public class ReflectEnumPage extends AbsReflectionPage {


	@Reflect_Domain
	public static enum Country {
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

	
	public ReflectEnumPage(String html) {
		super(html);
	}

	
	public String[] getResourceFileNames() {
		return new String[]{"ReflectEnumPage.java", "ReflectionBasicPage.html"};
	}

	public String getCaseName() {
		return "Basic Reflection Page";
	}

	@Override
	protected Class<?>[] getReflectionClasses() {
		return new Class<?>[]{Country.class};
	}
}
