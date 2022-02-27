package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.dto.CustomerOrderRequestDTO;
import com.getir.readingisgood.model.dto.CustomerOrderResponseDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/api")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/orders")
    public ResponseEntity<SuccessResponse<CustomerOrderResponseDTO>> createOrder(@Valid @RequestBody CustomerOrderRequestDTO order) {
        CustomerOrderResponseDTO response = orderService.createOrder(order);
        return ResponseEntity.ok().body(new SuccessResponse<>(response));
    }

    @GetMapping("/orders/{id}")
    public ResponseEntity<SuccessResponse<CustomerOrderResponseDTO>> getOrder(@PathVariable Long id) {
        CustomerOrderResponseDTO response = orderService.getOrderById(id);
        return ResponseEntity.ok().body(new SuccessResponse<>(response));
    }

    @GetMapping("/orders")
    public ResponseEntity<SuccessResponse<List<CustomerOrderResponseDTO>>> listOrdersByStartDateAndEndDate(@RequestParam Instant startDate, @RequestParam Instant endDate) {
        List<CustomerOrderResponseDTO> response = orderService.listOrdersByStartDateAndEndDate(startDate, endDate);
        return ResponseEntity.ok().body(new SuccessResponse<>(response));
    }
}
