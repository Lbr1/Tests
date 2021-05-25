package com.junit.testing.payment;

import com.junit.testing.customer.Customer;
import com.junit.testing.customer.CustomerRegistrationRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Fail.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PaymentIntegrationTest {

    @Autowired
    private PaymentRepository paymentRepository;
    //@Autowired
    //private CustomerRegistrationController customerRegistrationController;

    @Autowired
    private MockMvc mockMvc;


    @Test
    void testMockMVC() throws Exception {
        // Given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Tiago", "000000");

        //CustomerRegistrationRequest customerRequest = new CustomerRegistrationRequest(customer);

        ResultActions customerRegisterResultActions = mockMvc.perform(MockMvcRequestBuilders
                .put("/api/v1/customer-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(customer))));
    }

    @Test
    void itShouldCreatePaymentSuccessfully() throws Exception {
        // Given
        UUID customerId = UUID.randomUUID();
        Customer customer = new Customer(customerId, "Tiago", "000000");

        //Register Customer
        CustomerRegistrationRequest customerRequest = new CustomerRegistrationRequest(customer);
        ResultActions customerRegResultActions = mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/customer-registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content(Objects.requireNonNull(objectToJson(customerRequest)))
        );

        //System.out.println(customerRegResultActions);
        // When

        long paymentId = 1L;

        Currency eurCurrency = Currency.EUR;
        Currency usdCurrency = Currency.USD;
        // Test with EUR to fail
        Payment payment = new Payment(paymentId,
                customerId,
                new BigDecimal("100.00"),
                usdCurrency,
                "x0x0x0x0x",
                "LUIS");

        PaymentRequest paymentRequest = new PaymentRequest(payment);

        // when payment is sent
        ResultActions paymentResultActions = mockMvc.perform(post("/api/v1/payment")
                 .contentType(MediaType.APPLICATION_JSON)
                  .content(Objects.requireNonNull(objectToJson(paymentRequest))));

        // Then
        customerRegResultActions.andExpect(status().isOk());
        paymentResultActions.andExpect(status().isOk());

        // Payment is stored in db
        // TODO Do not user paymentRepository instead create an endpoint to retrieve payments for customers
        assertThat(paymentRepository.findById(paymentId))
        .isPresent()
        .hasValueSatisfying(p -> assertThat(p).isEqualToComparingFieldByField(payment));
    }

    private String objectToJson(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (JsonProcessingException e) {
            fail("Failed to convert object to json");
            return null;
        }
    }
}
