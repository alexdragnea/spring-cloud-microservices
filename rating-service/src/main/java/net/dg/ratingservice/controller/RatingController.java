package net.dg.ratingservice.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dg.ratingservice.dto.ResponseTemplate;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.exceptions.BookNotFoundException;
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

	@Operation(summary = "Get all Ratings")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Ratings found",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = Rating.class))) }),
			@ApiResponse(responseCode = "404", description = "No Ratings found", content = @Content) })
	@GetMapping
	public ResponseEntity<List<Rating>> getAllRatings() {

		List<Rating> ratings = ratingService.findAllRatings();
		if (!ratings.isEmpty()) {
			return new ResponseEntity<>(ratings, HttpStatus.FOUND);
		}

		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Create a rating")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Rating created",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Rating.class)) }),
			@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@PostMapping
	public ResponseEntity<Rating> createRating(@RequestBody Rating rating) {

		try {
			ratingValidationService.validate(rating);

			Rating ratingToBeSaved = ratingService.createRating(rating);

			return new ResponseEntity<>(ratingToBeSaved, HttpStatus.CREATED);
		}
		catch (ValidationException ex) {
			log.info(ex.getMessage());
			throw new ValidationException(ex.getMessage());
		}
	}

	@Operation(summary = "Get a Rating by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rating found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Rating.class)) }),
			@ApiResponse(responseCode = "404", description = "Rating not found with given id", content = @Content) })
	@GetMapping(path = "/{ratingId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Rating> getRatingById(@PathVariable Long ratingId) {

		try {
			Rating existingRating = ratingService.findRatingById(ratingId);
			return new ResponseEntity<>(existingRating, HttpStatus.FOUND);
		}
		catch (RatingNotFoundException ex) {
			log.info(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Get a Rating by Book id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rating found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Rating.class)) }),
			@ApiResponse(responseCode = "404", description = "Rating not found with given book id",
					content = @Content) })
	@GetMapping(path = "/book/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<List<Rating>> getRatingsByBookId(@PathVariable Long bookId) {

		try {
			List<Rating> existingRating = ratingService.findRatingsByBookId(bookId);
			return new ResponseEntity<>(existingRating, HttpStatus.FOUND);
		}
		catch (RatingNotFoundException ex) {
			log.info(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete a rating")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Rating deleted"),
			@ApiResponse(responseCode = "404", description = "Rating not found with given id", content = @Content) })
	@DeleteMapping(path = "/{ratingId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Rating> deleteRatingById(@PathVariable Long ratingId) {

		try {
			ratingService.deleteRating(ratingId);
		}
		catch (RatingNotFoundException ex) {
			log.info(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Update a rating")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Rating updated successfully",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = Rating.class)) }),
			@ApiResponse(responseCode = "404", description = "No Rating exists with given id", content = @Content) })
	@PatchMapping(path = "/{ratingId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Rating> updateRating(@PathVariable Long ratingId, @RequestBody Rating updatedRating) {

		try {
			Rating existingRating = ratingService.findRatingById(ratingId);
			existingRating.setBookId(updatedRating.getBookId());
			existingRating.setStars(updatedRating.getStars());

			ratingService.updateRating(existingRating, ratingId);
			return new ResponseEntity<>(existingRating, HttpStatus.OK);
		}
		catch (RatingNotFoundException ex) {
			log.info(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

	@Operation(summary = "Get Book by Rating id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Book found",
					content = { @Content(mediaType = "application/json",
							schema = @Schema(implementation = ResponseTemplate.class)) }),
			@ApiResponse(responseCode = "404", description = "Book not found with given Rating id",
					content = @Content) })
	@GetMapping(value = "/{bookId}/book")
	public ResponseEntity<ResponseTemplate> getBookWithRatings(@PathVariable("bookId") Long bookId) {
		try {
			ResponseTemplate responseTemplate = ratingService.getBookWithRatings(bookId);
			return new ResponseEntity<>(responseTemplate, HttpStatus.OK);
		}
		catch (BookNotFoundException ex) {
			log.info(ex.getMessage());
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

}
