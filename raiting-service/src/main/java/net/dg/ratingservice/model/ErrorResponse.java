package net.dg.ratingservice.model;

import java.util.List;

public class ErrorResponse {

	public ErrorResponse(String message, List<String> details) {
		super();
		this.message = message;
		this.details = details;
	}

	// General error message about nature of error
	private final String message;

	// Specific errors in API request processing
	private final List<String> details;

	// Getter and setters

}