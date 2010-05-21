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


package com.gwtent.ui.client;

import com.google.gwt.user.client.ui.Widget;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.impl.ClassTypeImpl;
import com.gwtent.ui.client.generator.ExtAutoUIGenerator;
import com.gwtent.ui.client.generator.GwtAutoUIGenerator;
import com.gwtent.ui.client.generator.UIGenerator;
import com.gwtent.ui.client.transition.POJOToModel;
import com.gwtent.ui.client.transition.POJOToModelImpl;
import com.gwtent.ui.client.validate.ExpressionProcessorRegister;
import com.gwtent.ui.client.validate.NotNullValidateProcessor;
import com.gwtent.ui.client.validate.StringValidateProcessor;

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
		registerValueTypesAndEditor();
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
	
	public void registerValueTypesAndEditor(){
		
	}
	
	public Widget gwtFactory(Object pojo, ClassType classType){
		if (uiGwtGenerator == null) uiGwtGenerator = new GwtAutoUIGenerator();
		
		return uiGwtGenerator.generator(transition.createModel(pojo, classType));
	}
	
	public Widget extFactory(Object pojo, ClassType classType){
		if (uiExtGenerator == null) uiExtGenerator = new ExtAutoUIGenerator();
		
		return uiExtGenerator.generator(transition.createModel(pojo, classType));
	}
 
	
	private static UIFactory instance = null;
	
	public static UIFactory getInstance(){
		if (instance == null) instance = new UIFactory();
		
		return instance;
	}
}
