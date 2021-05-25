package com.junit.testing.payment.stripe;

import com.junit.testing.payment.CardPaymentCharge;
import com.junit.testing.payment.Currency;
import com.stripe.exception.StripeException;
import com.stripe.model.Charge;
import com.stripe.net.RequestOptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

class StripeServiceTest {

    private StripeService underTest;

    //2 first show without API
    @Mock
    private StripeAPI stripeAPI;

    @BeforeEach
    void setUp() {
        //2
        MockitoAnnotations.initMocks(this);
        underTest = new StripeService(stripeAPI);
    }



    @Test
    void itShouldChargeCard() throws StripeException {
        //Given

        String cardSource = "0x0x0x";
        BigDecimal amount = new BigDecimal(10.00);
        Currency currency = Currency.USD;
        String description = "card x01";

        //When
        underTest.chargeCard(cardSource, amount, currency, description);

        //then
        ArgumentCaptor<Map<String, Object>> mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<RequestOptions> optionsArgumentCaptor = ArgumentCaptor.forClass(RequestOptions.class);

        then(stripeAPI).should().create(mapArgumentCaptor.capture(), optionsArgumentCaptor.capture());
    }

    @Test
    void itShouldChargeCardWithAPI() throws StripeException {
        //Given

        String cardSource = "0x0x0x";
        BigDecimal amount = new BigDecimal(10.00);
        Currency currency = Currency.USD;
        String description = "card x01";

        Charge charge = new Charge();
        charge.setPaid(true);
        given(stripeAPI.create(anyMap(), any())).willReturn(charge);

        //When
        CardPaymentCharge cardPaymentCharge = underTest.chargeCard(cardSource, amount, currency, description);

        //Then
        ArgumentCaptor<Map<String, Object>>  mapArgumentCaptor = ArgumentCaptor.forClass(Map.class);
        ArgumentCaptor<RequestOptions> optionsArgumentCaptor = ArgumentCaptor.forClass(RequestOptions.class);

        then(stripeAPI).should().create(mapArgumentCaptor.capture(), optionsArgumentCaptor.capture());

        Map<String, Object> requestMap = mapArgumentCaptor.getValue();

        assertThat(requestMap.keySet()).hasSize(4);
        assertThat(requestMap.get("amount")).isEqualTo(amount);
        assertThat(requestMap.get("currency")).isEqualTo(currency);
        assertThat(requestMap.get("source")).isEqualTo(cardSource);
        assertThat(requestMap.get("description")).isEqualTo(description);


        // the application key should be tested just in environment
        RequestOptions options = optionsArgumentCaptor.getValue();
        assertThat(options).isNotNull();

        assertThat(cardPaymentCharge).isNotNull();
        assertThat(cardPaymentCharge.isCardDebited()).isTrue();


    }
}