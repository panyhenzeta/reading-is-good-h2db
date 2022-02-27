package com.getir.readingisgood.repository;

import com.getir.readingisgood.model.CustomerOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

@Repository
public interface OrderRepository extends PagingAndSortingRepository<CustomerOrder, Long> {

    List<CustomerOrder> findByOrderDateBetween(Instant startDate, Instant endDate);
    List<CustomerOrder> findAll();
}
