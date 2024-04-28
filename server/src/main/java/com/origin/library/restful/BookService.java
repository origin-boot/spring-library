package com.origin.library.restful;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.origin.library.domain.Book;
import com.origin.library.domain.Borrow;
import com.origin.library.domain.User;
import com.origin.library.domain.error.BookNotFoundError;
import com.origin.library.domain.error.RequestForbiddenError;
import com.origin.library.infrastructure.repository.BookRepository;
import com.origin.library.infrastructure.repository.BorrowRepository;
import com.origin.library.infrastructure.utils.TimeUtils;

import java.util.List;

@Service
public class BookService {

	BookRepository bookRepository;
	BorrowRepository borrowRepository;

	public BookService(final BookRepository bookRepository, final BorrowRepository borrowRepository) {
		this.bookRepository = bookRepository;
		this.borrowRepository = borrowRepository;
	}

	public Book getBookById(Long id) throws BookNotFoundError {
		return bookRepository.findById(id).orElseThrow(
				() -> new BookNotFoundError().setDetails("id: " + id));
	}

	public SearchBooksResponse searchBooks(User user, SearchBooksQuery query) {

		List<BookResource> totalRecords = List.of(
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource());

		Pageable pageable = PageRequest.of(query.getPageNum() - 1, query.getPageSize());

		List<BookResource> pageRecords = totalRecords.subList(
				(int) pageable.getOffset(),
				(int) Math.min(pageable.getOffset() + pageable.getPageSize(), totalRecords.size()));

		SearchBooksResponse response = new SearchBooksResponse(pageRecords, totalRecords.size());

		return response;
	}

	public SearchBooksResponse searchMyBooks(User user, SearchBooksQuery query) {

		List<BookResource> totalRecords = List.of(
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource(),
				new BookResource());

		Pageable pageable = PageRequest.of(query.getPageNum() - 1, query.getPageSize());

		List<BookResource> pageRecords = totalRecords.subList(
				(int) pageable.getOffset(),
				(int) Math.min(pageable.getOffset() + pageable.getPageSize(), totalRecords.size()));

		SearchBooksResponse response = new SearchBooksResponse(pageRecords, totalRecords.size());

		return response;
	}

	public void borrowBook(User user, Long bookId) throws BookNotFoundError, RequestForbiddenError {
		Book book = getBookById(bookId);
		if (!book.couldBeBorrowed()) {
			throw new RequestForbiddenError().setDetails("Book is not available for borrowing");
		}

		book.setUserId(user.getId());
		book.setReturnTime(0);
		book.setBorrowTime(TimeUtils.getUnixTimestamp());
		book = bookRepository.save(book);

		Borrow borrow = new Borrow();
		borrow.setUserId(user.getId());
		borrow.setBookId(book.getId());
		borrow.setBorrowTime(book.getBorrowTime());
		borrow.setReturnTime(book.getReturnTime());
		borrow.setCreateTime(TimeUtils.getUnixTimestamp());
		borrow = borrowRepository.save(borrow);

		return;
	}

	public void returnBook(User user, Long bookId) throws BookNotFoundError, RequestForbiddenError {
		Book book = getBookById(bookId);

		if (!book.couldBeReturned()) {
			throw new RequestForbiddenError().setDetails("Book is not available for returning");
		}

		if (!book.isBorrowedBy(user.getId())) {
			throw new RequestForbiddenError().setDetails("Book is not borrowed by the user");
		}

		book.setReturnTime(TimeUtils.getUnixTimestamp());
		book = bookRepository.save(book);

		Borrow borrow = new Borrow();
		borrow.setUserId(user.getId());
		borrow.setBookId(book.getId());
		borrow.setBorrowTime(book.getBorrowTime());
		borrow.setReturnTime(book.getReturnTime());
		borrow.setCreateTime(TimeUtils.getUnixTimestamp());
		borrow = borrowRepository.save(borrow);

		return;
	}
}
