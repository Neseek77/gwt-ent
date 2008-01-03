package com.gwtent.client.reflection;

/**
 * The superclass of exceptions thrown by
 * {@link TypeOracle}.
 */
public class TypeOracleException extends Exception {

  public TypeOracleException() {
    super();
  }

  public TypeOracleException(String message) {
    super(message);
  }

  public TypeOracleException(String message, Throwable cause) {
    super(message, cause);
  }

  public TypeOracleException(Throwable cause) {
    super(cause);
  }

}
