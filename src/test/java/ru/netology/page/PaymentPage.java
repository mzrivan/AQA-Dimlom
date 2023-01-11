package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class PaymentPage {

    private SelenideElement paymentHead = $(byText("Путешествие дня"));

    private SelenideElement cardButton = $(byText("Купить"));

    private SelenideElement creditButton = $(byText("Купить в кредит"));

    private SelenideElement continueButton = $(byText("Продолжить"));

    private SelenideElement payMethod = $("#root > div > h3");

    private SelenideElement numberField = $("[placeholder='0000 0000 0000 0000']");

    private SelenideElement monthField = $("[placeholder='08']");

    private SelenideElement yearField = $("[placeholder='22']");

    private SelenideElement cvvField = $("[placeholder='999']");

    private SelenideElement nameField = $(byText("Владелец"));

    public PaymentPage() {
        paymentHead.shouldBe(visible);
    }

    public void chooseCardPayment() {
        cardButton.click();
        payMethod.shouldHave(Condition.text("Оплата по карте"), Duration.ofSeconds(5));
    }

    public void chooseCreditPayment() {
        creditButton.click();
        payMethod.shouldHave(Condition.text("Кредит по данным карты"), Duration.ofSeconds(5));
    }
}
