/**
 * FileName: TestCase.java
 * Date:			04/09/2008 3:03:01 PM
 * Author:		JamesLuo.au@gmail.com
 * purpose:
 * 
 * History:  
 * 
 * Test:      -Dgwt.args="-help -port 8888 -notHeadless"
 * 
 * WebSite:   http://www.sqlite.org/lang.html
 */


package com.gwtent.orm.client.test;

import com.google.gwt.gears.client.Factory;
import com.google.gwt.gears.client.database.Database;
import com.google.gwt.gears.client.database.DatabaseException;
import com.google.gwt.gears.client.database.ResultSet;
import com.google.gwt.junit.client.GWTTestCase;

import com.gwtent.orm.client.GearsUtils;

public class GearsTestCase extends GWTTestCase {
  
  private Database db;

  @Override
  public String getModuleName() {
    return "com.gwtent.orm.Orm";
  }
  
  public void testHowLongGWTStartUp() {
    assertTrue(2 + 2 == 4);
  }
  
  public void testGreas() throws DatabaseException{
      db = Factory.getInstance().createDatabase();
      db.open("database-demo");
      db.close();
  }
  
  
  
  public void testGreasCRUD() throws DatabaseException{
    db = Factory.getInstance().createDatabase();
    db.open("database-demo");
    db.execute("DROP TABLE if exists TestTable", new String[]{});
    db.execute("create table if not exists TestTable (Phrase varchar(255), Timestamp int)");
    ResultSet rs = db.execute("SELECT * From TestTable", new String[] {});
    for (int i = 0; i < rs.getFieldCount(); i++)
      System.out.println(rs.getFieldAsString(i));
    //assertTrue(rs.getFieldCount() == 2);
    db.execute("INSERT INTO TestTable values(?, ?)", new String[]{"Test1", "1"});
    rs = db.execute("SELECT * From TestTable", new String[] {});
    for (int i = 0; i < rs.getFieldCount(); i++)
      System.out.println(rs.getFieldName(i));
    
    assertTrue(GearsUtils.getRecordCount(db.execute("SELECT * From TestTable", new String[] {})) == 1);
    db.execute("DELETE FROM TestTable", new String[]{});
    assertTrue(GearsUtils.getRecordCount(db.execute("SELECT * From TestTable", new String[] {})) == 0);
    db.execute("DROP TABLE TestTable", new String[]{});
    assertTrue(testDropTable(db));
    db.close();    
  }
  
  private boolean testDropTable(Database db){
    try {
      db.execute("SELECT * From TestTable", new String[] {});
    } catch (DatabaseException e) {
      return true;
    }
    
    return false;
  }

}
