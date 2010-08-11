package com.gwtent.validate.client.util;

/**
 * emul for java.security.PrivilegedAction<T>
 *
 * 10/08/2010 2:32:20 PM
 */
public interface PrivilegedAction<T> {
	/**
   * Performs the computation.  This method will be called by
   * <code>AccessController.doPrivileged</code> after enabling privileges.
   *
   * @return a class-dependent value that may represent the results of the
   *	       computation. Each class that implements
   *         <code>PrivilegedAction</code>
   *	       should document what (if anything) this value represents.
   * @see AccessController#doPrivileged(PrivilegedAction)
   * @see AccessController#doPrivileged(PrivilegedAction,
   *                                     AccessControlContext)
   */
  T run();
}
