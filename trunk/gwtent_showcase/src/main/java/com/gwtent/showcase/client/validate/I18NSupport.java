package com.gwtent.showcase.client.validate;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.showcase.client.ShowCase;

/**
 * 
 * @author James Luo
 *
 * 29/06/2010 9:25:54 PM
 */
public class I18NSupport extends Composite implements ShowCase{

	private static I18NSupportUiBinder uiBinder = GWT
			.create(I18NSupportUiBinder.class);

	interface I18NSupportUiBinder extends UiBinder<Widget, I18NSupport> {
	}


	public I18NSupport() {
		initWidget(uiBinder.createAndBindUi(this));
	}


	@Override
	public String getCaseName() {
		return "I18N Support for Validation";
	}


	@Override
	public String[] getResourceFileNames() {
		return new String[] {"I18NSupport.java"};
	}


	@Override
	public Widget getShowCaseWidget() {
		return this;
	}


}
