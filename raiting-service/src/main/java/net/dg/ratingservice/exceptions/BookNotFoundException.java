package net.dg.ratingservice.exceptions;

import net.dg.ratingservice.constants.ServiceConstants;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super(ServiceConstants.BOOK_NOT_FOUND_EXCEPTION_MSG);
    }
}