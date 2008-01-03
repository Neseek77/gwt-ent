package com.gwtent.client.ui.model.value;

public class StringValue extends ValueDefaultImpl {
	public static class StringValueCreator implements ValueCreator{

		public AdvValue createValue() {
			return new StringValue();
		}
	}
}
