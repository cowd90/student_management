package com.unit.studentmgmt.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;

@Getter
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public enum ErrorCode {
	UNCATEGORIZED_EXCEPTION(499, "Uncategorized error", HttpStatus.INTERNAL_SERVER_ERROR),
	INCORRECT_PASSWORD(401, "Password is incorrect! Try again", HttpStatus.UNAUTHORIZED),
	UNAUTHORIZED(403, "Unauthorized", HttpStatus.FORBIDDEN),
	USER_NOT_EXISTS(404, "No such user exists", HttpStatus.NOT_FOUND),
	INVALID_KEY(405, "Invalid message key", HttpStatus.BAD_REQUEST),
	INVALID_PASSWORD(410, "Password must be at least {min} characters", HttpStatus.BAD_REQUEST),
	FILE_TOO_LARGE(413, "Image size exceeds the maximum limit of 10MB", HttpStatus.PAYLOAD_TOO_LARGE),
	INVALID_TOKEN(419, "Invalid token", HttpStatus.UNAUTHORIZED),
	UNAUTHENTICATED(420, "Unauthenticated", HttpStatus.UNAUTHORIZED),
	INVALID_FILE_TYPE(422, "Invalid file type. Only JPEG, PNG, and GIF are allowed.",
			HttpStatus.UNSUPPORTED_MEDIA_TYPE),
	INVALID_IMAGE_DIMENSION(423, "Invalid image dimensions. Required dimensions are 3x4.",
			HttpStatus.BAD_REQUEST),
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
