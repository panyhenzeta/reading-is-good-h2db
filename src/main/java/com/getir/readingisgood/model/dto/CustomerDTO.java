package com.getir.readingisgood.model.dto;

import com.getir.readingisgood.model.Customer;

import javax.validation.constraints.NotNull;

public class CustomerDTO {

    @NotNull
    private String email;
    private String name;
    private String surname;

    public CustomerDTO(){}

    public CustomerDTO(String email, String name, String surname ){
        this.email = email;
        this.name = name;
        this.surname = surname;
    }


    public Customer convertCustomer(){
        Customer customer = new Customer();
        customer.setEmail(this.email);
        customer.setName(this.name);
        customer.setSurname(this.surname);
        return customer;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
