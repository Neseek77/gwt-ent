package com.gwtent.client.ui.validate;

public class StringValidateProcessor implements ExpressionProcessor {

	private static StringValidateProcessor processor;
	
	public static StringValidateProcessor getInstance(){
		if (processor == null)
			processor = new StringValidateProcessor();
		
		return processor;
	}
	
	public boolean canProcess(String expression) {
		if (expression.startsWith("StrLength")){
			return true;
		}else{
			return false;
		}
	}

	
	protected void validateLength(String expression, String value) throws ValidateException {
		if (expression.indexOf(">") > 0){
			String[] strs = expression.split(">");
			int length = (Integer.valueOf(strs[1])).intValue();
			
			if (value.length() <= length){
				throw new ValidateException("String length must great than " + length);
			}
		}
		
		if (expression.indexOf("<") > 0){
			String[] strs = expression.split("<");
			int length = (Integer.valueOf(strs[1])).intValue();
			
			if (value.length() >= length){
				throw new ValidateException("String length must less than " + length);
			}
		}
		
	}

	public void AsyncValidate(String expression, Object value, ValidateCallBack callBack) throws ValidateException {
		// TODO Auto-generated method stub
		
	}

	public void SyncValidate(String expression, Object value) throws ValidateException {
		validateLength(expression, value.toString());
		
	}

}
