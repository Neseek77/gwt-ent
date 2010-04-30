package com.gwtent.showcase.client.reflection;

/**
 * 
 * @author James Luo
 *
 * 29/04/2010 12:00:47 PM
 */
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.TextArea;
import com.gwtent.client.template.HTMLWidget;
import com.gwtent.showcase.client.BaseShowCasePanel;
import com.gwtent.showcase.client.Utils;
import com.gwtent.showcase.client.reflection.ReflectBasicPage.TestReflection;

public abstract class AbsReflectionPage extends BaseShowCasePanel {

	protected abstract Class<?>[] getReflectionClasses();
	
	@HTMLWidget
	protected Button btnShowReflectionInfo_Basic = new Button("Show Reflection TestReflection", new ClickHandler(){
	
			public void onClick(ClickEvent event) {
				memoReflectionInfo_Basic.setText(Utils.getClassDescription(getReflectionClasses()));
			}});
	
	@HTMLWidget
	protected TextArea memoReflectionInfo_Basic = new TextArea();

	public AbsReflectionPage(String html) {
		super(html);
	}

}