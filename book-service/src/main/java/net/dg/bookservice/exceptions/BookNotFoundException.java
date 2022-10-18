package net.dg.bookservice.exceptions;

import net.dg.bookservice.constants.ServiceConstants;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException() {
        super(ServiceConstants.BOOK_NOT_FOUND_EXCEPTION);
    }
}