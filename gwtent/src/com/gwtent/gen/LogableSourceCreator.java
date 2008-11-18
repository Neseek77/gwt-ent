/*
 * GwtEnt - Gwt ent library.
 * 
 * Copyright (c) 2007, James Luo(JamesLuo.au@gmail.com)
 * 
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * 
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * 
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package com.gwtent.gen;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.core.ext.typeinfo.NotFoundException;
import com.google.gwt.core.ext.typeinfo.TypeOracle;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;

/**
 * usage:
 * 
 *   inherited this class and override doGetSourceWriter abstract method
 *   use useLog to set if have log output
 *   
 * @author James Luo
 * 2007-12-20 12:21:43 am
 *
 */
public abstract class LogableSourceCreator {

	private boolean useLog = false;
	private SourceWriter sourceWriter = null; 
	
	protected TreeLogger logger;
	protected GeneratorContext context;
	protected TypeOracle typeOracle;
	protected String typeName;

	public LogableSourceCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		this.logger = logger;
		this.context = context;
		this.typeOracle = context.getTypeOracle();
		this.typeName = typeName;
	}

	public boolean isUseLog() {
		return useLog;
	}

	public void setUseLog(boolean useLog) {
		this.useLog = useLog;
		
		if (logger == null) this.useLog = false;
	}
	
	/**
	 * override this method to provide really SourceWriter
	 * getSourceWriter will return the Decoratored SourceWriter;
	 * @param classType
	 * @return
	 */
	protected abstract SourceWriter doGetSourceWriter(JClassType classType);
	protected abstract String getSUFFIX();
	protected abstract void createSource(SourceWriter source, JClassType classType);
	
	protected GenExclusion getGenExclusion(){
		return null;
	}
	
	protected boolean genExclusion(JClassType classType){
		if (getGenExclusion() != null){
			return getGenExclusion().exclude(classType);
		}else
			return false;
	}
	
	
	public String generate() throws UnableToCompleteException{
		JClassType classType;
		try {
			classType = typeOracle.getType(typeName);
			if (genExclusion(classType)){
				return null;
			}
		
			SourceWriter source = getSourceWriter(classType, isUseLog(), 6);
	
			if ((source != null)) {
				source.indent();
				createSource(source, classType);
				source.outdent();
				source.commit(logger);
			}
			return getUnitName(classType);
		} catch (Throwable e) {
			this.logger.log(Type.ERROR, e.getMessage());
			throw new UnableToCompleteException();
		}
	}
	
	/**
	 * return the Decorator of SourceWriter
	 * @param classType
	 * @param useLog
	 * @param baseLineNumber
	 * @return
	 */
	public SourceWriter getSourceWriter(JClassType classType, boolean useLog, int baseLineNumber){
		if ((sourceWriter == null) && (getUnitName(classType) != null)){
			sourceWriter = doGetSourceWriter(classType);
			//Decorator it
			if (sourceWriter != null)
				sourceWriter = new SourceWriterLogDecorator(sourceWriter, this.logger, useLog, baseLineNumber);
			//else
			//	throw new CheckedExceptionWrapper("Can't create Source Writer, please make sure there is no same class in your source folder.");
		}
		
		return sourceWriter;
	}
	
	protected String getPackageName(JClassType classType){
		return classType.getPackage().getName();
	}
	
	/**
	 * this name will return to GWT compiler
	 * if the class you don't care in this creator, just return the original QualifiedSourceName
	 * @param classType
	 * @return
	 */
	protected String getUnitName(JClassType classType){    
	    String packageName = getPackageName(classType);
	    String className = getSimpleUnitName(classType);
	    
		//return classType.getParameterizedQualifiedSourceName() + SUFFIX;
	    return packageName + "." + className;
	}
	
	
	protected String getSimpleUnitName(JClassType classType){
		return classType.getName().replace('.', '_') + getSUFFIX();
	}
	
	

}
