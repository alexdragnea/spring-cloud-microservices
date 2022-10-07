package net.dg.orderservice.service.impl;

import lombok.AllArgsConstructor;
import net.dg.orderservice.exceptions.BookNotFoundException;
import net.dg.orderservice.model.Book;
import net.dg.orderservice.repository.BookRepository;
import net.dg.orderservice.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBookById(Long bookId) throws BookNotFoundException {

        return bookRepository.findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
    }

    @Override
    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book existingBook = bookRepository.findBookById(bookId).orElseThrow(() -> new BookNotFoundException("Book not found"));
        bookRepository.deleteById(bookId);

    }

    @Override
    public void updateBook(Book book, Long bookId) {

        bookRepository.save(book);
    }
}
