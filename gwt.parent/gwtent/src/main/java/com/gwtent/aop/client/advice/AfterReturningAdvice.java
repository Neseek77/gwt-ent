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


package com.gwtent.aop.client.advice;

import com.gwtent.aop.client.intercept.MethodInvocation;
import com.gwtent.reflection.client.Method;

/**
 * 
 * @author JamesLuo.au@gmail.com
 *
 */
public class AfterReturningAdvice extends AbstractAdvice {

	public AfterReturningAdvice(Method method, Object aspectInstance) {
		super(method, aspectInstance);
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object returnValue = invocation.proceed();
		
		if (shouldInvokeByReturnValue(returnValue))
			getAdviceMethod().invoke(getAspectInstance(), getArgs(invocation, returnValue));
		
		return returnValue;
	}
	
	private boolean shouldInvokeByReturnValue(Object returnValue){
//		ClassType classType = TypeOracle.Instance.getClassType(returnValue.getClass());
//		
//		if (classType != null){
//			
//		}
		return true;
	}

}
