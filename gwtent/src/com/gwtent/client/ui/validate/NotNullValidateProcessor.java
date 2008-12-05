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


package com.gwtent.client.ui.validate;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class NotNullValidateProcessor implements ExpressionProcessor {

	private static NotNullValidateProcessor processor;
	
	public static NotNullValidateProcessor getInstance(){
		if (processor == null)
			processor = new NotNullValidateProcessor();
		
		return processor;
	}

	private final List tests = new ArrayList();
	
	private NotNullValidateProcessor(){
		new ObjectTest();
		new StringTest();
		new DateTest();
	}

	
	public boolean canProcess(String expression) {
		if (expression.equalsIgnoreCase("NotNull"))
			return true;
		else
			return false;
	}

	
	public void AsyncValidate(String expression, Object value, ValidateCallBack callBack) throws ValidateException {
		// TODO Auto-generated method stub
		
	}


	public void SyncValidate(String expression, Object value) throws ValidateException {
		Iterator iterator = tests.iterator();
		while (iterator.hasNext()){
			NotNullTest test = (NotNullTest)iterator.next();
			test.test(value);
		}
	}
	
	
	interface NotNullTest {
		/**
		 * if null return true
		 * 
		 * @param value
		 * @return
		 */
		public void test(Object value) throws ValidateException;
	}

	
	
	/**
	 * not allow abstract class here ?
	 */
	abstract class AutoRegTest implements NotNullTest {
		public AutoRegTest() {
			tests.add(this);
		}

		public abstract void test(Object value) throws ValidateException;
	}

	class ObjectTest extends AutoRegTest implements NotNullTest {

		public void test(Object value) throws ValidateException {
			if (value == null)
				throw new ValidateException("Value not allow null.");
		}
	}

	class StringTest extends AutoRegTest implements NotNullTest {

		public void test(Object value) throws ValidateException {
			if (value instanceof String) {
				if (((String) value).length() <= 0)
					throw new ValidateException("Value not allow null.");
			}
		}
	}

	class DateTest extends AutoRegTest implements NotNullTest {

		public void test(Object value) throws ValidateException {
			if (value instanceof Date) {
				if (!((Date) value).equals(new Date(0)))
					throw new ValidateException("Date not allow null and must > " + new Date(0));
			}
		}

	}



	// TODO Implements more NotNull Test


}
