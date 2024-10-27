package com.unit.studentmgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION("App-01", "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INCORRECT_EMAIL_PASSWORD("App-02", "Email or password is incorrect! Try again", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED("App-03", "Unauthorized", HttpStatus.FORBIDDEN),
	USER_NOT_EXISTS("App-04", "No such user exists", HttpStatus.NOT_FOUND),
	INVALID_KEY("App-05", "Invalid message key", HttpStatus.BAD_REQUEST),
	FILE_TOO_LARGE("App-06", "Image size exceeds the maximum limit of {min}KB", HttpStatus.PAYLOAD_TOO_LARGE),
	INVALID_TOKEN("App-07", "Invalid token", HttpStatus.UNAUTHORIZED),
	UNAUTHENTICATED("App-08", "Unauthenticated", HttpStatus.UNAUTHORIZED),
	INVALID_FILE_TYPE("App-09", "Invalid file type. Only JPEG, PNG are allowed.",
			HttpStatus.UNSUPPORTED_MEDIA_TYPE),
	FORBIDDEN("App-10", "You don't have permission.", HttpStatus.FORBIDDEN),
	;
	
	String code;
	String message;
	HttpStatusCode statusCode;
	
	private ErrorCode(String code, String message, HttpStatusCode statusCode) {
		this.code = code;
		this.message = message;
		this.statusCode = statusCode;
	}
}
