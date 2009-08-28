package com.gwtent.showcase.client.reflection;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.gwtent.client.reflection.Reflectable;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.annotations.Reflect_Domain;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.BaseShowCasePanel;
import com.gwtent.showcase.client.Utils;


@HTMLTemplate("ReflectionBasicPage.html")
public class ReflectDomainPage extends BaseShowCasePanel {

	/**
	 * This class will be generated reflection information automatically
	 *
	 */
	public static class ClassRefByAnnotation{
		private String name;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	
	/**
	 * This annotation will create reflection information automatically
	 * cause this annotation used by Class_Domain
	 *
	 */
	@Retention(RetentionPolicy.RUNTIME)
	@Target(ElementType.TYPE)
	public static @interface ClassDomainRef {
		public String value();
		public Class<?> refTo();
	}
	
	/**
	 * Even this is a normal pojo class(without any @Reflectable or implement Reflection interface
	 * cause this class used by Class_Domain, and Class_Domain has "@Reflect_Domain" annotation,
	 * this class will generate reflection information as well. 
	 *
	 */
	public static class Class_Doamin_Ref{
		private String name;

		public void setName(String name) {
			this.name = name;
		}

		public String getName() {
			return name;
		}
	}
	
	@Reflect_Domain
	@ClassDomainRef(refTo = ClassRefByAnnotation.class, value = "hello")
	public static class Class_Domain{
		//java.util.Date will be generated reflection information as well
		private Date date;
		
		private Class_Doamin_Ref ref;

		public void setDate(Date date) {
			this.date = date;
		}

		public Date getDate() {
			return date;
		}

		public void setRef(Class_Doamin_Ref ref) {
			this.ref = ref;
		}

		public Class_Doamin_Ref getRef() {
			return ref;
		}
	}
	
	public ReflectDomainPage(String html) {
		super(html);
	}

	
	public String[] getResourceFileNames() {
		return new String[]{"ReflectDomainPage.java", "ReflectionBasicPage.html"};
	}

	public String getCaseName() {
		return "Reflection Domain";
	}
	
	@HTMLWidget
	protected Button btnShowReflectionInfo_Basic = new Button("Show Reflection Information", new ClickHandler(){

		public void onClick(ClickEvent event) {
			memoReflectionInfo_Basic.setHTML(Utils.getClassDescription(Class_Domain.class, Class_Doamin_Ref.class, ClassDomainRef.class, ClassRefByAnnotation.class));
		}});
	
	
	@HTMLWidget
	protected RichTextArea memoReflectionInfo_Basic = new RichTextArea();
}
