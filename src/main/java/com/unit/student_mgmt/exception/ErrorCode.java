package com.unit.student_mgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
	INVALID_TOKEN(419, "Invalid token", HttpStatus.UNAUTHORIZED),
	UNAUTHENTICATED(420, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	
	;
	
	int code;
	String message;
	HttpStatusCode statusCode;
	
	private ErrorCode(int code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}
}
