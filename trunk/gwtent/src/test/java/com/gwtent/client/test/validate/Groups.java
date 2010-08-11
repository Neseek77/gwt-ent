package com.gwtent.client.test.validate;

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
	
	
	public static interface FirstLevelCheck extends Default{
		
	}
	
}
