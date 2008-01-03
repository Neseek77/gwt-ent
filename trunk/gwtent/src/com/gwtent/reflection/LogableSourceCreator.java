package com.gwtent.reflection;

import java.io.PrintWriter;

import com.google.gwt.core.ext.Generator;
import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.gwt.core.ext.typeinfo.JClassType;
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
		
		if (logger == null) useLog = false;
	}
	
	/**
	 * override this method to provide really SourceWriter
	 * getSourceWriter will return the Decoratored SourceWriter;
	 * @param classType
	 * @return
	 */
	protected abstract SourceWriter doGetSourceWriter(JClassType classType);
	
	
	/**
	 * return the Decoratored SourceWriter
	 * @param classType
	 * @param useLog 
	 * @return
	 */
	public SourceWriter getSourceWriter(JClassType classType, boolean useLog){
		if (sourceWriter == null){
			sourceWriter = doGetSourceWriter(classType);
			//Decorator it
			if (sourceWriter != null)
				sourceWriter = new SourceWriterLogDecorator(sourceWriter, this.logger, useLog, 5);
		}
		
		return sourceWriter;
	}
	
	

}
