package ru.netology;

import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.RestApi;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ApiTests {

    @Test
    public void approvedPaymentTest() {
        final String response = RestApi.fillPaymentForm(DataGenerator.getApprovedCardInformation());
        assertTrue(response.contains("APPROVED"));
    }

    @Test
    public void declinedPaymentTest() {
        final String response = RestApi.fillPaymentForm(DataGenerator.getDeclinedCardInformation());
        assertTrue(response.contains("DECLINED"));
    }

    @Test
    public void approvedCreditTest() {
        final String response = RestApi.fillPaymentFormCredit(DataGenerator.getApprovedCardInformation());
        assertTrue(response.contains("APPROVED"));
    }

    @Test
    public void declinedCreditTest() {
        final String response = RestApi.fillPaymentFormCredit(DataGenerator.getDeclinedCardInformation());
        assertTrue(response.contains("DECLINED"));
    }
}
