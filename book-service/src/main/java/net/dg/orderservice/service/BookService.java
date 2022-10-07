package net.dg.orderservice.service;

import net.dg.orderservice.exceptions.BookNotFoundException;
import net.dg.orderservice.model.Book;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();
    Book findBookById(Long bookId) throws BookNotFoundException;
    Book createBook(Book book);
    void deleteBook(Long bookId);
    void updateBook(Book book, Long bookId);
}
