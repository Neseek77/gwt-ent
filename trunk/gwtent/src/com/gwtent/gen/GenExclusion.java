package com.gwtent.gen;

import com.google.gwt.core.ext.typeinfo.JClassType;

public interface GenExclusion {
	/**
	 * Used for Generator system
	 * Sometimes, You don't want generate all stuff which meet the conditions
	 * 
	 * for example
	 * AOP just apply to Class, not interface, so we can implement a new GenExclustion
	 * tell generator system don't generate source code for interface
	 * 
	 * @param classType
	 * @return
	 */
	boolean exclude(JClassType classType);
}
