package com.gwtent.showcase.client.uibinder;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.gwtent.common.client.ObjectFactory;
import com.gwtent.showcase.client.uibinder.UserNameEditor.IUserName;
import com.gwtent.uibinder.client.AbstractUIBinder;
import com.gwtent.uibinder.client.IBinderMetaData;
import com.gwtent.uibinder.client.ModelValue;
import com.gwtent.uibinder.client.UIBinder;

public class UserNameEditorBinder extends AbstractUIBinder<UserNameEditor, IUserName> {
  
  public static class SupportedEditors implements IBinderMetaData<UserNameEditor, IUserName>{

    public Class<?>[] getSupportedEditors() {
      return new Class<?>[]{UserNameEditor.class};
    }

    public ObjectFactory<UIBinder<UserNameEditor, IUserName>> getFactory() {
      return new ObjectFactory<UIBinder<UserNameEditor, IUserName>>(){

        public UIBinder<UserNameEditor, IUserName> getObject() {
          return new UserNameEditorBinder();
        }};
    }
  }
  

	@Override
	protected void doInit(UserNameEditor widget, ModelValue<IUserName> value) {
		//This is a binder inside other binder, so we only need set the value
		widget.setUserName(value.getValue());
	}


	@Override
	protected void setValueToEditor(IUserName value, UserNameEditor widget) {
		//Normally this is the place set value to widget, but in this case, it's handled by binder system in UserNameEditor automatically
		//please see com.gwtent.uibinder.client.binder.CheckBoxBinder
	}
	
	/**
	 * In this case, we override validate function. Just redirect it.
	 */
	public Set<ConstraintViolation<Object>> validate(boolean showMessagesToUI, Class<?>... validateGroups){
		return this.getWidget().dataBinder.validate(showMessagesToUI, validateGroups);
	}
}
