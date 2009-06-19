package com.gwtent.client.validate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gwtent.client.reflection.ClassType;
import com.gwtent.client.reflection.ReflectionUtils;
import com.gwtent.client.reflection.TypeOracle;

public class GWTValidateMessageStore {
	
	private final Map<ClassType, Object> messageStores = new HashMap<ClassType, Object>();
	private boolean messageStoreChanged;
	
	private GWTValidateMessageStore(){
		
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
	public void addMessageObject(Object messageStore, Class<?> clazz){
		ReflectionUtils.checkReflection(clazz);
		
		setMessageStoreChanged(true);
		messageStores.put(TypeOracle.Instance.getClassType(clazz), messageStore);
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
	
}
