package net.dg.orderservice.controller;

import lombok.AllArgsConstructor;
import net.dg.orderservice.exceptions.BookNotFoundException;
import net.dg.orderservice.model.Book;
import net.dg.orderservice.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@AllArgsConstructor
public class BookController {

    private BookService bookService;

    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.findAllBooks();
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return bookService.createBook(book);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) throws BookNotFoundException {

        try {
            Book existingBook = bookService.findBookById(bookId);
            return new ResponseEntity<>(existingBook, HttpStatus.NOT_FOUND);
        } catch (BookNotFoundException ex) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{bookId}")
    public ResponseEntity<Book> deleteBookById(@PathVariable Long bookId) {

        try {
            bookService.deleteBook(bookId);
        } catch (BookNotFoundException exception) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping(path = "/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book updatedBook) throws BookNotFoundException {

        Book existingBook = bookService.findBookById(bookId);

        try {
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());

            bookService.updateBook(existingBook, bookId);
            return new ResponseEntity<>(existingBook, HttpStatus.OK);
        } catch (BookNotFoundException exception) {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
}