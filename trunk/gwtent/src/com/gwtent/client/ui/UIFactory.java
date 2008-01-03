package com.gwtent.client.ui;

import com.google.gwt.user.client.ui.Composite;
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
	
	public Composite gwtFactory(Object pojo, Class clasz){
		if (uiGwtGenerator == null) uiGwtGenerator = new GwtAutoUIGenerator();
		
		return uiGwtGenerator.generator(transition.createModel(pojo, clasz));
	}
	
	public Composite extFactory(Object pojo, Class clasz){
		if (uiExtGenerator == null) uiExtGenerator = new ExtAutoUIGenerator();
		
		return uiExtGenerator.generator(transition.createModel(pojo, clasz));
	}
 
	
	private static UIFactory instance = null;
	
	public static UIFactory getInstance(){
		if (instance == null) instance = new UIFactory();
		
		return instance;
	}
}
