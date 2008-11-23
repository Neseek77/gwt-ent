package com.gwtent.gen.aop;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenExclusionCompositeImpl;
import com.gwtent.gen.GenUtils;

public class GenExclusionCompositeAOP extends GenExclusionCompositeImpl {
	private final AspectCollector aspectCollector;
	
	private GenExclusionCompositeAOP(AspectCollector aspectCollector){
		this.aspectCollector = aspectCollector;
		
		addGenExclusion(new GenExclusion(){

			public boolean exclude(JClassType classType) {
				if (classType.isInterface() != null)
					return true;
				
				//For now, comment these, next time we need found a way to AOP without Aspectable interface.
//				if (! GenExclusionCompositeAOP.this.aspectCollector.getPointcut().getClassFilter().matches(GenUtils.GWTTypeToClass(classType)))
//					return true;
				
				return false;
			}
			
		});
	}
	
	private static GenExclusionCompositeAOP INSTANCE = null;
	
	public static GenExclusionCompositeAOP getInstance(AspectCollector aspectCollector){
		if (INSTANCE == null)
			INSTANCE = new GenExclusionCompositeAOP(aspectCollector);
		
		return INSTANCE;
	}
}
