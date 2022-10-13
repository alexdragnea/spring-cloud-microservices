package net.dg.ratingservice.repository;

import net.dg.ratingservice.entity.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    Optional<List<Rating>> findRatingsByBookId(Long bookId);

    Optional<Rating> findRatingById(Long ratingId);
}
