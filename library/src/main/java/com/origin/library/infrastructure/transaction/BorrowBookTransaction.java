package com.origin.library.infrastructure.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.origin.library.domain.Book;
import com.origin.library.domain.Borrow;
import com.origin.library.domain.User;
import com.origin.library.infrastructure.repository.BookRepository;
import com.origin.library.infrastructure.repository.BorrowRepository;
import com.origin.library.infrastructure.util.TimeUtil;

@Service
public class BorrowBookTransaction {

  @Autowired
  private BookRepository bookRepository;

  @Autowired
  private BorrowRepository borrowRepository;

  @Transactional
  public void execute(User user, Book book) {
    book.setUserId(user.getId());
    book.setReturnTime(0);
    book.setBorrowTime(TimeUtil.getUnixTimestamp());
    book = bookRepository.save(book);

    Borrow borrow = new Borrow();
    borrow.setUserId(user.getId());
    borrow.setBookId(book.getId());
    borrow.setBorrowTime(book.getBorrowTime());
    borrow.setReturnTime(book.getReturnTime());
    borrow.setCreateTime(TimeUtil.getUnixTimestamp());
    borrow = borrowRepository.save(borrow);
  }
}
