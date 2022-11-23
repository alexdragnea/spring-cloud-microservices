package net.dg.ratingservice.service.validation;

import net.dg.ratingservice.entity.Rating;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import javax.validation.ValidationException;

import static org.junit.jupiter.api.Assertions.*;

@WebMvcTest(RatingValidationService.class)
class RatingValidationServiceTest {

	@Autowired
	private RatingValidationService ratingValidationService;

	@Test
	public void whenExceptionThrown_thenRuleIsApplied() {
		assertThrows(ValidationException.class, () -> ratingValidationService.validate(new Rating(null, null, 0)));

		assertThrows(ValidationException.class, () -> ratingValidationService.validate(new Rating(null, null, 10)));
	}

}