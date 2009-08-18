package com.gwtent.client.uibinder;

import com.gwtent.client.uibinder.binder.DateBoxBinder;
import com.gwtent.client.uibinder.binder.EnumListBoxBinder;
import com.gwtent.client.uibinder.binder.LabelBinder;
import com.gwtent.client.uibinder.binder.ListBoxBinder;
import com.gwtent.client.uibinder.binder.RadioSelectorBinder;
import com.gwtent.client.uibinder.binder.RichTextAreaBinder;
import com.gwtent.client.uibinder.binder.CheckBoxBinder;
import com.gwtent.client.uibinder.binder.TextBoxBinder;

public class GWTUIBinderRegister {
	public static void register(){
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new LabelBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new CheckBoxBinder.SupportedEditors());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new RichTextAreaBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new RadioSelectorBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new TextBoxBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new DateBoxBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new ListBoxBinder.BinderMetaData());
		UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new EnumListBoxBinder.BinderMetaData());

	}
}
