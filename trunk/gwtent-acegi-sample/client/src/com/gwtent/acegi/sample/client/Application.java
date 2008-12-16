package com.gwtent.acegi.sample.client;

import java.lang.annotation.Annotation;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.gwtent.acegi.client.UserLoginRpc;


/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class Application implements EntryPoint {

	final Button btnLogin = new Button("Login");
	final Button btnLogout = new Button("Logout");
	final TextBox edtUserName = new TextBox();
	final TextBox edtPassword = new TextBox();
	final CheckBox cbRememberMe = new CheckBox("Remember Me");
	
  final Button button = new Button("Click me");
  final TextArea memMessage = new TextArea();
  
  /**
   * This is the entry point method.
   */
  public void onModuleLoad() {


    
    btnLogin.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				UserLoginRpc.Util.getInstance().login(edtUserName.getText(), edtPassword.getText(), cbRememberMe.isChecked(), new AsyncCallback<Boolean>(){

					public void onFailure(Throwable caught) {
						addMessage(caught.toString());
					}

					public void onSuccess(Boolean result) {
						if (result)
							addMessage("Login successfully");
						else
							addMessage("Login Failed");
					}
					
				});
			}
    	
    });

    
    btnLogout.addClickListener(new ClickListener(){

			public void onClick(Widget sender) {
				UserLoginRpc.Util.getInstance().logout(edtUserName.getText(), new AsyncCallback(){

					public void onFailure(Throwable caught) {
						addMessage(caught.toString());
					}

					public void onSuccess(Object result) {
						addMessage("Logout successfully");
					}
					
				});
			}
    	
    });

    // Assume that the host HTML has elements defined whose
    // IDs are "slot1", "slot2".  In a real app, you probably would not want
    // to hard-code IDs.  Instead, you could, for example, search for all 
    // elements with a particular CSS class and replace them with widgets.
    //
    RootPanel.get("btnLogin").add(btnLogin);
    RootPanel.get("btnLogout").add(btnLogout);
    RootPanel.get("edtUserName").add(edtUserName);
    RootPanel.get("edtPassword").add(edtPassword);
    RootPanel.get("cbRememberMe").add(cbRememberMe);
    
    
    RootPanel.get("Message").add(memMessage);
  }
  
  private void addMessage(String msg){
  	memMessage.setText(memMessage.getText() + "\n" + msg);
  }

}
