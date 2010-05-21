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


/**
 * FileName: AllTests.java
 * Date:			04/09/2008 3:47:02 PM
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.test;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.gwtent.client.test.allmodules.AllModulesTestCase;
import com.gwtent.client.test.aop.AOPTestCase;
import com.gwtent.client.test.common.CommonTestCase;
import com.gwtent.client.test.common.annotations.AnnotationTestCase;
import com.gwtent.client.test.i18n.I18NTestCase;
import com.gwtent.client.test.json.SerializationTestCase;
import com.gwtent.client.test.reflection.ReflectionTestCase;
import com.gwtent.client.test.template.TemplateTestCase;
import com.gwtent.client.test.uibinder.DataBinderTestCase;
import com.gwtent.client.test.uibinder.UIBinderTestCase;
import com.gwtent.client.test.validate.ValidateTestCase;

public class AllTests {

  public static Test suite() {
    TestSuite suite = new TestSuite("Test for com.gwtent.client");
    //$JUnit-BEGIN$
    suite.addTestSuite(CommonTestCase.class);
    suite.addTestSuite(DataBinderTestCase.class);
    suite.addTestSuite(I18NTestCase.class);
    suite.addTestSuite(AnnotationTestCase.class);
    suite.addTestSuite(UIBinderTestCase.class);
    suite.addTestSuite(SerializationTestCase.class);
    suite.addTestSuite(ValidateTestCase.class);
    suite.addTestSuite(StudyTestCase.class);
    suite.addTestSuite(ReflectionTestCase.class);
    suite.addTestSuite(AOPTestCase.class);
    suite.addTestSuite(TemplateTestCase.class);
    suite.addTestSuite(AllModulesTestCase.class);
    //$JUnit-END$
    return suite;
  }

}
