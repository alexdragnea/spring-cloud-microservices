package net.dg.ratingservice.constants;

public class ServiceConstants {

	public static final String VALIDATION_FORMAT = "Constraint validation: %s.";

	// Exception messages
	public static final String BOOK_NOT_FOUND_EXCEPTION_MSG = "Book not found.";

	public static final String RATING_NOT_FOUND_EXCEPTION_MSG = "Rating not found.";

	// Validation messages

	public static final String RATING_MANDATORY = "rating cannot be null or empty";

	public static final String RATING_BOOKID_MANDATORY = "rating.bookId cannot be null or empty";

	public static final String RATING_STARS_MANDATORY = "rating.stars cannot be less than 0 or greater than 5";

}
