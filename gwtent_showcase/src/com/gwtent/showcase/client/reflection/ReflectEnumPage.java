package com.gwtent.showcase.client.reflection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.google.gwt.user.client.ui.TextArea;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.annotations.Reflect_Domain;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.BaseShowCasePanel;
import com.gwtent.showcase.client.Utils;


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
