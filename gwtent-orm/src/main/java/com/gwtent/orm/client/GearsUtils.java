/**
 * FileName: Utils.java
 * Date:			05/09/2008 1:05:02 PM
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:
 * 
 */


package com.gwtent.orm.client;

import com.google.gwt.gears.client.database.ResultSet;

public class GearsUtils {
  static public int getRecordCount(ResultSet rs){
    int result = 0;
    for (int i = 0; rs.isValidRow(); ++i, rs.next()) {
      result++;
    }
    return result;
  }
}
