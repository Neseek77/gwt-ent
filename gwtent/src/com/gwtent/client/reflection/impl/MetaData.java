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


package com.gwtent.client.reflection.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.gwtent.client.reflection.HasMetaData;


class MetaData implements HasMetaData {

  private final Map tagNameToStringArrayList = new HashMap();

  public void addMetaData(String tagName, String[] values) {
    List list = (List) tagNameToStringArrayList.get(tagName);
    if (list == null) {
      list = new ArrayList();
      tagNameToStringArrayList.put(tagName, list);
    }
    // Yes, we're adding the string array as an object into the list.
    //
    list.add(values);
  }

  public String[][] getMetaData(String tagName) {
    List list = (List) tagNameToStringArrayList.get(tagName);
    if (list != null) {
      return (String[][]) list.toArray(TypeOracleImpl.NO_STRING_ARR_ARR);
    } else {
      return TypeOracleImpl.NO_STRING_ARR_ARR;
    }
  }

  public String[] getMetaDataTags() {
    return (String[]) tagNameToStringArrayList.keySet().toArray(
      TypeOracleImpl.NO_STRINGS);
  }

  public String toString() {
    StringBuffer sb = new StringBuffer();
    final Set keys = tagNameToStringArrayList.keySet();
    String[] tags = (String[]) keys.toArray(TypeOracleImpl.NO_STRINGS);
    Arrays.sort(tags);
    for (int i = 0; i < tags.length; i++) {
      sb.append(tags[i]);
      sb.append(" => ");

      boolean isFirstList = true;
      List stringArrayList = (List) tagNameToStringArrayList.get(tags[i]);
      for (Iterator iter = stringArrayList.iterator(); iter.hasNext();) {
        if (isFirstList) {
          isFirstList = false;
        } else {
          sb.append(", ");
        }
        sb.append('[');
        String[] values = (String[]) iter.next();
        boolean needComma = false;
        for (int j = 0; j < values.length; j++) {
          if (needComma) {
            sb.append(", ");
          } else {
            needComma = true;
          }
          sb.append(values[j]);
        }
        sb.append(']');
      }
    }
    return sb.toString();
  }
}
