package com.gwtent.validate.client;

import javax.validation.Configuration;

/**
 * 
 * @author James Luo
 * 
 */
public interface GWTEntValidatorConfiguration extends Configuration<GWTEntValidatorConfiguration> {
	
	/**
	 * Add your I18N message interface here.
	 * <p>
	 * You can call it as many as your want, if a I18N key exists in more then one Messages classes, 
	 * it always using latest one. So your messages always overwrite system one if exists same I18N key. 
	 * <p>
	 * 
	 * @param msg , you message class, normally it's {@link com.google.gwt.i18n.client.Messages}, but in gwtent implement, it's only requeest this class is reflectable and contains functions has the same name with I18N keys
	 * @param messageClass, the class of your Message interface  
	 */
	public GWTEntValidatorConfiguration addI18NMessages(Object messages, Class<?> messageClass);
	
}

