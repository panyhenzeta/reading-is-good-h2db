package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.Customer;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends PagingAndSortingRepository<Customer,Long> {
}
