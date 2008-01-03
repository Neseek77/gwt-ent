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
package com.gwtent.client.ui;

import com.google.gwt.user.client.ui.Composite;
import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.ui.editor.gwt.StringEditor;
import com.gwtent.client.ui.editorFactory.EditorRegister;
import com.gwtent.client.ui.generator.ExtAutoUIGenerator;
import com.gwtent.client.ui.generator.GwtAutoUIGenerator;
import com.gwtent.client.ui.generator.UIGenerator;
import com.gwtent.client.ui.model.value.StringValue;
import com.gwtent.client.ui.model.value.ValueRegister;
import com.gwtent.client.ui.transition.POJOToModel;
import com.gwtent.client.ui.transition.POJOToModelImpl;
import com.gwtent.client.ui.validate.ExpressionProcessorRegister;
import com.gwtent.client.ui.validate.NotNullValidateProcessor;
import com.gwtent.client.ui.validate.StringValidateProcessor;

/**
 * the fasade of ui system
 * 
 * @author James Luo
 * 2007-12-29 下午06:17:19
 *
 */
public class UIFactory {

	private UIGenerator uiGwtGenerator = null;
	private UIGenerator uiExtGenerator = null;
	private POJOToModel transition = null;
	
	private UIFactory(){
		transition = new POJOToModelImpl();
		registerExpressionProcessors();
		registerValueTypes();
		registerEditors();
	}
	
	/**
	 * Is everybody can tell me, How can I process this situation
	 * thanks
	 * Is there any way more easy?
	 */
	public void registerExpressionProcessors(){
		ExpressionProcessorRegister.getInstance().registValidator(NotNullValidateProcessor.getInstance());
		ExpressionProcessorRegister.getInstance().registValidator(StringValidateProcessor.getInstance());
	}
	
	public void registerValueTypes(){
		ValueRegister.getInstance().register("java.lang.String", new StringValue.StringValueCreator());
	}
	
	public void registerEditors(){
		EditorRegister.getInstance().register("java.lang.String", new StringEditor.StringEditorCreator());
	}
	
	public Composite gwtFactory(Object pojo, ClassType classType){
		if (uiGwtGenerator == null) uiGwtGenerator = new GwtAutoUIGenerator();
		
		return uiGwtGenerator.generator(transition.createModel(pojo, classType));
	}
	
	public Composite extFactory(Object pojo, ClassType classType){
		if (uiExtGenerator == null) uiExtGenerator = new ExtAutoUIGenerator();
		
		return uiExtGenerator.generator(transition.createModel(pojo, classType));
	}
 
	
	private static UIFactory instance = null;
	
	public static UIFactory getInstance(){
		if (instance == null) instance = new UIFactory();
		
		return instance;
	}
}
