package com.gwtent.validate.server.metadata;

import javax.validation.constraints.Future;
import javax.validation.constraints.Past;

import com.gwtent.validate.client.metadata.ConstraintHelper;
import com.gwtent.validate.server.constraints.FutureValidatorForCalendar;
import com.gwtent.validate.server.constraints.PastValidatorForCalendar;


/**
 * 
 * @author James Luo
 *
 * 06/08/2010 4:49:12 PM
 */
public class ConstraintServerHelper extends ConstraintHelper {
	public ConstraintServerHelper(){
		this.getBuiltinConstraints().get(Future.class).add(FutureValidatorForCalendar.class);
		
		this.getBuiltinConstraints().get(Past.class).add(PastValidatorForCalendar.class);
	}
}
