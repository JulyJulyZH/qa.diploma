package ru.netology.pages;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.DataGenerator;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class DataForm {
    private final ElementsCollection fields = $$(".input__control");
    private final SelenideElement cardNumber = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement month = $("[placeholder='08']");
    private final SelenideElement year = $("[placeholder='22']");
    private final SelenideElement holder = fields.get(3);
    private final SelenideElement cvc = $("[placeholder='999']");
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement successNotification = $(byText("Операция одобрена Банком."));
    private final SelenideElement errorNotification = $(byText("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement wrongFormatText = $(byText("Неверный формат"));
    private final SelenideElement wrongTermText = $(byText("Неверно указан срок действия карты"));
    private final SelenideElement cardExpiredText = $(byText("Истёк срок действия карты"));
    private final SelenideElement requiredFieldText = $(byText("Поле обязательно для заполнения"));

    public void fillCardInformation(DataGenerator.CardInformation cardInformation) {
        cardNumber.setValue(cardInformation.getNumber());
        month.setValue(cardInformation.getMonth());
        year.setValue(cardInformation.getYear());
        holder.setValue(cardInformation.getHolder());
        cvc.setValue(cardInformation.getCvc());
        continueButton.click();
    }

    public void checkSuccessfulPayment() {
        successNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void checkErrorPayment() {
        errorNotification.shouldBe(Condition.visible, Duration.ofSeconds(15));
    }

    public void checkWrongFormat() {
        wrongFormatText.shouldBe(visible);
    }

    public void checkExpired() {
        cardExpiredText.shouldBe(visible);
    }

    public void checkEmptyField() {
        requiredFieldText.shouldBe(visible);
    }

    public void checkWrongTerm() {
        wrongTermText.shouldBe(visible);
    }

}
