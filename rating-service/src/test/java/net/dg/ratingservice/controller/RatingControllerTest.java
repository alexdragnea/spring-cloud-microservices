package net.dg.ratingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dg.ratingservice.constants.RatingObjectMother;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.exceptions.RatingNotFoundException;
import net.dg.ratingservice.repository.RatingRepository;
import net.dg.ratingservice.service.impl.RatingServiceImpl;
import net.dg.ratingservice.service.validation.RatingValidationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
class RatingControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	RatingServiceImpl ratingService;

	@MockBean
	RatingRepository ratingRepository;

	@MockBean
	RatingValidationService ratingValidationService;

	@Test
	void testCreateRatingThenReturnisCreatedStatusCode() throws Exception {

		Rating rating = RatingObjectMother.buildRating();

		mockMvc.perform(MockMvcRequestBuilders.post("/rating").content(objectMapper.writeValueAsString(rating))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void testRetrieveAllRatings() throws Exception {
		List<Rating> ratings = RatingObjectMother.buildListOfRatings();

		when(ratingService.findAllRatings()).thenReturn(ratings);

		mockMvc.perform(MockMvcRequestBuilders.get("/rating").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound()).andExpect(jsonPath("$[0].id").isNotEmpty())
				.andExpect(jsonPath("$[0].bookId").isNotEmpty()).andExpect(jsonPath("$[0].stars").isNotEmpty())
				.andExpect(jsonPath("$[1].id").isNotEmpty()).andExpect(jsonPath("$[1].bookId").isNotEmpty())
				.andExpect(jsonPath("$[1].stars").isNotEmpty());

	}

	@Test
	void testRetrieveRatingById() throws Exception {
		when(ratingService.findRatingById(1L)).thenReturn(RatingObjectMother.buildRating());

		mockMvc.perform(MockMvcRequestBuilders.get("/rating/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound()).andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.bookId").isNotEmpty()).andExpect(jsonPath("$.stars").isNotEmpty());
	}

	@Test
	void testDeleteRatingById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/rating/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	void testFindRatingShouldReturnHttpStatusCode404() throws Exception {
		when(ratingService.findRatingById(1L)).thenThrow(new RatingNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders.get("/rating/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

	@Test
	void testRetrieveRatingsByBookId() throws Exception {
		when(ratingService.findRatingsByBookId(1L)).thenReturn(RatingObjectMother.buildListOfRatings());

		mockMvc.perform(MockMvcRequestBuilders.get("/rating/book/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isFound()).andExpect(jsonPath("$[0].id").isNotEmpty())
				.andExpect(jsonPath("$[0].bookId").isNotEmpty()).andExpect(jsonPath("$[0].stars").isNotEmpty())
				.andExpect(jsonPath("$[1].id").isNotEmpty()).andExpect(jsonPath("$[1].bookId").isNotEmpty())
				.andExpect(jsonPath("$[1].stars").isNotEmpty());
	}

	@Test
	void testRetrieveBookWithRatings() throws Exception {
		when(ratingService.getBookWithRatings(1L)).thenReturn(RatingObjectMother.buildResponseTemplate());

		mockMvc.perform(MockMvcRequestBuilders.get("/rating/1/book").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
	}

}
