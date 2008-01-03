package com.gwtent.client.ui.model.value;

/**
 * GWT not allow create class use reflection
 * so add this interface
 * 
 * use AdvValue interface
 * 
 * @author James Luo
 * 2008-1-2 上午09:02:32
 *
 */
public interface ValueCreator {
	public AdvValue createValue();
}
