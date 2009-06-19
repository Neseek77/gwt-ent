package com.gwtent.client.validate.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.MessageInterpolator;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.Method;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;
import com.gwtent.client.uibinder.Parameter;
import com.gwtent.client.validate.GWTValidateMessageStore;

public class MessageInterpolatorGWTImpl implements MessageInterpolator {

	/**
	 * {@inheritDoc}
	 * </p>
	 * GWT message interpolator not support Locale parameter, please using \"public String interpolate(String messageTemplate, Context context)\", the Locale is the same with you current GWT locale.
	 */
	public String interpolate(String messageTemplate, Context context) {
		return doInterpolate(messageTemplate, context);
	}

//	public String interpolate(String messageTemplate, Context context,
//			Locale locale) {
//		throw new RuntimeException("GWT message interpolator not support Locale parameter, please using \"public String interpolate(String messageTemplate, Context context)\", the Locale is the same with you current GWT locale.");
//	}
	
	private Map<ClassType, Object> messageTypes = null;
	
	private void updateMessageTypes(){
		messageTypes = GWTValidateMessageStore.getInstance().getMessageStores();
	}
	
	private String doInterpolate(String messageTemplate, Context context) {
		messageTemplate = messageTemplate.trim();
		if (messageTemplate.startsWith("{") && messageTemplate.endsWith("}")){
			updateMessageTypes();
			
			messageTemplate = messageTemplate.substring(1, messageTemplate.length() - 1).trim();
			
			for (ClassType type : messageTypes.keySet()){
				Method method = ReflectionUtils.findMethodByName(type, messageTemplate);
				if (method != null){
					Object instance = messageTypes.get(type);
					
					List<Object> args = new ArrayList<Object>();
					for (Parameter param : method.getParameters()){
						Object value = context.getConstraintDescriptor().getAttributes().get(param.getName());
						args.add(value.toString());
					}
					
					if (args.size() > 0)
						return method.invoke(instance, args.toArray()).toString();
					else
						return method.invoke(instance).toString();
				}
			}
			
			if (messageTypes.keySet().size() <= 0)
				throw new RuntimeException("Please using [GWTValidateMessageStore.getInstance().addMessageObject] setup your message class first.");
			
			StringBuilder sb = new StringBuilder();
			sb.append("Can not found message method from message class list. please ensure function [").append(messageTemplate).append("(...)] exists in the following classes: ");
			for (ClassType type : messageTypes.keySet())
				sb.append(type.getName());
			throw new RuntimeException(sb.toString());
			
		}else{
			return createMessageDirect(messageTemplate, context);
		}
	}

	private String createMessageDirect(String messageTemplate, Context context) {
		if (messageTemplate.indexOf("{") > 0 && messageTemplate.indexOf("}") > 0){
			for (String key : context.getConstraintDescriptor().getAttributes().keySet()){
				String text = "{" + key + "}";
				if (messageTemplate.indexOf(text) > 0){
					messageTemplate = messageTemplate.replaceAll(text, context.getConstraintDescriptor().getAttributes().get(key).toString());
				}
			}
		}
		
		return messageTemplate;
	}

}
