package com.gwtent.orm.client.test;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.spi.PersistenceUnitInfo;

import com.google.gwt.core.client.GWT;
import com.gwtent.reflection.client.ClassType;
import com.gwtent.orm.client.test.domain.Account;

public class TestUnitInfo implements PersistenceUnitInfo {

	public String getDatabaseName() {
		return "test_database";
	}

	public List<ClassType> getManagedClasses() {
		List<ClassType> result = new ArrayList<ClassType>();
		
		result.add((ClassType)GWT.create(Account.class));
		
		return result;
	}

}
