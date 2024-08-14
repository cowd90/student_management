package com.unit.studentmgmt.exception;

public class AppException extends RuntimeException {
	private ErrorCode errorCode;
	
	public AppException(ErrorCode errorCode) {
		super();
		this.errorCode = errorCode;
	}

	public ErrorCode getErrorCode() {
		return errorCode;
	}
	
}
