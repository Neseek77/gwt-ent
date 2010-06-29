package com.gwtent.showcase.client.uibinder;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.datepicker.client.DateBox;

/**
 * Created your version of DateBox, you can just register it to binder system
 *
 * 29/06/2010 1:25:51 PM
 */
public class MyDateBox extends DateBox {
	public MyDateBox(){
		this.setFormat(new DefaultFormat(DateTimeFormat.getFormat("dd/MM/yyyy")));
	}
}
