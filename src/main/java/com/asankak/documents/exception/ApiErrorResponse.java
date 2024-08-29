package com.asankak.documents.exception;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiErrorResponse {

	private String message;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private final LocalDateTime timestamp;

}
