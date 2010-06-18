package com.gwtent.orm.client.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import com.google.gwt.junit.client.GWTTestCase;
import com.gwtent.orm.client.test.domain.Account;

public class PersistenceTestCase extends GWTTestCase {

	@Override
	public String getModuleName() {
		return "com.gwtent.orm.Orm";
	}

	public void testMain() {
		EntityManagerFactory factory = Persistence
				.createEntityManagerFactory(new TestUnitInfo());
		EntityManager em = factory.createEntityManager();

		// Begin a new local transaction so that we can persist a new entity
		em.getTransaction().begin();

		Account account = new Account();
		account.setAddress1("address 1");
		account.setBannerName("bannerName");
		account.setBannerOption(true);
		account.setCity("Melbourne");
		account.setCountry("Australia");
		account.setEmail("JamesLuo.au@gmail.com");
		account.setFirstName("James");
		account.setLastName("Luo");
		account.setPassword("passsword");
		account.setUsername("JamesLuo");

		em.persist(account);
		em.getTransaction().commit();

		em.close();

	}

}
