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
 * 23/06/2010 4:44:38 PM
 */
public class ValidateByCode extends Composite implements ShowCase {

	private static ValidateByCodeUiBinder uiBinder = GWT
			.create(ValidateByCodeUiBinder.class);

	interface ValidateByCodeUiBinder extends UiBinder<Widget, ValidateByCode> {
	}

	@UiField
	Button button;

	public ValidateByCode() {
		initWidget(uiBinder.createAndBindUi(this));
		button.setText("Validate");
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}

	@Override
	public String getCaseName() {
		return "Validate By Code";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[]{"ValidateByCode.java"};
	}
	
	public Widget getShowCaseWidget(){
		return this;
	}

}
