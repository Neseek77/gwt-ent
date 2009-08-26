package com.gwtent.showcase.client.reflection;

import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.RichTextArea;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.annotations.Reflect_Domain;
import com.gwtent.client.template.HTMLTemplate;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.client.validate.constraints.Regular;
import com.gwtent.showcase.client.BaseShowCasePanel;


@HTMLTemplate("ReflectionBasicPage.html")
public class ReflectBasicPage extends BaseShowCasePanel {


	@Entity(name="TestReflection")
	@Table(name="Table_Test")
	@Reflect_Domain
	public static class TestReflection<T> {
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
	
	public ReflectBasicPage(String html) {
		super(html);
	}

	
	public String[] getResourceFileNames() {
		return new String[]{"ReflectBasicPage.java", "ReflectionBasicPage.html"};
	}

	public String getCaseName() {
		return "Basic Reflection Page";
	}
	
	@HTMLWidget
	protected Button btnShowReflectionInfo_Basic = new Button("Show Reflection TestReflection", new ClickHandler(){

		public void onClick(ClickEvent event) {
			memoReflectionInfo_Basic.setHTML(ReflectionUtils.getDescription(TestReflection.class).replace("\n", "<br>"));
		}});
	
	
	@HTMLWidget
	protected RichTextArea memoReflectionInfo_Basic = new RichTextArea();
}
