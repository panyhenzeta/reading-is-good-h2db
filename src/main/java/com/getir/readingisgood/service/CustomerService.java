package com.getir.readingisgood.service;

import com.getir.readingisgood.exception.CustomValidationException;
import com.getir.readingisgood.model.Customer;
import com.getir.readingisgood.model.dto.CustomerDTO;
import com.getir.readingisgood.model.response.SuccessResponse;
import com.getir.readingisgood.repository.CustomerRepository;
import com.getir.readingisgood.util.EmailUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public Customer createCustomer(CustomerDTO customer) {
        if (!EmailUtil.isValidMail(customer.getEmail())){
            throw new CustomValidationException("error.emailIsNotValid");
        }
        Customer newCustomer = customer.convertCustomer();
        return customerRepository.save(newCustomer);
    }

    public List<Customer> findAll(Pageable pageable){
        Page<Customer> customerPage = customerRepository.findAll(pageable);
        return customerPage.getContent();
    }

    public Customer findCustomerById(Long customerId) {
        Optional<Customer> customer = customerRepository.findById(customerId);
        if (customer.isPresent()) {
            return customer.get();
        } else {
            throw new CustomValidationException("error.customerNotFound");
        }
    }
}
