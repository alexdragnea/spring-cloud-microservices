package net.dg.bookservice.service.validation;

import net.dg.bookservice.constants.ServiceConstants;
import net.dg.bookservice.model.Book;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Objects;

@Service
public class BookValidationService {

	public void validate(Book book) throws ValidationException {

		if (Objects.isNull(book)) {
			throw new ValidationException(
					String.format(ServiceConstants.VALIDATION_FORMAT, ServiceConstants.BOOK_MANDATORY));
		}
		if (StringUtils.isEmpty(book.getAuthor()) || Objects.isNull(book.getAuthor())) {
			throw new ValidationException(
					String.format(ServiceConstants.VALIDATION_FORMAT, ServiceConstants.BOOK_AUTHOR_MANDATORY));
		}
		if (StringUtils.isEmpty(book.getTitle()) || Objects.isNull(book.getTitle())) {
			throw new ValidationException(
					String.format(ServiceConstants.VALIDATION_FORMAT, ServiceConstants.BOOK_TITLE_MANDATORY));
		}
	}

}
