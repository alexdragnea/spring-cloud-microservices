package net.dg.bookservice.repository;

import net.dg.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

	Optional<Book> findBookById(Long bookId);

}
