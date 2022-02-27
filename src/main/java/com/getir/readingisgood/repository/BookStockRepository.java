package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.Book;
import com.getir.readingisgood.model.BookStock;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface BookStockRepository extends PagingAndSortingRepository<BookStock,Long> {

    @Lock(LockModeType.OPTIMISTIC)
    Optional<BookStock> findBookStockByBookId(Long bookId);
}
