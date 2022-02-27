package com.getir.readingisgood.service;

import com.getir.readingisgood.model.CustomerOrder;
import com.getir.readingisgood.model.dto.MonthlyStatisticsDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class StatisticsService {
    private final OrderService orderService;

    public StatisticsService(OrderService orderService) {
        this.orderService = orderService;
    }

    public List<MonthlyStatisticsDTO> getMonthlyStatistics(){
        Map<Integer, List<CustomerOrder>> grouppedList = orderService.findAll()
                .stream()
                .collect(Collectors.groupingBy(customerOrder -> Date.from(customerOrder.getOrderDate()).getMonth()));

        List<MonthlyStatisticsDTO> monthlyStatisticsDTOS = new ArrayList<>();
        grouppedList.forEach((month, customerOrders) -> {
            AtomicReference<Long> totalBook = new AtomicReference<>(0L);
            AtomicReference<Double> totalPrice = new AtomicReference<>(0.0);
            customerOrders.forEach(customerOrder -> {
                totalBook.updateAndGet(v -> v + customerOrder.getOrderBooks().stream().mapToLong(value -> value.getQuantity()).sum());
                totalPrice.updateAndGet(v -> v + customerOrder.getTotalPrice());
            });
            MonthlyStatisticsDTO monthlyStatisticsDTO =
                    new MonthlyStatisticsDTO(month.toString(), customerOrders.size(), totalBook.get(), totalPrice.get());
            monthlyStatisticsDTOS.add(monthlyStatisticsDTO);
        });
        return monthlyStatisticsDTOS;
    }
}
