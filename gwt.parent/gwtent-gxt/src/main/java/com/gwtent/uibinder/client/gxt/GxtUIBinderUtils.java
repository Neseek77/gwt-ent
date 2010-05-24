package com.gwtent.uibinder.client.gxt;

import com.extjs.gxt.ui.client.widget.Component;
import com.gwtent.client.uibinder.UIBinderUtils;
import com.gwtent.uibinder.client.DataBinder;

public class GxtUIBinderUtils extends UIBinderUtils {
	public static void enableAllEditors(DataBinder manager, boolean enabled){
		for (Object obj : manager.getAllUIObjects()){
			if (obj instanceof Component){
				((Component)obj).setEnabled(enabled);
			}
		}
	}
}
