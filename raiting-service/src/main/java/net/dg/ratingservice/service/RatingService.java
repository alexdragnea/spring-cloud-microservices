package net.dg.ratingservice.service;

import net.dg.bookservice.exceptions.BookNotFoundException;
import net.dg.ratingservice.dto.ResponseTemplate;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.exceptions.RatingNotFoundException;

import java.util.List;

public interface RatingService {

    List<Rating> findRatingsByBookId(Long bookId);

    List<Rating> findAllRatings();

    Rating findRatingById(Long ratingId) throws RatingNotFoundException;

    Rating createRating(Rating rating);

    void deleteRating(Long ratingId);

    Rating updateRating(Rating rating, Long ratingId);

    ResponseTemplate getBookWithRatings(Long bookId) throws BookNotFoundException;
}
