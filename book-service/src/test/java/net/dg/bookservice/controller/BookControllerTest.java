package net.dg.bookservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.dg.bookservice.constants.BookObjectMother;
import net.dg.bookservice.exceptions.BookNotFoundException;
import net.dg.bookservice.model.Book;
import net.dg.bookservice.repository.BookRepository;
import net.dg.bookservice.service.BookService;
import net.dg.bookservice.service.validation.BookValidationService;
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

@WebMvcTest(BookController.class)
class BookControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Autowired
	ObjectMapper objectMapper;

	@MockBean
	BookService bookService;

	@MockBean
	BookRepository bookRepository;

	@MockBean
	BookValidationService bookValidationService;

	@Test
	void testCreateBookThenReturnisCreatedStatusCode() throws Exception {

		Book book = BookObjectMother.buildBook();

		mockMvc.perform(MockMvcRequestBuilders.post("/book").content(objectMapper.writeValueAsString(book))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isCreated());
	}

	@Test
	void testRetrieveAllBooks() throws Exception {
		List<Book> books = BookObjectMother.buildListOfBooks();

		when(bookService.findAllBooks()).thenReturn(books);

		mockMvc.perform(MockMvcRequestBuilders.get("/book").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$[0].id").isNotEmpty())
				.andExpect(jsonPath("$[0].author").isNotEmpty()).andExpect(jsonPath("$[0].title").isNotEmpty())
				.andExpect(jsonPath("$[0].author").isNotEmpty()).andExpect(jsonPath("$[1].id").isNotEmpty())
				.andExpect(jsonPath("$[1].author").isNotEmpty()).andExpect(jsonPath("$[1].title").isNotEmpty())
				.andExpect(jsonPath("$[1].author").isNotEmpty());
	}

	@Test
	void testRetrieveBookById() throws Exception {
		when(bookService.findBookById(1L)).thenReturn(BookObjectMother.buildBook());

		mockMvc.perform(MockMvcRequestBuilders.get("/book/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(jsonPath("$.id").isNotEmpty())
				.andExpect(jsonPath("$.author").isNotEmpty()).andExpect(jsonPath("$.title").isNotEmpty())
				.andExpect(jsonPath("$.author").isNotEmpty());
	}

	@Test
	void testDeleteBookById() throws Exception {

		mockMvc.perform(MockMvcRequestBuilders.delete("/book/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());

	}

	@Test
	void testFindBookShouldReturnHttpStatusCode404() throws Exception {
		when(bookService.findBookById(1L)).thenThrow(new BookNotFoundException());

		mockMvc.perform(MockMvcRequestBuilders.get("/book/1").contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isNotFound());
	}

}