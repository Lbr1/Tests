package com.arun.cucumber.hello.bdd.stepdefs;



import com.junit.testing.customer.Customer;
import com.junit.testing.customer.CustomerRepository;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import cucumber.api.java8.En;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;


public class CustomerSteps implements En{


    private Customer customer;
    @Autowired
    private CustomerRepository customerRepository;

    public CustomerSteps(){
        Given("There is a new customer with name {string} and number {string}", (String name, String number) -> {
            UUID id = UUID.randomUUID();
            customer = new Customer(id, name, number);
        });

        When("We want to add the customer", () -> {
            customerRepository.save(customer);
        });

        Then("You can Get the new customer", () -> {
            assertAll("Verify that customer exists and have the same fields",
                    () -> assertEquals(true, customerRepository.selectCustomerByPhoneNumber(customer.getPhoneNumber()).isPresent()),
                    () ->  assertEquals("Joao", customerRepository.selectCustomerByPhoneNumber(customer.getPhoneNumber()).get().getName()));
        });
    }
}
