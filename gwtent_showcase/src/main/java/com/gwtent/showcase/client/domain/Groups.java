package com.gwtent.showcase.client.domain;

import javax.validation.groups.Default;

public class Groups {
	
	/**
	* Validation group checking a user is billable
	*/
	public static interface Billable extends Default {

	}

	
	/**
	* customer can buy without any harrassing checking process
	*/
	public static interface BuyInOneClick extends Billable {
		
	}
	
}
