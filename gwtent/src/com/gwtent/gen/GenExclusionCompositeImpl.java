package com.gwtent.gen;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.typeinfo.JClassType;

public class GenExclusionCompositeImpl implements GenExclusion, GenExclusionComposite {

	private List<GenExclusion> genExclusions = new ArrayList<GenExclusion>();
	
	public void addGenExclusion(GenExclusion exclusion){
		genExclusions.add(exclusion);
	}
	
	public boolean exclude(JClassType classType) {
		for (GenExclusion e : genExclusions){
			if (e.exclude(classType))
				return true;
		}
		
		return false;
	}

}
