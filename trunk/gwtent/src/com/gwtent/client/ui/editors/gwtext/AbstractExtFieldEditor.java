/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.client.ui.editors.gwtext;

import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.editor.AbstractEditor;
import com.gwtent.client.ui.editor.EditorEventAware;
import com.gwtent.client.ui.model.Value;
import com.gwtext.client.core.EventObject;
import com.gwtext.client.widgets.form.Field;
import com.gwtext.client.widgets.form.Form;
import com.gwtext.client.widgets.form.FormConfig;
import com.gwtext.client.widgets.form.event.FieldListener;

/**
 * Abstract editor that use ext field editors
 * @author James Luo
 * 2008-1-5
 *
 */
public abstract class AbstractExtFieldEditor extends AbstractEditor implements EditorEventAware{
	
	private Form form;

	public AbstractExtFieldEditor(Value value) {
		super(value);
		
		form = new Form(new FormConfig(){
			{
				setWidth(300);
				setLabelWidth(0);
				setHideLabels(true);
			}
		});
	}

	public Widget getWidget() {
		return form;
	}
	
	public Form getForm() {
		return form;
	}
	
	public void finishAddEditors(){
//		form.setWidth(800);
//		form.render();
	}
	
	public class FieldListenerImpl implements FieldListener{

		public void onBlur(Field field) {
			//doValidate();
		}

		public void onChange(Field field, Object newVal, Object oldVal) {
			doValidate();
		}

		public void onFocus(Field field) {
			
		}

		public void onInvalid(Field field, String msg) {
			
		}

		public void onSpecialKey(Field field, EventObject e) {
			if (e.getKey() == EventObject.ENTER)
				doValidate();
		}

		public void onValid(Field field) {
			
			
		}
		
	}





}
