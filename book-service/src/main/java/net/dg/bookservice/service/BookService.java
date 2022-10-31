package net.dg.bookservice.service;

import net.dg.bookservice.exceptions.BookNotFoundException;
import net.dg.bookservice.model.Book;

import java.util.List;

public interface BookService {

	List<Book> findAllBooks();

	Book findBookById(Long bookId) throws BookNotFoundException;

	Book createBook(Book book);

	void deleteBook(Long bookId) throws BookNotFoundException;

	void updateBook(Book book, Long bookId);

}
