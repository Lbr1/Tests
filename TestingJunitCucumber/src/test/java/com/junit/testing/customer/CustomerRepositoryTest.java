package com.junit.testing.customer;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository underTest;


    @Test
    void itShouldSaveCustomer(){
        //Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Jorge", "763727777");
        //When
        underTest.save(customer);
        //Then
        Optional<Customer> optionalCustomer = underTest.findById(id);

        //assertThat(optionalCustomer)
               // .isNotPresent();

        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying( c -> {
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    // change query to prove that work
    @Test
    void itShouldSelectCustomerByPhoneNumber(){
        //Given
        UUID id = UUID.randomUUID();
        String phoneNumber = "763727777";
        Customer customer = new Customer(id, "Jorge", phoneNumber);

        //When
        underTest.save(customer);

        //Then
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);

        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying( c -> {
                    /*assertThat(c.getId()).isEqualTo(id);
                    assertThat(c.getName()).isEqualTo("Jorge");
                    assertThat(c.getPhoneNumber()).isEqualTo("763727777");*/
                    assertThat(c).isEqualToComparingFieldByField(customer);
                });
    }

    @Test
    void itNotShouldSelectCustomerByPhoneNumberWhenDoesNotExists(){
        //Given
        String phoneNumber = "763727777";

        //When
        Optional<Customer> optionalCustomer = underTest.selectCustomerByPhoneNumber(phoneNumber);

        //Then
        assertThat(optionalCustomer).isNotPresent();
    }



    //Hibernate: drop table customer if exists
    //Hibernate: create table customer (id binary not null, name varchar(255), phone_number varchar(255), primary key (id))

    // @Column tags are not triggered in unit testing
    // So we need add the @DataJpaTest properties = "spring.jpa.properties.javax.persistence.validation.mode=none

    //org.springframework.dao.DataIntegrityViolationException: not-null property references a null or transient value : Customer.name; nested exception is org.hibernate.PropertyValueException: not-null property references a null or transient value : Customer.name
    @Test
    //@Disabled("This test is ignored because its just a @Column example")
    void itShouldNotSaveCustomerWhenNameIsNull(){
        //Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, null, "763727777");
        //When
        underTest.save(customer);
        //Then
        assertThat(underTest.findById(id)).isPresent();
        //assertThat(underTest.findById(id)).isNotPresent();

    }

    @Test
    void itShouldNotSaveCustomerWhenNameIsNulAndThrowException(){
        //Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, null, "763727777");

        //When
        //Then

        assertThatThrownBy( () -> underTest.save(customer))
                .hasMessageContaining("not-null property references a null or transient value")
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    void itShouldNotSaveCustomerWhenPhoneNumberIsNulAndThrowException(){
        //Given
        UUID id = UUID.randomUUID();
        Customer customer = new Customer(id, "Luis", null);

        //When
        //Then

        assertThatThrownBy( () -> underTest.save(customer))
                .hasMessageContaining("not-null property references a null or transient value")
                .isInstanceOf(DataIntegrityViolationException.class);
    }





}