// $Id: C.java 17620 2009-10-04 19:19:28Z hardy.ferentschik $
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
package com.gwtent.client.test.validate.tck.tests.constraints.groups.groupsequenceisolation;

import javax.validation.GroupSequence;
import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;
import javax.validation.groups.Default;

/**
 * @author Hardy Ferentschik
 */
@GroupSequence({ Minimal.class, C.class })
public class C {

	public C() {
		d = new D1();
	}

	@Max(value = 10, groups = Minimal.class)
	int size;

	@Size(max = 20)
	String name;

	@Valid
	D1 d;
}

@GroupSequence({ Default.class, Heavy.class })
interface Complete {
}

interface Later {
}
