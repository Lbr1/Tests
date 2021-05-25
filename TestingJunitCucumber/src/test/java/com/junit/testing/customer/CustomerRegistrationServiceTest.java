package com.junit.testing.customer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

public class CustomerRegistrationServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    //private CustomerRepository customerRepository = mock(CustomerRepository.class);

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;

    private CustomerRegistrationService underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new CustomerRegistrationService(customerRepository);
    }

    @Test
    void itShouldSaveNewCustomer(){
        //Given
        String phoneNumber = "937463526";
        Customer customer = new Customer(UUID.randomUUID(), "Luis", phoneNumber);

        // a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        //No customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.empty());

        //when
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue =  customerArgumentCaptor.getValue();
        //can use isEqualTo
        assertThat(customerArgumentCaptorValue).isEqualToComparingFieldByField(customer);
    }

    @Test
    void itShouldNotSaveCustomerWhenCustomerExists() {
        //Given
        String phoneNumber = "937463526";
        Customer customer = new Customer(UUID.randomUUID(), "Luis", phoneNumber);

        // a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // an existing customer is returned
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.of(customer));

        //when
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should(never()).save(any());
        //then(customerRepository).should().selectCustomerByPhoneNumber(phoneNumber);
        //then(customerRepository).shouldHaveNoMoreInteractions();
    }

    @Test
    void willThrowWhenPhoneNumberIsTaken() {
        //Given
        String phoneNumber = "937463526";
        Customer customer = new Customer(UUID.randomUUID(), "Luis", phoneNumber);
        Customer customerTwo = new Customer(UUID.randomUUID(), "Luisa", phoneNumber);

        // a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        // an existing customer is returned
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.of(customerTwo));

        // when
        // then
        assertThatThrownBy(() -> underTest.registerNewCustomer(request))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining(String.format("phone number [%s] is taken", phoneNumber));

        // finally
        then(customerRepository).should(never()).save(any(Customer.class));
        //verify(studentRepository, never()).save(any());
    }

    @Test
    void itShouldSaveNewCustomerWhenIdIsNull(){
        //Given
        String phoneNumber = "937463526";
        Customer customer = new Customer(null, "Luis", phoneNumber);

        // a request
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(customer);

        //No customer with phone number passed
        given(customerRepository.selectCustomerByPhoneNumber(phoneNumber)).willReturn(Optional.empty());

        //when
        underTest.registerNewCustomer(request);

        // Then
        then(customerRepository).should().save(customerArgumentCaptor.capture());
        Customer customerArgumentCaptorValue =  customerArgumentCaptor.getValue();
        //can use isEqualTo
        assertThat(customerArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(customer, "id");

        assertThat(customerArgumentCaptorValue.getId()).isNotNull();
    }
}
