package com.jiajie.exceptions;


public class SystemException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3563672946725861559L;
	private String key ;
	private Object[] errorMessageParams;
	public SystemException() {
		super();
	}

	public SystemException(String errorMessage, Throwable throwable) {
		super(errorMessage, throwable);
	}

	public SystemException(String errorMessage) {
		super(errorMessage);
	}

	public SystemException(Throwable throwable) {
		super(throwable);
	}
	
	public SystemException(String errorMessage,String key) {
		super(errorMessage);
		
	}
	public SystemException(String errorMessage,Object[] errorMessageParams,String key) {
		super(errorMessage);
		this.key = key;
		this.errorMessageParams = errorMessageParams;
	}

	public String getKey() {
		return key;
	}

	public Object[] getErrorMessageParams() {
		return errorMessageParams;
	}
}
