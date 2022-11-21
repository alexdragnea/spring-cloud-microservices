package net.dg.ratingservice.constants;

import net.dg.ratingservice.entity.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingObjectMother {

    public static Rating buildRating() {
        return new Rating(1L, 1L, 5);
    }

    public static List<Rating> buildListOfRatings() {
        Rating rating2 = new Rating(2L, 1L, 4);

        List<Rating> ListOfRatings = new ArrayList<>();
        ListOfRatings.add(buildRating());
        ListOfRatings.add(rating2);

        return ListOfRatings;
    }
}
