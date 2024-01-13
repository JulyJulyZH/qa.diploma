package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQL;
import ru.netology.pages.MainPage;


import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTestCredit {
    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }


    @BeforeEach
    void initEach() {
        open("http://localhost:8080");
    }


    //тесты в форме купить в кредит

    @Test
    void successfulPaymentCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getApprovedCardInformation());
        dataForm.checkSuccessfulPayment();
        assertEquals("APPROVED", SQL.getPaymentStatusCredit(SQL.getPaymentId()), "payment status 'approved'");
    }

    @Test
    void declinedPaymentCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getDeclinedCardInformation());
        dataForm.checkErrorPayment();
        assertEquals("DECLINED", SQL.getPaymentStatusCredit(SQL.getPaymentId()), "payment status 'declined'");
    }

    @Test
    void noDBPaymentCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getAnknownCardInformation());
        dataForm.checkErrorPayment();
        assertEquals("DECLINED", SQL.getPaymentStatusCredit(SQL.getPaymentId()), "payment status 'declined'");
    }

    @Test
    void shortCardNumberCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithWrongLongCardNumber());
        dataForm.checkWrongFormat();
    }

    @Test // не проходит
    void emptyCardNumberCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyCardNumber());
        dataForm.checkEmptyField();
    }

    @Test // не проходит
    void emptyMonthCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyMonth());
        dataForm.checkEmptyField();
    }

    @Test
    void wrongMonthCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithWrongMonth());
        dataForm.checkWrongTerm();
    }

    @Test //не проходит
    void expiredMonthCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithLastMonth());
        dataForm.checkExpired();
    }

    @Test //не проходит
    void emptyYearCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyYear());
        dataForm.checkEmptyField();
    }

    @Test
    void expiredYearCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithWrongYear());
        dataForm.checkExpired();
    }

    @Test
    void bigYearCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithBigYear());
        dataForm.checkWrongTerm();
    }

    @Test
    void emptyHolderCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyHolderName());
        dataForm.checkEmptyField();
    }

    @Test //не проходит
    void numbersInHolderCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithNumberHolderName());
        dataForm.checkWrongFormat();
    }

    @Test //не проходит
    void kirillicInHolderCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithKirillicHolderName());
        dataForm.checkWrongFormat();
    }

    @Test //не проходит
    void symbolsInHolderCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithSymbolsHolderName());
        dataForm.checkWrongFormat();
    }

    @Test //ложно проходит текст у другого поля
    void emptyCVCCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyCvc());
        dataForm.checkEmptyField();
    }

    @Test
    void shortCvcCreditTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuyByCredit();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithShortCvc());
        dataForm.checkWrongFormat();
    }

}

