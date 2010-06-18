/*******************************************************************************
 *  Copyright 2001, 2007 JamesLuo(JamesLuo.au@gmail.com)
 *  
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not
 *  use this file except in compliance with the License. You may obtain a copy of
 *  the License at
 *  
 *  http://www.apache.org/licenses/LICENSE-2.0
 *  
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 *  WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 *  License for the specific language governing permissions and limitations under
 *  the License.
 * 
 *  Contributors:
 *******************************************************************************/


package com.gwtent.gen.uibind;

import java.io.PrintWriter;

import com.google.gwt.core.ext.GeneratorContext;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.typeinfo.JClassType;
import com.google.gwt.user.rebind.ClassSourceFileComposerFactory;
import com.google.gwt.user.rebind.SourceWriter;
import com.gwtent.gen.GenExclusion;
import com.gwtent.gen.LogableSourceCreator;
import com.gwtent.gen.template.DataBinderUtils;

public class DataBinderCreator extends LogableSourceCreator {
	
	
	private JClassType ownerClassType;


	public DataBinderCreator(TreeLogger logger, GeneratorContext context,
			String typeName) {
		super(logger, context, typeName);
	
		
	}
	

	protected void createSource(SourceWriter source, JClassType classType){
		DataBinderUtils dataBinderGen = new DataBinderUtils(source, classType);
		dataBinderGen.buildSource(typeOracle);
	}
	
  

	/**
	 * SourceWriter instantiation. Return null if the resource already exist.
	 * 
	 * @return sourceWriter
	 */
	public SourceWriter doGetSourceWriter(JClassType classType) throws Exception {
		
		//Created into owner package, so can access package visable fields
		String packageName = ownerClassType.getPackage().getName();
		
		String simpleName = DataBinderUtils.getDataBinderClassName(classType); 
		ClassSourceFileComposerFactory composer = new ClassSourceFileComposerFactory(
				packageName, simpleName);
		composer.setSuperclass("com.gwtent.uibinder.client.UIBinderManager<" + ownerClassType.getQualifiedSourceName() + ">");
		composer.addImplementedInterface(classType.getQualifiedSourceName());
		composer.addImport(classType.getQualifiedSourceName());
		

		composer.addImport("com.google.gwt.core.client.*");
		composer.addImport("com.google.gwt.user.client.*");
		composer.addImport("com.gwtent.reflection.client.*");
		composer.addImport("java.util.*");
		composer.addImport(classType.getPackage().getName() + ".*");
		composer.addImport(ownerClassType.getPackage().getName() + ".*");

		PrintWriter printWriter = context.tryCreate(logger, packageName,
				simpleName);
		if (printWriter == null) {
			return null;
		} else {
			SourceWriter sw = composer.createSourceWriter(context, printWriter);
			return sw;
		}
		
	}

	@Override
	protected String getUnitName(JClassType classType){
		ownerClassType = DataBinderUtils.getOwnerClassType(typeOracle, classType);
		if (ownerClassType == null)
			throw new RuntimeException("DataBinder need a owner class as the Parameterized type. current class:" + classType.getQualifiedSourceName());
	  
		
		return ownerClassType.getPackage().getName() + "." + DataBinderUtils.getDataBinderClassName(classType);
	}
	
	protected GenExclusion getGenExclusion(){
		return null;
	}


	@Override
	protected String getSUFFIX() {
		return null;
	}

}

