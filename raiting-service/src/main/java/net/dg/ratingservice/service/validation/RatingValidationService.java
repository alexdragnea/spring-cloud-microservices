package net.dg.ratingservice.service.validation;


import net.dg.ratingservice.constants.ServiceConstants;
import net.dg.ratingservice.entity.Rating;
import org.springframework.stereotype.Service;

import javax.validation.ValidationException;
import java.util.Objects;

@Service
public class RatingValidationService {

    public void validate(Rating rating) throws ValidationException {

        if (Objects.isNull(rating)) {
            throw new ValidationException(String.format(ServiceConstants.VALIDATION_FORMAT, ServiceConstants.RATING_MANDATORY));
        }
        if (Objects.isNull(rating.getBookId())) {
            throw new ValidationException(String.format(ServiceConstants.VALIDATION_FORMAT, ServiceConstants.RATING_BOOKID_MANDATORY));
        }
        if (rating.getStars() < 0 || rating.getStars() > 5) {
            throw new ValidationException(String.format(ServiceConstants.VALIDATION_FORMAT, ServiceConstants.RATING_STARS_MANDATORY));
        }
    }
}
