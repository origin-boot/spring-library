package com.origin.library.infrastructure.repository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.origin.library.domain.Borrow;
import com.origin.library.domain.Book;
import com.origin.library.domain.Page;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

interface AdvancedBorrowRepository {
	Page<Borrow> searchMyBorrows(long userId, String keyword, int offset, int limit);
}

public interface BorrowRepository extends JpaRepository<Borrow, Long>, AdvancedBorrowRepository {
}

@Service
class AdvancedBorrowRepositoryImpl implements AdvancedBorrowRepository {
	@Autowired
	EntityManager em;

	@Override
	public Page<Borrow> searchMyBorrows(long userId, String keyword, int offset, int limit) {
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Borrow> q = cb.createQuery(Borrow.class);
		Root<Borrow> a = q.from(Borrow.class);
		Join<Borrow, Book> b = a.join("book", JoinType.INNER);

		Predicate[] w = searchBorrowsPredicates(cb, a, b, userId, keyword);
		q = q.select(a)
				.where(w)
				.orderBy(cb.desc(a.get("id")));

		List<Borrow> Borrows = em.createQuery(q)
				.setFirstResult(offset)
				.setMaxResults(limit)
				.getResultList();

		// FIXME: too many copies
		CriteriaQuery<Long> q2 = cb.createQuery(Long.class);
		Root<Borrow> a2 = q2.from(Borrow.class);
		Join<Borrow, Book> b2 = a2.join("book", JoinType.INNER);
		Predicate[] w2 = searchBorrowsPredicates(cb, a2, b2, userId, keyword);
		q2 = q2.select(cb.count(a2))
				.where(w2);
		long count = em.createQuery(q2).getSingleResult();

		return new Page<Borrow>(Borrows, count);
	}

	Predicate[] searchBorrowsPredicates(CriteriaBuilder cb, Root<Borrow> a, Join<Borrow, Book> b,
			long userId, String keyword) {

		List<Predicate> predicates = new ArrayList<>();
		if (userId > 0) {
			predicates.add(cb.equal(a.get("userId"), userId));
		}
		if (keyword != null && !keyword.isEmpty()) {
			predicates.add(cb.like(b.get("name"), "%" + keyword + "%"));
		}
		return predicates.toArray(new Predicate[predicates.size()]);
	}
}