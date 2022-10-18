package net.dg.ratingservice.feign.consumer;

import net.dg.bookservice.exceptions.BookNotFoundException;
import net.dg.ratingservice.dto.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "BOOK-SERVICE", decode404 = true)
public interface BookRestConsumer {

    @GetMapping(value = "/book/{bookId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    Book getBookById(@PathVariable Long bookId) throws BookNotFoundException;

}
