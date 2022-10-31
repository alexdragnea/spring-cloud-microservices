package net.dg.bookservice.service.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.dg.bookservice.exceptions.BookNotFoundException;
import net.dg.bookservice.model.Book;
import net.dg.bookservice.repository.BookRepository;
import net.dg.bookservice.service.BookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;

	@Override
	public List<Book> findAllBooks() {
		return bookRepository.findAll();
	}

	@Override
	public Book findBookById(Long bookId) throws BookNotFoundException {

		return bookRepository.findBookById(bookId).orElseThrow(() -> new BookNotFoundException());
	}

	@Override
	public Book createBook(Book book) {
		return bookRepository.save(book);
	}

	@Override
	public void deleteBook(Long bookId) {
		Optional<Book> existingBook = bookRepository.findBookById(bookId);
		if (existingBook.isPresent()) {
			bookRepository.deleteById(bookId);
		}
		else
			throw new BookNotFoundException();

	}

	@Override
	public void updateBook(Book book, Long bookId) {

		bookRepository.save(book);
	}

}
