package net.dg.bookservice.service.validation;

import net.dg.bookservice.model.Book;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(BookValidationService.class)
class BookValidationServiceTest {

	@Autowired
	BookValidationService bookValidationService;

	@Test
	void whenExceptionThrown_thenRuleIsApplied() {
		assertThrows(ValidationException.class, () -> bookValidationService.validate(new Book(1L, null, null)));

		assertThrows(ValidationException.class,
				() -> bookValidationService.validate(new Book(1L, StringUtils.EMPTY, StringUtils.EMPTY)));
	}

}