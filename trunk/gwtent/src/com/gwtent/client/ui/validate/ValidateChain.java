package com.gwtent.client.ui.validate;

import java.util.Iterator;


public interface ValidateChain extends Validate {
	public void addValidate(Validate validate);
	
	public Iterator iterator();
}
