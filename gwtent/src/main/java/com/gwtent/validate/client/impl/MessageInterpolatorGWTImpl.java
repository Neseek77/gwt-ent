package com.gwtent.validate.client.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.MessageInterpolator;

import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.Method;
import com.gwtent.reflection.client.Parameter;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.validate.client.GWTValidateMessageStore;

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
	private List<ClassType> messagesClasses = null;
	
	private void updateMessageTypes(){
		messageTypes = GWTValidateMessageStore.getInstance().getMessageStores();
		messagesClasses = GWTValidateMessageStore.getInstance().getMessagesClasses();
	}
	
	private String doInterpolate(String messageTemplate, Context context) {
		messageTemplate = messageTemplate.trim();
		if (messageTemplate.startsWith("{") && messageTemplate.endsWith("}")){
			updateMessageTypes();
			
			String methodName = messageTemplate.substring(1, messageTemplate.length() - 1).trim();
			methodName = methodName.replace(".", "_");
			
			for (int i = messagesClasses.size() - 1; i >= 0; i--){
				ClassType type = messagesClasses.get(i);
			
				Method method = ReflectionUtils.findMethodByName(type, methodName);
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
			
			StringBuilder sb = new StringBuilder();
			sb.append("Can not found message method from message class list. please ensure function [").append(messageTemplate).append("(...)] exists in the following classes: ");
			for (ClassType type : messageTypes.keySet())
				sb.append(type.getName());
			sb.append(". You can using [GWTValidateMessageStore.getInstance().addMessageObject] setup your message class.");
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

	//TODO James - JSR303 - Locale
//	public String interpolate(String messageTemplate, Context context,
//			Locale locale) {
//		//TODO finish this
//		throw new RuntimeException("Not start yet.");
//	}

}
