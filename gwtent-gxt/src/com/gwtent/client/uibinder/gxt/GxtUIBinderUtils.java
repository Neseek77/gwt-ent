package com.gwtent.client.uibinder.gxt;

import com.extjs.gxt.ui.client.widget.Component;
import com.gwtent.client.uibinder.UIBinderManager;
import com.gwtent.client.uibinder.UIBinderUtils;

public class GxtUIBinderUtils extends UIBinderUtils {
	public static void enableAllEditors(UIBinderManager manager, boolean enabled){
		for (Object obj : manager.getAllUIObjects()){
			if (obj instanceof Component){
				((Component)obj).setEnabled(enabled);
			}
		}
	}
}
