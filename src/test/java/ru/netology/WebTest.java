package ru.netology;

import com.codeborne.selenide.logevents.SelenideLogger;
import lombok.val;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.data.DataGenerator;
import ru.netology.data.SQL;
import ru.netology.pages.MainPage;


import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class WebTest {



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
        open(System.getProperty("app.host"));
    }


    // Тесты в форме купить
    @Test
    void successfulPaymentTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getApprovedCardInformation());
        dataForm.checkSuccessfulPayment();
        assertEquals("APPROVED", SQL.getPaymentStatus(SQL.getPaymentId()), "payment status 'approved'");
        assertEquals(45000, SQL.getPaymentAmount(SQL.getPaymentId()), "written off 45000");
    }

    @Test
    void declinedPaymentTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getDeclinedCardInformation());
        dataForm.checkErrorPayment();
        assertEquals("DECLINED", SQL.getPaymentStatus(SQL.getPaymentId()), "payment status 'declined'");
    }

    @Test
    void noDBPaymentTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getAnknownCardInformation());
        dataForm.checkErrorPayment();
        assertEquals("DECLINED", SQL.getPaymentStatus(SQL.getPaymentId()), "payment status 'declined'");
    }

    @Test
    void shortCardNumberTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithWrongLongCardNumber());
        dataForm.checkWrongFormat();
    }

    @Test // не проходит
    void emptyCardNumberTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyCardNumber());
        dataForm.checkEmptyField();
    }

    @Test // не проходит
    void emptyMonthTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyMonth());
        dataForm.checkEmptyField();
    }

    @Test
    void wrongMonthTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithWrongMonth());
        dataForm.checkWrongTerm();
    }

    @Test //не проходит
    void expiredMonthTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithLastMonth());
        dataForm.checkExpired();
    }

    @Test //не проходит
    void emptyYearTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyYear());
        dataForm.checkEmptyField();
    }

    @Test
    void expiredYearTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithWrongYear());
        dataForm.checkExpired();
    }

    @Test
    void bigYearTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithBigYear());
        dataForm.checkWrongTerm();
    }

    @Test
    void emptyHolderTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyHolderName());
        dataForm.checkEmptyField();
    }

    @Test //не проходит
    void numbersInHolderTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithNumberHolderName());
        dataForm.checkWrongFormat();
    }

    @Test //не проходит
    void kirillicInHolderTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithKirillicHolderName());
        dataForm.checkWrongFormat();
    }

    @Test //не проходит
    void symbolsInHolderTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithSymbolsHolderName());
        dataForm.checkWrongFormat();
    }

    @Test
    void emptyCVCTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithEmptyCvc());
        dataForm.checkEmptyField();
    }

    @Test
    void shortCVCTest() {
        val mainPage = new MainPage();
        val dataForm = mainPage.selectBuy();
        dataForm.fillCardInformation(DataGenerator.getCardInformationWithShortCvc());
        dataForm.checkWrongFormat();
    }

}

