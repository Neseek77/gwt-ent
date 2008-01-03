package com.gwtent.client.ui.model.impl;

import com.gwtent.client.ui.model.Field;
import com.gwtent.client.ui.model.Value;
import com.gwtent.client.ui.validate.Validate;
import com.gwtent.client.ui.validate.ValidateException;

/**
 * interface Field default adapter
 * @author James Luo
 * 2007-12-21 下午02:01:52
 *
 */
public class FieldImpl implements Field {
	
	private String caption;
	private boolean require;
	private String desc;
	private Value value;
	
	public FieldImpl(){
		
	}
	
	public FieldImpl(String caption, boolean require, String desc){
		this.caption = caption;
		this.require = require;
		this.desc = desc;
	}
	
	public FieldImpl(String caption, boolean require, String desc, Value value){
		this(caption, require, desc);
		this.value = value;
	}
	
	public String getCaption() {
		return caption;
	}

	public boolean getRequire() {
		return require;
	}


	public String getDesc() {
		return desc;
	}

	public Value getValue() {
		return value;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setRequire(boolean require) {
		this.require = require;
	}

	public void setValue(Value value) {
		this.value = value;
	}

}
