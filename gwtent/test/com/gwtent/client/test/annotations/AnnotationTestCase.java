package com.gwtent.client.test.annotations;

import com.gwtent.client.test.GwtEntTestCase;

public class AnnotationTestCase extends GwtEntTestCase{
	public void testAnnotation(){
		UniqueConstraint u = new UniqueConstraintImpl();
		assertTrue(u.columnNames().length == 2);
	}
}
