package com.gwtent.client.uibinder;

import com.gwtent.client.uibinder.binder.LabelBinder;
import com.gwtent.client.uibinder.binder.RichTextAreaBinder;
import com.gwtent.client.uibinder.binder.TextBoxBinder;

public class GWTUIBinderRegister {
	public static void register(){
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new LabelBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new TextBoxBinder.SupportedEditors());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new RichTextAreaBinder.BinderMetaData());
	}
}
