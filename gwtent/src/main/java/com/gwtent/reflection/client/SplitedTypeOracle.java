

package com.gwtent.reflection.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface SplitedTypeOracle {
//	public static SplitedTypeOracle Instance = (SplitedTypeOracle) GWT
//			.create(SplitedTypeOracle.class);

	public void getClassType(String name, AsyncCallback<ClassType> callback);

}