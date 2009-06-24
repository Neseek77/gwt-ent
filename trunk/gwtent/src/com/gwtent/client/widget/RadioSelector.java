package com.gwtent.client.widget;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.utils.WebUtils;

public class RadioSelector<T extends Object> extends FlexTable {

	public RadioSelector(){
		this.setStylePrimaryName("gwtent-RadioSelector");
		
		this.setBorderWidth(0);
	}
	
	private void doValueChanged() {
		valueChangedListeners.fireValueChanged(this, getValue());
	}
	
	public T getValue(){
		for (Data data : values){
			if (data.getRadio().isChecked())
				return data.value;
		}
		
		return null;
	}
	
	public void setValue(T value){
		for (Data data : values){
			data.getRadio().setChecked(false);
		}
		
		for (Data data : values){
			if (data.getValue().equals(value))
				data.getRadio().setChecked(true);
		}
	}
	
	
	/**
	 * Set the display label of value, by default, it's value.toString()
	 * @param value
	 * @param label
	 */
	public void setLabel(T value, String label){
		for (Data data : values){
			if (data.getValue().equals(value))
				data.setLabel(label);
		}
	}
	
	
	public void addValue(T value, String label){
		Data data = new Data(groupID, value, label);
		values.add(data);
		this.setWidget(0, values.size() - 1, data.getRadio());
	}
	
	public void addValueChangedListener(ValueChangedListener<T> listener){
		valueChangedListeners.add(listener);
	}
	
	public void removeValueChangedListener(ValueChangedListener<T> listener){
		valueChangedListeners.remove(listener);
	}
	
	public void updateByEnum(Class<T> clazzOfEnum){
		T[] constants = clazzOfEnum.getEnumConstants();
		if (constants == null) {
      throw new IllegalArgumentException(
      		clazzOfEnum.getName() + " is not an enum.");
    }
		
    for (T constant : constants) {
        this.addValue(constant, constant.toString());
    }
	}
	
	private class Data {
		public Data(String groupID, T value, String label){
			radio = new RadioButton(groupID);
			radio.addClickListener(new ClickListener(){

				public void onClick(Widget sender) {
					doValueChanged();
				}});
			this.setValue(value);
			this.setLabel(label);
		}
		private final RadioButton radio;
		private T value;
		private String label;
		
		public RadioButton getRadio() {
			return radio;
		}
		public void setValue(T value) {
			this.value = value;
		}
		public T getValue() {
			return value;
		}
		public void setLabel(String label) {
			this.label = label;
			radio.setText(label);
			radio.setTitle(label);
		}
		public String getLabel() {
			return label;
		}
	}
	
	private String groupID = WebUtils.getRandomElementID();
	private List<Data> values = new ArrayList<Data>();
	private ValueChangedCollection<T> valueChangedListeners = new ValueChangedCollection<T>(); 
}