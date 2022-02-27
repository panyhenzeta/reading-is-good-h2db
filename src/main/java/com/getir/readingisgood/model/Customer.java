package com.getir.readingisgood.model;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(uniqueConstraints={@UniqueConstraint(columnNames={"email"})})
public class Customer {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    private String email;

    private String name;

    private String surname;

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
