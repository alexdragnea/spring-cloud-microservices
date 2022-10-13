package net.dg.ratingservice.controller;

import lombok.AllArgsConstructor;
import net.dg.ratingservice.dto.ResponseTemplate;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.exceptions.RatingNotFoundException;
import net.dg.ratingservice.service.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rating")
@AllArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @GetMapping
    public List<Rating> getAllRatings() {
        return ratingService.findAllRatings();
    }

    @PostMapping
    public Rating createBook(@RequestBody Rating rating) {
        return ratingService.createRating(rating);
    }

    @GetMapping(path = "/{ratingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rating> getRatingById(@PathVariable Long ratingId) throws RatingNotFoundException {

        try {
            Rating existingRating = ratingService.findRatingById(ratingId);
            return new ResponseEntity<>(existingRating, HttpStatus.NOT_FOUND);
        } catch (RatingNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping(path = "/{ratingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rating> deleteRatingById(@PathVariable Long ratingId) {

        try {
            ratingService.deleteRating(ratingId);
        } catch (RatingNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/{ratingId}", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId, @RequestBody Rating updatedRating) throws RatingNotFoundException {

        try {
            Rating existingRating = ratingService.findRatingById(ratingId);
            existingRating.setBookId(updatedRating.getBookId());
            existingRating.setStars(updatedRating.getStars());

            ratingService.updateRating(existingRating, ratingId);
            return new ResponseEntity<>(existingRating, HttpStatus.OK);
        } catch (RatingNotFoundException exception) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(value = "/{bookId}/book")
    public ResponseTemplate getBookWithRatings(@PathVariable("bookId") Long bookId) {
        return ratingService.getBookWithRatings(bookId);
    }

}
