package com.gwtent.reflection;

import java.util.Stack;

import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.user.rebind.SourceWriter;

public class SourceWriterLogDecorator implements SourceWriter {

	private SourceWriter sourceWriter;
	private TreeLogger rootLogger;
	private boolean useLog;
	
	private TreeLogger currentTreeLogger = null;
	private final Stack<TreeLogger> logStack = new Stack<TreeLogger>();
	
	private int lineNumber = 0;
	
	private final TreeLogger.Type logType = TreeLogger.ERROR;
	
	public SourceWriterLogDecorator(SourceWriter sourceWriter, TreeLogger logger, boolean useLog, int baseLineNumber){
		this.sourceWriter = sourceWriter;
		this.rootLogger = logger;
		this.lineNumber = baseLineNumber;
		logStack.push(rootLogger);
		currentTreeLogger = rootLogger.branch(logType, "branch for new source", null);
		logStack.push(currentTreeLogger);

		this.useLog = useLog;
	}
	
	public void beginJavaDocComment() {
		sourceWriter.beginJavaDocComment();
	}

	public void commit(TreeLogger logger) {
		sourceWriter.commit(logger);
		
	}

	public void endJavaDocComment() {
		sourceWriter.endJavaDocComment();
		
	}

	public void indent() {
		sourceWriter.indent();
		
		if (useLog){
			push(currentTreeLogger);
			currentTreeLogger = currentTreeLogger.branch(logType, "", null);
		}
		
	}

	public void indentln(String s) {
		sourceWriter.indentln(s);
		lineNumber++;
		if (useLog){
			push(currentTreeLogger.branch(logType, "" + s, null));
		}
		
	}

	public void outdent() {
		sourceWriter.outdent();

		if (useLog){
			//pop().log(logType, "(outdent)", null);
			pop();
		}
	}

	public void print(String s) {
		//TODO this have a mistake, wrong line with log!
		sourceWriter.print(s);
		if (useLog){
			log(s);
		}
		
	}

	public void println() {
		sourceWriter.println();
		lineNumber++;
		if (useLog){
			log("");
		}	
	}

	public void println(String s) {
		sourceWriter.println(s);
		
		lineNumber++;
		if (useLog){
			log(s);
		}	
	}
	

	/**
	 * pop TreeLogger to currentTreeLogger;
	 * @return
	 */
	private TreeLogger pop(){
		if (logStack.empty()){
			currentTreeLogger = null;
		}else{
			currentTreeLogger = this.logStack.pop();
		}
		
		
		/**
		 * we use rootlogger as the last logger, this can tall use the dent is error.
		 */
		if (currentTreeLogger == null){
			currentTreeLogger = this.rootLogger;
		}
		
		return currentTreeLogger;
	}
	
	/**
	 * push to stack and currnetTreeLogger
	 * @param logger
	 */
	private void push(TreeLogger logger){
		this.logStack.push(logger);
		this.currentTreeLogger = logger;
	}
	
	private void log(String msg){
		currentTreeLogger.log(logType, msg + "(" + lineNumber + ")", null);
	}

}
