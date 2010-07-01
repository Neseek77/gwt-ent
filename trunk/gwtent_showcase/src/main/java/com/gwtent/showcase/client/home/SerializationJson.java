package com.gwtent.showcase.client.home;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.test.json.People;
import com.gwtent.client.test.json.Person;
import com.gwtent.serialization.client.DataContractSerializer;
import com.gwtent.serialization.client.json.JsonSerializer;
import com.gwtent.showcase.client.ShowCase;
import com.gwtent.showcase.client.domain.User;
import com.gwtent.showcase.client.domain.UserFactory;
import com.gwtent.uibinder.client.UIBind;

/**
 * 
 * @author James Luo
 *
 * 30/06/2010 5:42:17 PM
 */
public class SerializationJson extends Composite implements ShowCase{

	private static SerializationJsonUiBinder uiBinder = GWT
			.create(SerializationJsonUiBinder.class);

	interface SerializationJsonUiBinder extends
			UiBinder<Widget, SerializationJson> {
	}
	
	interface DataBinder extends com.gwtent.uibinder.client.DataBinder<SerializationJson>{
		
	}
	DataBinder dataBinder = GWT.create(DataBinder.class);

	@UiField
	Button btnConvertToJSON;
	
	@UiField
	Button btnConvertFromJSON;

	public SerializationJson() {
		initWidget(uiBinder.createAndBindUi(this));
		
		Person p = new Person();
		p.setName("James");
		p.setAge(120);
		
		Person p1 = new Person();
		p1.setName("YourName");
		p1.setAge(20);
		
		people = new People();
		people.add(p);
		people.add(p1);
		
		dataBinder.bindAll(this);
		
		
	}

	@UiHandler("btnConvertToJSON")
	void onbtnConvertToJSONClick(ClickEvent e) {
		Set<ConstraintViolation<Object>> result = dataBinder.validate(true);
		if (result != null && result.size() > 0){
			edtJson.setText("Please correct input first.\n");
			
			for (ConstraintViolation<Object> r : result)
				edtJson.setText(edtJson.getText() + "\n" + r);
		}else{
			//Convert to JSON
			DataContractSerializer serializer = new JsonSerializer();
			edtJson.setText(serializer.serializeObject(people));
		}
	}
	
	@UiHandler("btnConvertFromJSON")
	void onbtnConvertFromJSONClick(ClickEvent e) {
		try {
			DataContractSerializer serializer = new JsonSerializer();
			people = serializer.deserializeObject(edtJson.getText(), People.class);
			dataBinder.modelChanged();
		} catch (Exception e1) {
			Window.alert(e1.getMessage());
		}
	}
	
	@UiField
	@UIBind(path = "getSecPerson().name")
	TextBox edtName2;
	
	@UiField
	@UIBind(path = "getFirstPerson().name")
	TextBox edtName;
	
	@UiField
	TextArea edtJson;
	
	People people;
	
	Person getFirstPerson(){
		return people.get(0);
	}
	
	Person getSecPerson(){
		return people.get(1);
	}
	

	
	//----------ShowCase-----------------
	@Override
	public String getCaseName() {
		return "Serialization JSON";
	}

	@Override
	public String[] getResourceFileNames() {
		return new String[]{"SerializationJson.java", "People.java", "Person.java"};
	}

	@Override
	public Widget getShowCaseWidget() {
		return this;
	} 

}
