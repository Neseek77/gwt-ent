package com.gwtent.orm.client.broker;

public interface Broker {
	public boolean save(Object object);
	public boolean delete(Object object);
}
