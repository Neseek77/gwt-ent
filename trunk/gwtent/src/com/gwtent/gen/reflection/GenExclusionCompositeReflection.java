package com.gwtent.gen.reflection;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.GenExclusionCompositeImpl;
import com.gwtent.gen.GenUtils;

/**
 * Need found a way to set exclusion from module xml file.
 * For now we hard code here.
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
public class GenExclusionCompositeReflection extends GenExclusionCompositeImpl implements GenExclusion {
	private GenExclusionCompositeReflection(){
		//Can don't create reflection for AOP generated source.
		addGenExclusion(new GenExclusion(){

			public boolean exclude(JClassType classType) {
				if (classType.getName().endsWith(GenUtils.getAOP_SUFFIX()))
					return true;
				
				return false;
			}
			
		});
	}
	
	public static final GenExclusionCompositeReflection INSTANCE = new GenExclusionCompositeReflection();
}
