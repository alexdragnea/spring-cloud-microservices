package net.dg.ratingservice.service.impl;

import lombok.AllArgsConstructor;
import net.dg.ratingservice.consumer.BookRestConsumer;
import net.dg.ratingservice.dto.BookDTO;
import net.dg.ratingservice.dto.ResponseTemplate;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.exceptions.RatingNotFoundException;
import net.dg.ratingservice.repository.RatingRepository;
import net.dg.ratingservice.service.RatingService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class RatingServiceImpl implements RatingService {

    private final RatingRepository ratingRepository;
    private final BookRestConsumer bookRestConsumer;

    @Override
    public List<Rating> findRatingsByBookId(Long bookId) throws RatingNotFoundException {
        return ratingRepository.findRatingsByBookId(bookId).orElseThrow(() -> new RatingNotFoundException("Rating not found"));
    }

    @Override
    public List<Rating> findAllRatings() {
        return ratingRepository.findAll();
    }

    @Override
    public Rating findRatingById(Long ratingId) {
        return ratingRepository.findRatingById(ratingId).orElseThrow(() -> new RatingNotFoundException("Rating not found exception"));
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
        } else throw new RatingNotFoundException("Rating not found");
    }

    @Override
    public Rating updateRating(Rating rating, Long ratingId) {
        return ratingRepository.save(rating);
    }

    @Override
    public ResponseTemplate getBookWithRatings(Long bookId) {
        ResponseTemplate responseTemplate = new ResponseTemplate();
        Optional<List<Rating>> rating = ratingRepository.findRatingsByBookId(bookId);


        BookDTO bookDTO = bookRestConsumer.getBookById(bookId);


        responseTemplate.setBookDTO(bookDTO);

        rating.ifPresent(responseTemplate::setRatingList);

        return responseTemplate;
    }
}
