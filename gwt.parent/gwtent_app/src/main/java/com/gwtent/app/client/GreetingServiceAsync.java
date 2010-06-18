package com.gwtent.app.client;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/*
 * * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("greet")
public interface GreetingServiceAsync {
	void greetServer(String name, AsyncCallback<String> callback);
}
