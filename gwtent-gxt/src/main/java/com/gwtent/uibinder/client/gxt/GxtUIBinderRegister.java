package com.gwtent.uibinder.client.gxt;

import com.gwtent.uibinder.client.UIBinderGWTFactory;

public class GxtUIBinderRegister {
  public static void register(){
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new FieldBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new GridBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new EnumComboBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new ComboBoxBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new SimpleComboBoxBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new CheckBoxBinder.BinderMetaData());
  }
}
