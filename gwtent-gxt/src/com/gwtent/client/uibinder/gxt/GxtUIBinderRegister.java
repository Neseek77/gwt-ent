package com.gwtent.client.uibinder.gxt;

import com.gwtent.client.uibinder.UIBinderGWTFactory;

public class GxtUIBinderRegister {
  public static void register(){
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new FieldBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new GridBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new EnumComboBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new ComboBoxBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new SimpleComboBoxBinder.BinderMetaData());
  }
}
