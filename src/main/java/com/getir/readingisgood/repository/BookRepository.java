package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book,Long> {
}
