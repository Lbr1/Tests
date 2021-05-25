package com.junit.testing.payment;

import com.junit.testing.customer.Customer;
import com.junit.testing.customer.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;

class PaymentServiceTest {

    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private CardPaymentCharger cardPaymentCharger;

    private PaymentService underTest;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.initMocks(this);
        underTest = new PaymentService(customerRepository, paymentRepository, cardPaymentCharger);
    }

    @Test
    void itShouldChargeCardSuccessfully() {
        // Given
        UUID customerId = UUID.randomUUID();
        // Mock the Customer because I have not using it
        // ... Customer exists
        given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));

        // Payment Request
        PaymentRequest paymentRequest = new PaymentRequest(
                new Payment(
                null,
                null,
                new BigDecimal(100.00),
                Currency.USD,
                "card1234",
                "donation"
                ));

        // ... Card is charged successfully
        given(cardPaymentCharger.chargeCard(paymentRequest.getPayment().getSource(),
                paymentRequest.getPayment().getAmount(),
                paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription())).willReturn(new CardPaymentCharge(true));

        // when
        underTest.chargeCard(customerId, paymentRequest);

        //then
        ArgumentCaptor<Payment> paymentArgumentCaptor = ArgumentCaptor.forClass(Payment.class);

        then(paymentRepository).should().save(paymentArgumentCaptor.capture());

        Payment paymentArgumentCaptorValue = paymentArgumentCaptor.getValue();
        assertThat(paymentArgumentCaptorValue)
                .isEqualToIgnoringGivenFields(paymentRequest.getPayment(), "customerId");

        // Testing customer ID
        assertThat(paymentArgumentCaptorValue.getCustomerID()).isEqualTo(customerId);
    }


    // Run coverare
    // test if the card is not debited
    // show coverage


    @Test
    void itShouldThrowWhenCardIsNotCharged(){
        // Given
        UUID customerId = UUID.randomUUID();
        // Mock the Customer because I have not using it
        // ... Customer exists
        given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));

        Currency currency = Currency.USD;

        // Payment Request
        PaymentRequest paymentRequest = new PaymentRequest(
                new Payment(
                        null,
                        null,
                        new BigDecimal(100.00),
                        Currency.USD,
                        "card1234",
                        "donation"
                ));

        // ... Card is charged successfuly
        given(cardPaymentCharger.chargeCard(paymentRequest.getPayment().getSource(),
                paymentRequest.getPayment().getAmount(),
                paymentRequest.getPayment().getCurrency(),
                paymentRequest.getPayment().getDescription())).willReturn(new CardPaymentCharge(false));

        // when
        //then
        assertThatThrownBy(() -> underTest.chargeCard(customerId, paymentRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Card not debited for customer " + customerId);



        then(paymentRepository).should(never()).save(any(Payment.class));
    }


    //Currency not supported
    // copy and past code its not a problem, because we can see inside of any test method what we have testing
    @Test
    void itShouldTNotChargeCardAndThrowCurrencyNotSupported(){
        // Given
        UUID customerId = UUID.randomUUID();
        // Mock the Customer because I have not using it
        // ... Customer exists
        given(customerRepository.findById(customerId)).willReturn(Optional.of(mock(Customer.class)));

        Currency currency = Currency.USD;

        // Payment Request
        PaymentRequest paymentRequest = new PaymentRequest(
                new Payment(
                        null,
                        null,
                        new BigDecimal(100.00),
                        Currency.EUR,
                        "card1234",
                        "donation"
                ));

        // when
        assertThatThrownBy(() -> underTest.chargeCard(customerId, paymentRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Currency [" + Currency.EUR + "] not supported");

        //then
        // ... no interaction with cardPaymentCharger
        then(cardPaymentCharger).shouldHaveNoInteractions();
        // ... no interaction with paymentRepository
        then(paymentRepository).shouldHaveNoInteractions();;
    }


    @Test
    void itShouldTNotChargeCardAndThrowWhenCustomerNotFound(){
        // Given
        UUID customerId = UUID.randomUUID();

        // ... Customer not found in db
        given(customerRepository.findById(customerId)).willReturn(Optional.empty());

        //when
        //then
        assertThatThrownBy(() -> underTest.chargeCard(customerId, new PaymentRequest(new Payment())))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Customer with id [" + customerId + "] not found");


        // ... no interaction with cardPaymentCharger
        then(cardPaymentCharger).shouldHaveNoInteractions();
        // ... no interaction with paymentRepository
        then(paymentRepository).shouldHaveNoInteractions();;
    }

}