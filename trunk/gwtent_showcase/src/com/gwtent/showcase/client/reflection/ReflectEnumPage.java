package com.gwtent.showcase.client.reflection;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.annotations.Reflect_Domain;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.BaseShowCasePanel;


@HTMLTemplate("ReflectionBasicPage.html")
public class ReflectEnumPage extends BaseShowCasePanel {


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
	
	@HTMLWidget
	protected Button btnShowReflectionInfo_Basic = new Button("Show Reflection TestReflection", new ClickHandler(){

		public void onClick(ClickEvent event) {
			memoReflectionInfo_Basic.setHTML(ReflectionUtils.getDescription(Country.class).replace("\n", "<br>"));
		}});
	
	
	@HTMLWidget
	protected RichTextArea memoReflectionInfo_Basic = new RichTextArea();
}
