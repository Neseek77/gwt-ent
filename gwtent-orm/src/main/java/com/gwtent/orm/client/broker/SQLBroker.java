package com.gwtent.orm.client.broker;


public interface SQLBroker {
	public void close() throws BrokerException;
	
	public ResultSet execute(String sqlStatement, String... args) throws BrokerException;
	
	public int getRowsAffected();
	
	public void open(String name);
}
