package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;

public class MainPage {
    private SelenideElement paymentHead = $(byText("Путешествие дня"));
    private SelenideElement cardButton = $(byText("Купить"));
    private SelenideElement creditButton = $(byText("Купить в кредит"));
    private SelenideElement payMethod = $("#root > div > h3");

    public MainPage() {
        paymentHead.shouldBe(visible);
    }

    public PaymentPage chooseCardPayment() {
        cardButton.click();
        payMethod.shouldHave(Condition.text("Оплата по карте"));
        return new PaymentPage();
    }

    public PaymentPage chooseCreditPayment() {
        creditButton.click();
        payMethod.shouldHave(Condition.text("Кредит по данным карты"));
        return new PaymentPage();
    }
}
