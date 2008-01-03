package com.gwtent.client.ui.transition;

import com.gwtent.client.reflection.Reflection;
import com.gwtent.client.ui.model.Fields;
import com.gwtent.client.ui.model.value.ValueFactoryImpl;

public class POJOToModelImpl implements POJOToModel {
	
	public Fields createModel(Object pojo, Class clasz) {
		if (pojo instanceof Reflection){
			POJOToModel transition = new ReflectionToModel(ValueFactoryImpl.getInstance());
			return transition.createModel(pojo, clasz);
		}else{
			throw new TransitionException("Cann't create fields of class, dose this class implement Refection or UIFriendly interface?");
		}
	}

}
