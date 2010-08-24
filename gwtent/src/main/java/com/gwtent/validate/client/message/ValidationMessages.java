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
package com.gwtent.validate.client.message;

import com.google.gwt.i18n.client.Messages;
import com.gwtent.reflection.client.Reflectable;
import com.gwtent.reflection.client.annotations.Reflect_Full;
import com.gwtent.reflection.client.annotations.Reflect_Mini;

//@Reflectable(relationTypes=true, superClasses=true, assignableClasses=true)
@Reflect_Mini
public interface ValidationMessages extends Messages {
	String javax_validation_constraints_AssertFalse_message();
	String javax_validation_constraints_AssertTrue_message();
	String javax_validation_constraints_DecimalMax_message(String value);
	String javax_validation_constraints_DecimalMin_message(String value);
	String javax_validation_constraints_Digits_message(String integer, String fraction);
	String javax_validation_constraints_Future_message();
	String javax_validation_constraints_Max_message(String value);
	String javax_validation_constraints_Min_message(String value);
	String javax_validation_constraints_NotNull_message();
	String javax_validation_constraints_Null_message();
	String javax_validation_constraints_Past_message();
	String javax_validation_constraints_Pattern_message(String regexp);
	String javax_validation_constraints_Size_message(String min, String max);
	String com_gwtent_validate_client_constraints_Email_message(String value);
	String com_gwtent_validate_client_constraints_Length_message(String min, String max);
	String com_gwtent_validate_client_constraints_NotEmpty_message();
	String com_gwtent_validate_client_constraints_Range_message(String value, String min, String max);
}