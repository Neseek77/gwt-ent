/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.client.ui.generator.layout;

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
import com.gwtent.client.ui.generator.GeneratorCSSName;
import com.gwtent.client.ui.model.Action;
import com.gwtent.client.ui.model.ActionCallBack;
import com.gwtent.client.ui.model.Domain;
import com.gwtent.client.ui.model.Field;
import com.gwtent.client.ui.validate.ValidateCallBack;
import com.gwtent.client.ui.validate.ValidateException;

public class VerticalLayout extends AbstractLayoutCreator {


	private EditorFactory editorFactory;
	
	/**
	 * main panel, 3 rows, 1 col
	 *               Title                      waitting...(if necessary)
	 *               Editors
	 *               Button area
	 */
	private Grid mainPanel = new Grid(3, 1);
	private Grid titlePanel = new Grid(1, 2);

	private Domain domain;
	
	private boolean useColon = true;
	
	public Widget doGenerateLayout(Domain domain, EditorFactory editorFactory) {
		this.domain = domain;
		this.editorFactory = editorFactory;
		
		mainPanel.setWidth("100%");
		
		mainPanel.setCellSpacing(30);
		mainPanel.setWidget(0, 0, setupTitlePanel());
		mainPanel.setWidget(1, 0, createEditors());
		mainPanel.getCellFormatter().setHorizontalAlignment(2, 0, HasHorizontalAlignment.ALIGN_CENTER);
		mainPanel.setWidget(2, 0, setupButtons());
		
		return mainPanel;
	}
	
	protected Widget setupTitlePanel(){
		titlePanel.getCellFormatter().setWidth(0, 0, "100%");
		HTML caption = new HTML(this.domain.getCaption());
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
		
		Iterator iterator = domain.actionIterator();
		while (iterator.hasNext()) {
			final Action element = (Action) iterator.next();
			
			result.add(new Button(element.getCaption(), new ClickListener(){

				public void onClick(Widget sender) {
					element.doAction(domain.getInstnace());
					element.doAsyncAction(domain.getInstnace(), new ActionCallBackImpl());
				}
			}));
		}
		
		return result;
	}
	
	private int getFieldCount(){
		int result = 0;
		Iterator iterator = domain.fieldIterator();
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
//		HorizontalPanel container = new HorizontalPanel();
		Grid panel = new Grid(getFieldCount(), 3);
		
		panel.setWidth("100%");
		
		panel.getColumnFormatter().setWidth(0, "30%");
		panel.getColumnFormatter().setWidth(1, "60%");
		panel.getColumnFormatter().setWidth(2, "10%");
		
		panel.setCellSpacing(10);
		
		int row = 0;
		Field field = null;
		Iterator iterator = domain.fieldIterator();
		while (iterator.hasNext()){
			field = (Field)iterator.next();
			
			panel.getCellFormatter().setVerticalAlignment(row, 0, HasVerticalAlignment.ALIGN_TOP);
			panel.getCellFormatter().setHorizontalAlignment(row, 0, HasHorizontalAlignment.ALIGN_RIGHT);
			panel.getCellFormatter().setVerticalAlignment(row, 1, HasVerticalAlignment.ALIGN_TOP);
			panel.getCellFormatter().setVerticalAlignment(row, 2, HasVerticalAlignment.ALIGN_TOP);
			
			panel.setWidget(row, 0, new HTML(field.getCaption()));
			panel.setWidget(row, 1, createEditor(field));
			panel.setWidget(row, 2, createRequire());
			
			
			row++;
		}
		
		//container.add(panel);
		//return container;
		return panel;
	}
	
	protected Widget createEditor(Field field){
		VerticalPanel vp = new VerticalPanel();
		vp.setSpacing(0);
		vp.setWidth("100%");
		
		Editor editor = editorFactory.getEditor(field.getValue());
		this.addEditor(editor);
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

	public boolean isUseColon() {
		return useColon;
	}

	public void setUseColon(boolean useColon) {
		this.useColon = useColon;
	}
	
	
	public class ActionCallBackImpl implements ActionCallBack{

		public void actionCallDone() {
			//hide waitting
			
		}
		
	}


}
