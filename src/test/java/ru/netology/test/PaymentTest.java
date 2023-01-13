package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.CardInfo;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;

public class PaymentTest {
    @BeforeEach
    void setup() {
        open("http://localhost:8080");
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    @Test
    @DisplayName("Choice Card Payment")
    void choiceCardPayment(){
        PaymentPage paymentPage = new PaymentPage();
        paymentPage.chooseCardPayment();
        //paymentPage.chooseCreditPayment();
        CardInfo cardInfo = new CardInfo("4444 4444 4444 4441", "02", "25","Boris","123");
        paymentPage.fillFields(cardInfo);
        paymentPage.checkNotificationOk();
        //paymentPage.checkNotificationError();
        //paymentPage.checkFieldError(PaymentPage.Field.YEAR);
    }
}
