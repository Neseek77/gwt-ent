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
package com.gwtent.client.ui.generator;

import java.util.Iterator;

import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.HasVerticalAlignment;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.client.ui.editor.Editor;
import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.model.Field;
import com.gwtent.client.ui.model.Fields;
import com.gwtent.client.ui.validate.ValidateCallBack;
import com.gwtent.client.ui.validate.ValidateException;

public class CompositeVerticalLayout extends com.google.gwt.user.client.ui.Composite {


	private EditorFactory editorFactory;
	
	/**
	 * main panel, 3 rows, 1 col
	 *               Title                      waitting...(if necessary)
	 *               Editors
	 *               Button area
	 */
	private Grid mainPanel = new Grid(3, 1);
	private Grid titlePanel = new Grid(1, 2);

	private Fields fields;
	
	public CompositeVerticalLayout(Fields fields, EditorFactory editorFactory) {
		this.fields = fields;
		this.editorFactory = editorFactory;
		
		mainPanel.setWidth("100%");
		
		mainPanel.setCellSpacing(30);
		mainPanel.setWidget(0, 0, setupTitlePanel());
		mainPanel.setWidget(1, 0, createEditors());
		mainPanel.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setWidget(2, 0, setupButtons());
		
		initWidget(mainPanel);
	}
	
	protected Widget setupTitlePanel(){
		titlePanel.getCellFormatter().setWidth(0, 0, "100%");
		HTML caption = new HTML(this.fields.getCaption());
		caption.setStylePrimaryName(GeneratorCSSName.CSS_TITLE);
		
		titlePanel.getCellFormatter().setHorizontalAlignment(0, 0, HasHorizontalAlignment.ALIGN_CENTER);
		titlePanel.setWidget(0, 0, caption);
		titlePanel.getCellFormatter().setStylePrimaryName(0, 0, GeneratorCSSName.CSS_WAITE);
		titlePanel.setWidth("100%");
		
		return titlePanel;
	}
	
	protected Widget setupButtons(){
		HorizontalPanel result = new HorizontalPanel();
		result.setSpacing(20);
		
		result.add(new Button("Apply", new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				
			}
			
		}));
		
		result.add(new Button("Cancel", new ClickListener(){

			public void onClick(Widget sender) {
				// TODO Auto-generated method stub
				
			}
			
		}));
		
		return result;
	}
	
	private int getFieldCount(){
		int result = 0;
		Iterator iterator = fields.iterator();
		while (iterator.hasNext()){
			iterator.next();
			result++;	
		}
		return result;
	}
	
	/**
	 * ------------------------------------------------------------
	 * |  Caption  |  Editor                            |Require  |
	 * |           |------------------------------------|         |
	 * |           |  Descript                          |         |
	 * |           |  Validate Msg                      |         |
	 * |           |------------------------------------|         | 
	 * ------------------------------------------------------------
	 * 
	 */
	protected Widget createEditors(){
		Grid panel = new Grid(getFieldCount(), 3);
		
		panel.setWidth("100%");
		
		panel.getColumnFormatter().setWidth(0, "30%");
		panel.getColumnFormatter().setWidth(1, "60%");
		panel.getColumnFormatter().setWidth(2, "10%");
		
		int row = 0;
		Field field = null;
		Iterator iterator = fields.iterator();
		while (iterator.hasNext()){
			field = (Field)iterator.next();
			
			panel.setWidget(row, 0, new HTML(field.getCaption()));
			panel.setWidget(row, 1, createEditor(field));
			panel.setWidget(row, 2, createRequire());
			
			panel.getCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
			panel.getCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_TOP);
			panel.getCellFormatter().setVerticalAlignment(row, 2, HasVerticalAlignment.ALIGN_TOP);
			
			row++;
		}
		return panel;
	}
	
	protected Widget createEditor(Field field){
		VerticalPanel vp = new VerticalPanel();
		vp.setWidth("100%");
		
		Editor editor = editorFactory.getEditor(field.getValue());
		vp.add(editor.getWidget());
		vp.add(createDesc(field));
		vp.add(createValidate(field, editor));
		
		return vp;
	}
	
	
	protected Widget createDesc(Field field){
		Widget result = new HTML(field.getDesc());
		result.setStylePrimaryName(GeneratorCSSName.CSS_DESC);
		return result;
	}
	
	protected Widget createValidate(Field field, Editor editor){
		final HTML result = new HTML("");
		result.setStylePrimaryName(GeneratorCSSName.CSS_VALIDATE);
		result.setVisible(false);
		
		editor.setValidateCallBack(new ValidateCallBack(){

			public void finishValidate(ValidateException exception) {
				if (exception != null){
					result.setVisible(true);
					result.setHTML(exception.getMessage());
				}else{
					result.setHTML("");
					result.setVisible(false);
				}
			}
			
		});
		
		return result;
	}
	
	
	protected Widget createRequire(){
		Widget result = new HTML("*");
		result.setStyleName(GeneratorCSSName.CSS_REQUIRE);
		return result;
	}

	public EditorFactory getEditorFactory() {
		return editorFactory;
	}


}
