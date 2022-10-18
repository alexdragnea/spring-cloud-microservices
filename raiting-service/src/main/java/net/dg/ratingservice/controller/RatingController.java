package net.dg.ratingservice.controller;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dg.bookservice.exceptions.BookNotFoundException;
import net.dg.ratingservice.dto.ResponseTemplate;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.exceptions.RatingNotFoundException;
import net.dg.ratingservice.service.RatingService;
import net.dg.ratingservice.service.validation.RatingValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/rating")
@AllArgsConstructor
@Slf4j
public class RatingController {

    private final RatingService ratingService;
    private final RatingValidationService ratingValidationService;

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.findAllRatings();
    }

    @PostMapping
    public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {

        try {
            ratingValidationService.validate(rating);

            Rating ratingToBeSaved = ratingService.createRating(rating);

            return new ResponseEntity<>(ratingToBeSaved, HttpStatus.CREATED);
        } catch (ValidationException ex) {
            log.info(ex.getMessage());
            throw new ValidationException(ex.getMessage());
        }
    }

    @GetMapping(path = "/{ratingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rating> getRatingById(@PathVariable Long ratingId) {

        try {
            Rating existingRating = ratingService.findRatingById(ratingId);
            return new ResponseEntity<>(existingRating, HttpStatus.FOUND);
        } catch (RatingNotFoundException ex) {
            log.info(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(path = "/book/{bookId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<Rating>> getRatingsByBookId(@PathVariable Long bookId) {

        try {
            List<Rating> existingRating = ratingService.findRatingsByBookId(bookId);
            return new ResponseEntity<>(existingRating, HttpStatus.FOUND);
        } catch (RatingNotFoundException ex) {
            log.info(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{ratingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rating> deleteRatingById(@PathVariable Long ratingId) {

        try {
            ratingService.deleteRating(ratingId);
        } catch (RatingNotFoundException ex) {
            log.info(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/{ratingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId, @RequestBody Rating updatedRating) {

        try {
            Rating existingRating = ratingService.findRatingById(ratingId);
            existingRating.setBookId(updatedRating.getBookId());
            existingRating.setStars(updatedRating.getStars());

            ratingService.updateRating(existingRating, ratingId);
            return new ResponseEntity<>(existingRating, HttpStatus.OK);
        } catch (RatingNotFoundException ex) {
            log.info(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/{bookId}/book")
    public ResponseEntity<ResponseTemplate> getBookWithRatings(@PathVariable("bookId") Long bookId) {
        try {
            ResponseTemplate responseTemplate = ratingService.getBookWithRatings(bookId);
            return new ResponseEntity<>(responseTemplate, HttpStatus.OK);
        } catch (BookNotFoundException ex) {
            log.info(ex.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
