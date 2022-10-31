package net.dg.ratingservice.exceptions;

import net.dg.ratingservice.constants.ServiceConstants;

public class RatingNotFoundException extends RuntimeException {

	public RatingNotFoundException() {
		super(ServiceConstants.RATING_NOT_FOUND_EXCEPTION_MSG);
	}

}