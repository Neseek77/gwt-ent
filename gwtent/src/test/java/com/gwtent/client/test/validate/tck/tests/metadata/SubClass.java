// $Id: SubClass.java 17640 2009-10-07 08:36:01Z hardy.ferentschik $
/*
* JBoss, Home of Professional Open Source
* Copyright 2009, Red Hat, Inc. and/or its affiliates, and individual contributors
* by the @authors tag. See the copyright.txt in the distribution for a
* full listing of individual contributors.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
* http://www.apache.org/licenses/LICENSE-2.0
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.gwtent.client.test.validate.tck.tests.metadata;

import javax.validation.GroupSequence;
import javax.validation.constraints.Max;


/**
 * @author Hardy Ferentschik
 */
@GroupSequence({ SubClass.class, SubClass.DefaultGroup.class })
public class SubClass extends SuperClass {
	@Max(value = 10, groups = SubClass.DefaultGroup.class)
	private String myField = "1234567890";

	public String yourField = "";

	public interface DefaultGroup {
	}
}
