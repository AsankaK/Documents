package com.asankak.documents.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ApiExceptionHandler {

	@ExceptionHandler(DocumentManagerException.class)
	public ResponseEntity<ApiErrorResponse> handleApiException(DocumentManagerException ex) {
		ApiErrorResponse response = new ApiErrorResponse(ex.getMessage(), LocalDateTime.now());

		return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
	}

}
