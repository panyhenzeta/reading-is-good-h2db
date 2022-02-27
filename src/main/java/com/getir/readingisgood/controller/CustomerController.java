package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.service.CustomerService;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CustomerController {

    private final CustomerService customerService;

    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @PostMapping("/customers")
    public ResponseEntity<SuccessResponse<Customer>> createCustomer(@RequestBody CustomerDTO customer) {
        Customer savedCustomer = customerService.createCustomer(customer);
        return  ResponseEntity.ok().body(new SuccessResponse<>(savedCustomer));
    }

    @GetMapping("/customers")
    public ResponseEntity<SuccessResponse<List<Customer>>> listCustomers(Pageable pageable) {
        List<Customer> customerResponse = customerService.findAll(pageable);
        return ResponseEntity.ok().body(new SuccessResponse<>(customerResponse));
    }
}
