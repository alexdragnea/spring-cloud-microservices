package net.dg.bookservice.controller;

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

	@GetMapping
	public List<Book> getAllBooks() {
		return bookService.findAllBooks();
	}

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
