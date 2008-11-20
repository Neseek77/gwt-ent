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
		String result = sb.toString();//super.getMessage();
		
		if (this.getCause() != null)
			result = result + "(Cause: " + getCause().getMessage() + ")";
		
		return result;
	}
}
