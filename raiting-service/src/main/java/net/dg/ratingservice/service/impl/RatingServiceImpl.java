package net.dg.ratingservice.service.impl;

import lombok.AllArgsConstructor;
import net.dg.ratingservice.dto.Book;
import net.dg.ratingservice.dto.ResponseTemplate;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.exceptions.BookNotFoundException;
import net.dg.ratingservice.exceptions.RatingNotFoundException;
import net.dg.ratingservice.feign.consumer.BookRestConsumer;
import net.dg.ratingservice.repository.RatingRepository;
import net.dg.ratingservice.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

	private final RatingRepository ratingRepository;

	private final BookRestConsumer bookRestConsumer;

	@Override
	public List<Rating> findRatingsByBookId(Long bookId) throws RatingNotFoundException {
		return ratingRepository.findRatingsByBookId(bookId).orElseThrow(() -> new RatingNotFoundException());
	}

	@Override
	public List<Rating> findAllRatings() {
		return ratingRepository.findAll();
	}

	@Override
	public Rating findRatingById(Long ratingId) throws RatingNotFoundException {
		return ratingRepository.findRatingById(ratingId).orElseThrow(() -> new RatingNotFoundException());
	}

	@Override
	public Rating createRating(Rating rating) {
		return ratingRepository.save(rating);
	}

	@Override
	public void deleteRating(Long ratingId) {
		Optional<Rating> existingRating = ratingRepository.findRatingById(ratingId);
		if (existingRating.isPresent()) {
			ratingRepository.deleteById(ratingId);
		}
		else
			throw new RatingNotFoundException();
	}

	@Override
	public Rating updateRating(Rating rating, Long ratingId) {
		return ratingRepository.save(rating);
	}

	@Override
	public ResponseTemplate getBookWithRatings(Long bookId) throws BookNotFoundException {
		ResponseTemplate responseTemplate = new ResponseTemplate();
		Optional<List<Rating>> rating = ratingRepository.findRatingsByBookId(bookId);

		Book book = bookRestConsumer.getBookById(bookId);
		if (Objects.isNull(book)) {
			throw new BookNotFoundException();
		}
		else {

			responseTemplate.setBook(book);

			rating.ifPresent(responseTemplate::setRatingList);
		}

		return responseTemplate;
	}

}
