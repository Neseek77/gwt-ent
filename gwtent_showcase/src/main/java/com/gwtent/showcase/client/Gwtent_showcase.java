package com.gwtent.showcase.client;

import java.util.Date;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtent.common.client.ObjectFactory;
import com.gwtent.showcase.client.uibinder.ASmartClientEditBoxBinder;
import com.gwtent.showcase.client.uibinder.MyDateBox;
import com.gwtent.showcase.client.uibinder.UserNameEditorBinder;
import com.gwtent.uibinder.client.GWTUIBinderRegister;
import com.gwtent.uibinder.client.UIBinder;
import com.gwtent.uibinder.client.UIBinderGWTFactory;
import com.gwtent.uibinder.client.binder.GWTHasValueBinder;
import com.gwtent.validate.client.GWTValidateMessageStore;
import com.gwtent.validate.client.message.ValidateMessages;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Gwtent_showcase implements EntryPoint {
	
	/**
   * The type passed into the
   * {@link com.google.gwt.sample.showcase.generator.ShowcaseGenerator}.
   */
  private static final class GeneratorInfo {
  }
	
	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//For UIBind
		GWTUIBinderRegister.register();
		
		//For custom binders
		//If the binder already there, you just need register the widget class to it.
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(MyDateBox.class, new ObjectFactory<UIBinder>(){

			public UIBinder getObject() {
				return new GWTHasValueBinder();
			}});
		
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new ASmartClientEditBoxBinder.SupportedEditors());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new UserNameEditorBinder.SupportedEditors());
		
		//For Validate Messages
		GWTValidateMessageStore.getInstance().addMessageObject(GWT.create(ValidateMessages.class), ValidateMessages.class);
		
		RootPanel.get().add((MainPageHTMLPanel)GWT.create(MainPageHTMLPanel.class));
		//RootPanel.get().add(new MainPageHTMLPanel__Template_Test());
	}

}
