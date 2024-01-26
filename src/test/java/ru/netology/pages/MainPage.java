package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;


public class MainPage {
    private SelenideElement buttonBuyByDebit = $(byText("Купить"));
    private SelenideElement buttonBuyCredit = $(byText("Купить в кредит"));
    private SelenideElement paymentBySelectedWayHeader = $("#root > div > h3");


    public DataForm selectBuy() {
        buttonBuyByDebit.click();
        paymentBySelectedWayHeader.shouldHave(Condition.text("Оплата по карте"));
        return new DataForm();
    }

    public DataForm selectBuyByCredit() {
        buttonBuyCredit.click();
        paymentBySelectedWayHeader.shouldHave(Condition.text("Кредит по данным карты"));
        return new DataForm();
    }
}
