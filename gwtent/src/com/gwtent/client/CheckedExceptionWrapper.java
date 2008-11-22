package com.gwtent.client;

public class CheckedExceptionWrapper extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public CheckedExceptionWrapper(String message){
		super(message);
	}
	
	public CheckedExceptionWrapper(String message, Throwable cause) {
    super(message, cause);
	}
	
	public CheckedExceptionWrapper(Throwable cause) {
    super(cause);
	}
	
	public String getMessage(){
		StringBuilder sb = new StringBuilder(super.getMessage());
		
		if (this.getCause() != null)
			sb.append("(Cause: " + getCause() + ")");
		
		return sb.toString();
	}
}
