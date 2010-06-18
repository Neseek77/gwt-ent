package com.gwtent.orm.client.broker;

import java.util.Date;

/**
 * Represents the results of executing an SQL statement. A
 * <code>ResultSet</code> must have its {@link #close()} called once it is no
 * longer needed.
 * 
 * The {@link ResultSet} class provides methods for getting fields as a
 * particular type, <code>getFieldAdByte</code>, <code>getFieldAdChar</code>,
 * etc. These getter methods will attempt to convert the field value into the
 * requested type, but the overflow behavior is undefined.
 */
public interface ResultSet {

	/**
	 * Releases the state associated with this {@link ResultSet}.
	 * 
	 * You are required to call close() when you are finished with any
	 * {@link ResultSet}.
	 * 
	 * Note: there is currently a feature request against the JavaScript API to
	 * have close called automatically when the result set goes out of scope.
	 * 
	 * @throws DatabaseException on any error
	 */
	public void close() throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>byte</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a byte
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public byte getFieldAsByte(int fieldIndex)
			throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>char</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a char
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public char getFieldAsChar(int fieldIndex)
			throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>Date</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a Date
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public Date getFieldAsDate(int fieldIndex)
			throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>double</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a double
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public double getFieldAsDouble(int fieldIndex)
			throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>float</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a float
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public float getFieldAsFloat(int fieldIndex)
			throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as an
	 * <code>int</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as an int
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public int getFieldAsInt(int fieldIndex) throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>long</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a long
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public long getFieldAsLong(int fieldIndex)
			throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>short</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a short
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public short getFieldAsShort(int fieldIndex)
			throws BrokerException;

	/**
	 * Retrieves the value of the field at <code>fieldIndex</code> as a
	 * <code>String</code>.
	 * 
	 * @param fieldIndex zero-based index of the field
	 * @return the field value as a String
	 * @throws BrokerException if the <code>fieldIndex</code> is out of range
	 */
	public String getFieldAsString(int fieldIndex)
			throws BrokerException;

	/**
	 * Returns the number of fields in this result set.
	 * 
	 * @return the number of fields (columns) in this result set
	 */
	public int getFieldCount();

	/**
	 * Returns the name of the specified field (column) in this result set.
	 * 
	 * @param fieldIndex the zero-based index of the desired field
	 * @return the name of the field
	 */
	public String getFieldName(int fieldIndex)
			throws BrokerException;

	/**
	 * Returns <code>true</code> if this <code>ResultSet</code> cursor is
	 * currently pointing at a valid row. Typically used to iterate over rows in
	 * the ResultSet.
	 * 
	 * @return true if there is a valid row containing data, false otherwise
	 */
	public boolean isValidRow();

	/**
	 * Advances to the next row of the results.
	 */
	public void next();

}