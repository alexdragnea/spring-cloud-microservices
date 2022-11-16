package net.dg.bookservice.constants;

import net.dg.bookservice.model.Book;

import java.util.ArrayList;
import java.util.List;

public class BookObjectMother {

	public static Book buildBook() {
		return new Book(1L, "book1", "Laurentiu");
	}

	public static List<Book> buildListOfBooks() {
		Book book2 = new Book(2L, "book2", "book2");

		List<Book> listOfBooks = new ArrayList<>();
		listOfBooks.add(buildBook());
		listOfBooks.add(book2);

		return listOfBooks;
	}

}
