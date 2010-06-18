package com.gwtent.client.test.uibinder;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.uibinder.client.DataBinder;
import com.gwtent.uibinder.client.UIBind;

/**
 * 
 * @author James Luo
 *
 * 15/04/2010 10:06:18 AM
 */
public class GWTUiBinder extends Composite {

	private static GWTUiBinderUiBinder uiBinder = GWT
			.create(GWTUiBinderUiBinder.class);

	interface GWTUiBinderUiBinder extends UiBinder<Widget, GWTUiBinder> {
	}
	
	GWTUiBinderDataBinder dataBinder = GWT.create(GWTUiBinderDataBinder.class);
	interface GWTUiBinderDataBinder extends DataBinder<GWTUiBinder>{
		
	}
	
	//Test create twice
	private static GWTUiBinderDataBinder dataBinder1 = GWT.create(GWTUiBinderDataBinder.class);

	@UiField
	Button button;
	
	@UiField
	@UIBind(path="theName")
	TextBox testTextBox;
	
	@UiField
	@UIBind(path="model.firstName")
	TextBox testTextBoxModelReturnsNull;

	public GWTUiBinder() {
		initWidget(uiBinder.createAndBindUi(this));
		dataBinder.bindAll(this);
		
		dataBinder.modelChanged();
	}

	@UiHandler("button")
	void onClick(ClickEvent e) {
		Window.alert("Hello!");
	}
	
	String theName = "abc";

	TestModel model = null;
}
