package com.gwtent.client.test.validate;

import junit.framework.ComparisonFailure;

import com.gwtent.client.test.common.GwtEntTestCase;

/**
 * 
 * @author James Luo
 * 
 *         12/08/2010 2:33:54 PM
 */
public class VTestCase extends GwtEntTestCase {
	public String getModuleName() {
		return "com.gwtent.client.test.validate.Validate";
	}
	
	
	//======TestNG functions=====================
	
	/**
   * Asserts that two objects are equal. If they are not,
   * an AssertionError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(Object actual, Object expected, String message) {
    if((expected == null) && (actual == null)) {
      return;
    }
    if((expected != null) && expected.equals(actual)) {
      return;
    }
    failNotEquals(message, actual, expected);
  }
  
  /**
   * Asserts that two objects are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(Object actual, Object expected) {
    assertEquals(actual, expected, null);
  }
  
  /**
   * Asserts that two Strings are equal. If they are not,
   * an AssertionError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(String actual, String expected, String message) {
    assertEquals((Object) actual, (Object) expected, message);
  }
  
  /**
   * Asserts that two Strings are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(String actual, String expected) {
    assertEquals(actual, expected, null);
  }
  
  /**
   * Asserts that two doubles are equal concerning a delta.  If they are not,
   * an AssertionError, with the given message, is thrown.  If the expected
   * value is infinity then the delta value is ignored.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param delta the absolute tolerate value value between the actual and expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(double actual, double expected, double delta, String message) {
    // handle infinity specially since subtracting to infinite values gives NaN and the
    // the following test fails
    if(Double.isInfinite(expected)) {
      if(!(expected == actual)) {
        failNotEquals(message, new Double(actual), new Double(expected));
      }
    }
    else if(!(Math.abs(expected - actual) <= delta)) { // Because comparison with NaN always returns false
      failNotEquals(message, new Double(actual), new Double(expected));
    }
  }
  
  /**
   * Asserts that two doubles are equal concerning a delta. If they are not,
   * an AssertionError is thrown. If the expected value is infinity then the 
   * delta value is ignored. 
   * @param actual the actual value 
   * @param expected the expected value 
   * @param delta the absolute tolerate value value between the actual and expected value 
   */
  static public void assertEquals(double actual, double expected, double delta) {
    assertEquals(actual, expected, delta, null);
  }
  
  /**
   * Asserts that two floats are equal concerning a delta. If they are not,
   * an AssertionError, with the given message, is thrown.  If the expected
   * value is infinity then the delta value is ignored.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param delta the absolute tolerate value value between the actual and expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(float actual, float expected, float delta, String message) {
    // handle infinity specially since subtracting to infinite values gives NaN and the
    // the following test fails
    if(Float.isInfinite(expected)) {
      if(!(expected == actual)) {
        failNotEquals(message, new Float(actual), new Float(expected));
      }
    }
    else if(!(Math.abs(expected - actual) <= delta)) {
      failNotEquals(message, new Float(actual), new Float(expected));
    }
  }
  
  /**
   * Asserts that two floats are equal concerning a delta. If they are not,
   * an AssertionError is thrown. If the expected
   * value is infinity then the delta value is ignored.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param delta the absolute tolerate value value between the actual and expected value 
   */
  static public void assertEquals(float actual, float expected, float delta) {
    assertEquals(actual, expected, delta, null);
  }
  
  /**
   * Asserts that two longs are equal. If they are not,
   * an AssertionError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(long actual, long expected, String message) {
    assertEquals(new Long(actual), new Long(expected), message);
  }
  
  /**
   * Asserts that two longs are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(long actual, long expected) {
    assertEquals(actual, expected, null);
  }
  
  /**
   * Asserts that two booleans are equal. If they are not,
   * an AssertionError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(boolean actual, boolean expected, String message) {
    assertEquals( Boolean.valueOf(actual), Boolean.valueOf(expected), message);
  }
  
  /**
   * Asserts that two booleans are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(boolean actual, boolean expected) {
    assertEquals(actual, expected, null);
  }
  
  /**
   * Asserts that two bytes are equal. If they are not,
   * an AssertionError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(byte actual, byte expected, String message) {
    assertEquals(new Byte(actual), new Byte(expected), message);
  }
  
  /**
   * Asserts that two bytes are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(byte actual, byte expected) {
    assertEquals(actual, expected, null);
  }
  
  /**
   * Asserts that two chars are equal. If they are not,
   * an AssertionFailedError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(char actual, char expected, String message) {
    assertEquals(new Character(actual), new Character(expected), message);
  }
  
  /**
   * Asserts that two chars are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(char actual, char expected) {
    assertEquals(actual, expected, null);
  }
  
  /**
   * Asserts that two shorts are equal. If they are not,
   * an AssertionFailedError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(short actual, short expected, String message) {
    assertEquals(new Short(actual), new Short(expected), message);
  }
  
  /**
   * Asserts that two shorts are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(short actual, short expected) {
    assertEquals(actual, expected, null);
  }
  
  /**
   * Asserts that two ints are equal. If they are not,
   * an AssertionFailedError, with the given message, is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   * @param message the assertion error message 
   */
  static public void assertEquals(int actual,  int expected, String message) {
    assertEquals(new Integer(actual), new Integer(expected), message);
  }
  
  /**
   * Asserts that two ints are equal. If they are not,
   * an AssertionError is thrown.
   * @param actual the actual value 
   * @param expected the expected value 
   */
  static public void assertEquals(int actual, int expected) {
    assertEquals(actual, expected, null);
  }
  
  
  /**
   * Asserts that a condition is true. If it isn't, 
   * an AssertionError, with the given message, is thrown.
   * @param condition the condition to evaluate 
   * @param message the assertion error message 
   */
  static public void assertTrue(boolean condition, String message) {
    if(!condition) {
      failNotEquals( message, Boolean.valueOf(condition), Boolean.TRUE);
    }
  }
  
  /**
   * Asserts that a condition is true. If it isn't, 
   * an AssertionError is thrown.
   * @param condition the condition to evaluate 
   */
  static public void assertTrue(boolean condition) {
    assertTrue(condition, null);
  }
  
  /**
   * Asserts that a condition is false. If it isn't,   
   * an AssertionError, with the given message, is thrown.
   * @param condition the condition to evaluate 
   * @param message the assertion error message 
   */
  static public void assertFalse(boolean condition, String message) {
    if(condition) {
      failNotEquals( message, Boolean.valueOf(condition), Boolean.FALSE); // TESTNG-81
    }
  }
  
  /**
   * Asserts that a condition is false. If it isn't,    
   * an AssertionError is thrown.
   * @param condition the condition to evaluate 
   */
  static public void assertFalse(boolean condition) {
    assertFalse(condition, null);
  }
}
