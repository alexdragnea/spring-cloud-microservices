package net.dg.bookservice.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dg.bookservice.exceptions.BookNotFoundException;
import net.dg.bookservice.model.Book;
import net.dg.bookservice.service.BookService;
import net.dg.bookservice.service.validation.BookValidationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.ValidationException;
import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
@Slf4j
public class BookController {

	private BookService bookService;

	private BookValidationService bookValidationService;

	@Operation(summary = "Get all Books")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "302", description = "Books found",
					content = { @Content(mediaType = "application/json",
							array = @ArraySchema(schema = @Schema(implementation = Book.class))) }),
			@ApiResponse(responseCode = "404", description = "No Books found", content = @Content) })
	@GetMapping
	public ResponseEntity<List<Book>> getAllBooks() {

		List<Book> books = bookService.findAllBooks();
		if (!books.isEmpty()) {
			return new ResponseEntity<>(books, HttpStatus.FOUND);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@Operation(summary = "Create a book")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "201", description = "Book created",
							content = { @Content(mediaType = "application/json",
									schema = @Schema(implementation = Book.class)) }),
					@ApiResponse(responseCode = "400", description = "Bad request", content = @Content) })
	@PostMapping
	public ResponseEntity<Book> createBook(@RequestBody Book book) {

		try {
			bookValidationService.validate(book);

			Book bookToBeSaved = bookService.createBook(book);

			return new ResponseEntity<>(bookToBeSaved, HttpStatus.CREATED);
		}
		catch (ValidationException exception) {
			throw new ValidationException(exception.getMessage());
		}
	}

	@Operation(summary = "Get a Book by id")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Book found",
							content = { @Content(mediaType = "application/json",
									schema = @Schema(implementation = Book.class)) }),
					@ApiResponse(responseCode = "404", description = "Book not found with given id",
							content = @Content) })
	@GetMapping(path = "/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Book> getBookById(@PathVariable Long bookId) throws BookNotFoundException {

		try {
			Book existingBook = bookService.findBookById(bookId);
			return new ResponseEntity<>(existingBook, HttpStatus.OK);
		}
		catch (BookNotFoundException ex) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete a book")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Book deleted"),
			@ApiResponse(responseCode = "404", description = "Book not found with given id", content = @Content) })
	@DeleteMapping(path = "/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Book> deleteBookById(@PathVariable Long bookId) {

		try {
			bookService.deleteBook(bookId);
		}
		catch (BookNotFoundException exception) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}

	@Operation(summary = "Update a book")
	@ApiResponses(
			value = {
					@ApiResponse(responseCode = "200", description = "Book updated successfully",
							content = { @Content(mediaType = "application/json",
									schema = @Schema(implementation = Book.class)) }),
					@ApiResponse(responseCode = "404", description = "No Book exists with given id",
							content = @Content) })
	@PatchMapping(path = "/{bookId}", produces = { MediaType.APPLICATION_JSON_VALUE })
	public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook)
			throws BookNotFoundException {

		try {
			Book existingBook = bookService.findBookById(bookId);

			bookValidationService.validate(updatedBook);
			existingBook.setTitle(updatedBook.getTitle());
			existingBook.setAuthor(updatedBook.getAuthor());

			bookService.updateBook(existingBook, bookId);
			return new ResponseEntity<>(existingBook, HttpStatus.OK);
		}
		catch (BookNotFoundException exception) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}

	}

}
