package com.origin.library.port;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.origin.library.domain.Book;
import com.origin.library.domain.error.BookNotFoundError;
import com.origin.library.domain.success.Empty;
import com.origin.library.domain.success.Ok;
import com.origin.library.infrastructure.repository.BookRepository;
import com.origin.library.infrastructure.test.Exceptional;
import com.origin.library.infrastructure.test.ShortcutTester;

@SpringBootTest
public class BookControllerTest extends ShortcutTester {
  @Autowired
  private BookController bookController;

  @Autowired
  private BookRepository bookRepository;

  private static final long firstBookId = 1L;

  private Book getBook(long id) throws BookNotFoundError {
    return bookRepository
        .findById(id).orElseThrow(
            () -> new BookNotFoundError().setDetails("id: " + id));
  }

  private Book getFirstBook() throws BookNotFoundError {
    return getBook(firstBookId);
  }

  @BeforeEach
  void contextLoads() throws Exception {
    assertNotNull(bookController);
    assertNotNull(bookRepository);
  }

  @Test
  public void testSearchBooks() {
    SearchBooksQuery query = new SearchBooksQuery();
    query.setName("");
    query.setMine(false);
    query.setPageNum(1);
    query.setPageSize(5);

    Exceptional<Ok<SearchBooksResponse>> e = execute(
        () -> bookController.searchBooks(getFirstUser(), query));
    assertNull(e.getException());
    SearchBooksResponse response = e.getValue().getBody();
    assertNotNull(response);
    assertEquals(query.getPageSize(), response.getRecords().size());
  }

  @Test
  public void testSearchMyBooks() {
    SearchBooksQuery query = new SearchBooksQuery();
    query.setName("");
    query.setMine(true);
    query.setPageNum(1);
    query.setPageSize(5);

    Exceptional<Ok<SearchBooksResponse>> e = execute(
        () -> bookController.searchBooks(getFirstUser(), query));
    assertNull(e.getException());
    SearchBooksResponse response = e.getValue().getBody();
    assertNotNull(response);
  }

  @Test
  public void testBorrowBook() throws Exception {
    Book book = getFirstBook();
    Exceptional<Ok<Empty>> e = execute(
        () -> bookController.borrowBook(getFirstUser(), book.getId()));
    if (book.couldBeBorrowed()) {
      assertNull(e.getException());
    } else {
      assertNotNull(e.getException());
    }
  }

  @Test
  public void testReturnBook() throws Exception {
    Book book = getFirstBook();
    Exceptional<Ok<Empty>> e = execute(
        () -> bookController.returnBook(getFirstUser(), book.getId()));
    if (book.couldBeReturned()) {
      assertNull(e.getException());
    } else {
      assertNotNull(e.getException());
    }
  }
}
