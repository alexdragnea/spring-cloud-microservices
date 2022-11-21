package net.dg.ratingservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dg.ratingservice.constants.RatingObjectMother;
import net.dg.ratingservice.entity.Rating;
import net.dg.ratingservice.repository.RatingRepository;
import net.dg.ratingservice.service.RatingService;
import net.dg.ratingservice.service.validation.RatingValidationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RatingController.class)
class RatingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    RatingService ratingService;

    @MockBean
    RatingRepository ratingRepository;

    @MockBean
    RatingValidationService ratingValidationService;
    @BeforeEach
    /*public void createRating (){
        Rating rating = RatingObjectMother.buildRating();
    }*/

    @Test
    void testCreateRatingThenReturnCreatedRatingCode () throws Exception {
        Rating rating = RatingObjectMother.buildRating();

        mockMvc.perform(MockMvcRequestBuilders.post("/rating").content(objectMapper.writeValueAsString(rating))
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
    }

    @Test
    void testRetrieveListOfRatings ()throws Exception {


        List<Rating> ratings = RatingObjectMother.buildListOfRatings();

        mockMvc.perform(MockMvcRequestBuilders.get("/rating").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

    }

    /*@Test
    void testRetrieveRatingById () throws Exception {
        Rating rating = RatingObjectMother.buildRating();

        mockMvc.perform(MockMvcRequestBuilders.get("/{ratingId}").param("ratingId", "1L").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testRetrieveRatingByBookId () throws Exception {
        Rating rating = RatingObjectMother.buildRating();

        mockMvc.perform(MockMvcRequestBuilders.get("/book/{bookId}").param("bookId", "1L").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteRatingById () throws Exception {
        Rating rating = RatingObjectMother.buildRating();

        mockMvc.perform(MockMvcRequestBuilders.delete("/{ratingId}").param("ratingId", "1L").contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk());
    }*/


}
