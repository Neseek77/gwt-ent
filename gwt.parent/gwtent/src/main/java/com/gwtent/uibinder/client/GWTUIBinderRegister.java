package com.gwtent.uibinder.client;

import com.gwtent.uibinder.client.binder.CheckBoxBinder;
import com.gwtent.uibinder.client.binder.EnumListBoxBinder;
import com.gwtent.uibinder.client.binder.GWTHasValueBinder;
import com.gwtent.uibinder.client.binder.LabelBinder;
import com.gwtent.uibinder.client.binder.ListBoxBinder;
import com.gwtent.uibinder.client.binder.RadioSelectorBinder;
import com.gwtent.uibinder.client.binder.RichTextAreaBinder;

public class GWTUIBinderRegister {
	public static void register(){
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new GWTHasValueBinder.SupportedEditors());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new LabelBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new CheckBoxBinder.SupportedEditors());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new RichTextAreaBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new RadioSelectorBinder.BinderMetaData());		
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new ListBoxBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new EnumListBoxBinder.BinderMetaData());

	}
}
