package com.gwtent.gen.aop;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenExclusionCompositeImpl;

public class GenExclusionCompositeAOP extends GenExclusionCompositeImpl {
	private GenExclusionCompositeAOP(){
		//Can don't create reflection for AOP generated source.
		addGenExclusion(new GenExclusion(){

			public boolean exclude(JClassType classType) {
				if (classType.isInterface() != null)
					return true;
				
				return false;
			}
			
		});
	}
	
	public static final GenExclusionCompositeAOP INSTANCE = new GenExclusionCompositeAOP();
}
