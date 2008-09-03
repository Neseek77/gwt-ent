package com.gwtent.client.ui.ext;

import com.gwtent.client.ui.ValueEditorRegister;
import com.gwtent.client.ui.editors.StringValue;
import com.gwtent.client.ui.editors.gwtext.DateExtEditor;
import com.gwtent.client.ui.editors.gwtext.StringExtEditor;
import com.gwtent.client.ui.model.value.ValueDefaultImpl;

public class Register {
  public static void regist(){
    ValueEditorRegister.getInstance().register("java.lang.String", new StringValue.StringValueCreator(), new StringExtEditor.StringEditorCreator());
    ValueEditorRegister.getInstance().register("java.util.Date", new ValueDefaultImpl.ValueDefaultImplCreator(), new DateExtEditor.DateEditorCreator());
  }
}
