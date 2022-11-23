package net.dg.ratingservice.service.validation;

import net.dg.ratingservice.entity.Rating;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.assertThrows;

@WebMvcTest(RatingValidationService.class)
class RatingValidationServiceTest {

	@Autowired
	RatingValidationService ratingValidationService;

	@Test
	void whenExceptionThrown_thenRuleIsApplied() {
		assertThrows(ValidationException.class, () -> ratingValidationService.validate(new Rating(null, null, 0)));

		assertThrows(ValidationException.class, () -> ratingValidationService.validate(new Rating(null, null, 10)));
	}

}