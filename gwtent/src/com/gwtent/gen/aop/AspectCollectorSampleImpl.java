package com.gwtent.gen.aop;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.typeinfo.JClassType;
import com.gwtent.aop.Pointcut;

public class AspectCollectorSampleImpl implements AspectCollector {

	private List<Class<?>> advicedClasses = new ArrayList<Class<?>>();
	private List<JClassType> aspectedClasses = new ArrayList<JClassType>();
	private List<Pointcut> pointCuts = new ArrayList<Pointcut>();
	
	AspectCollectorSampleImpl(){
		
	}
	
	@Override
	public List<Class<?>> allAdvicedClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	public List<JClassType> allAspectedClasses() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Pointcut> allPointCut() {
		// TODO Auto-generated method stub
		return null;
	}

}
