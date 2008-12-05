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


package com.gwtent.client.ui.generator;

import com.gwtent.client.ui.editorFactory.EditorFactory;
import com.gwtent.client.ui.editorFactory.EditorFactoryImpl;

public class GwtAutoUIGenerator extends AbstractAutoUIGenerator {

	private EditorFactory editorFactory = new EditorFactoryImpl();

	protected EditorFactory getEditorFactory() {
		return editorFactory;
	}


}
