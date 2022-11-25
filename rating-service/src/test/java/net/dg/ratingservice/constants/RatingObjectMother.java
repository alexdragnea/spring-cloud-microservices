package net.dg.ratingservice.constants;

import net.dg.ratingservice.dto.Book;
import net.dg.ratingservice.dto.ResponseTemplate;
import net.dg.ratingservice.entity.Rating;

import java.util.ArrayList;
import java.util.List;

public class RatingObjectMother {

	public static Rating buildRating() {
		return new Rating(1L, 1L, 5);
	}

	public static List<Rating> buildListOfRatings() {
		Rating rating2 = new Rating(2L, 2L, 5);

		List<Rating> listOfBooks = new ArrayList<>();
		listOfBooks.add(buildRating());
		listOfBooks.add(rating2);

		return listOfBooks;
	}

	public static ResponseTemplate buildResponseTemplate() {

		ResponseTemplate responseTemplate = new ResponseTemplate();
		responseTemplate.setBook(new Book(1L, "laurentiu splica", "spring starts here"));

		return responseTemplate;
	}

}
