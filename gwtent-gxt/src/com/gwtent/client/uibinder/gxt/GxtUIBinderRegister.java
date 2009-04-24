package com.gwtent.client.uibinder.gxt;

import com.gwtent.client.uibinder.UIBinderGWTFactory;

public class GxtUIBinderRegister {
  public static void register(){
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new TextFieldBinder.BinderMetaData());
    UIBinderGWTFactory.getUIBinderGWTFactory().getUIBinderGWTRegister().register(new GridBinder.BinderMetaData());
  }
}
