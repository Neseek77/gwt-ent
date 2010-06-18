package com.gwtent.showcase.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;
import com.gwtent.uibinder.client.GWTUIBinderRegister;
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
		//For Validate Messages
		GWTValidateMessageStore.getInstance().addMessageObject(GWT.create(ValidateMessages.class), ValidateMessages.class);
		
		RootPanel.get().add((MainPageHTMLPanel)GWT.create(MainPageHTMLPanel.class));
		//RootPanel.get().add(new MainPageHTMLPanel__Template_Test());
	}

}
