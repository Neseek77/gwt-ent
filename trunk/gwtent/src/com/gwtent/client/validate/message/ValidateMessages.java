/*
 * Copyright 2008 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.gwtent.client.validate.message;

import com.google.gwt.i18n.client.Messages;
import com.gwtent.client.reflection.Reflectable;
import com.gwtent.client.reflection.annotations.Reflect_Full;
import com.gwtent.client.reflection.annotations.Reflect_Mini;

//@Reflectable(relationTypes=true, superClasses=true, assignableClasses=true)
@Reflect_Mini
public interface ValidateMessages extends Messages {
	String constraint_null();
	String constraint_notNull();
	String constraint_assertTrue();
	String constraint_assertFalse();
	String constraint_min(String value);
	String constraint_max(String value);
	String constraint_size(String min, String max);
	String constraint_digits(String integer, String fraction);
	String constraint_past();
	String constraint_future();
	String constraint_pattern(String regexp);
	String constraint_notEmpty();
	
	//
	String constraint_required();
	String constraint_regular(String regexp);
}