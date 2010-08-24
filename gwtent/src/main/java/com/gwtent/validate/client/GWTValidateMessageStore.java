package com.gwtent.validate.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gwt.core.client.GWT;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.reflection.client.ReflectionUtils;
import com.gwtent.reflection.client.TypeOracle;
import com.gwtent.validate.client.message.ValidationMessages;

public class GWTValidateMessageStore {
	
	private final List<ClassType> messagesClasses = new ArrayList<ClassType>();
	private final Map<ClassType, Object> messageStores = new HashMap<ClassType, Object>();
	private boolean messageStoreChanged;
	
	private GWTValidateMessageStore(){
		this.addMessageObject(GWT.create(ValidationMessages.class), ValidationMessages.class);
	}
	
	private static GWTValidateMessageStore messageStore = new GWTValidateMessageStore();
	
	public static GWTValidateMessageStore getInstance(){
		return messageStore;
	}
	
	/**
	 * Normally the messageStore should a GWT message interface with Reflection enabled
	 * But GWT implement just supposed an object contain some functions
	 * which match the message_id.
	 * 
	 * @param messageStore
	 */
	public void addMessageObject(Object messageStore, Class<?> messageClass){
		assert messageStore != null;
		
		Class<?> clazz = messageClass;
		
		if (messagesClasses.indexOf(clazz) >= 0)
			return;
		
		ReflectionUtils.checkReflection(clazz);
		
		setMessageStoreChanged(true);
		
		ClassType type = TypeOracle.Instance.getClassType(clazz);
		messagesClasses.add(type);
		messageStores.put(type, messageStore);
	}
	
//	public void addMessageObject(Object messageStore){
//		addMessageObject(messageStore, messageStore.getClass());
//	}
	
	/**
	 * For inner use only
	 * @return
	 */
	public Map<ClassType, Object> getMessageStores(){
		return messageStores;
	}

	public void setMessageStoreChanged(boolean messageStoreChanged) {
		this.messageStoreChanged = messageStoreChanged;
	}

	public boolean isMessageStoreChanged() {
		return messageStoreChanged;
	}
	
	public List<ClassType> getMessagesClasses(){
		return this.messagesClasses;
	}
}
