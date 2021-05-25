package com.junit.testing.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class PhoneNumberValidatorTest {

   //private PhoneNumberValidator underTest;

    @BeforeEach
    void setUp(){
        //underTest = new PhoneNumberValidator();
    }

   /* @Test
    @DisplayName("PhoneNumber should has 9 digits")
    void itShouldValidatePhoneNumber(){

        // Given
        String phoneNumber = "257245348";

        // when
        boolean isValid = underTest.validate(phoneNumber);

        //Then
       assertThat(isValid).isTrue();

    }

    @ParameterizedTest
    @CsvSource({"222222222, True", "222222222, True", "222272222, True"})
    void itShouldValidatePhoneNumber(String phoneNumber, String expected){

        // when
        boolean isValid = underTest.validate(phoneNumber);

        //Then
        assertThat(isValid).isEqualTo(Boolean.valueOf(expected));

    }*/
}
