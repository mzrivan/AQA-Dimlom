package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.DataHelper;
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
    @DisplayName(" Card Payment")
    void shouldCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        paymentPage.fillFields(dataHelper.getAcceptedCard());
        paymentPage.checkNotificationOk();
        //paymentPage.checkNotificationError();
        //paymentPage.checkFieldError(PaymentPage.Field.YEAR);
    }

    @Test
    @DisplayName("Invalid Card Number CARD PAYMENT with char")
    void shouldGetErrorNumberCardPaymentWithChar() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setNumber("123412341234123A");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card Number 13 digits CARD PAYMENT")
    void shouldGetErrorNumber13DigitsCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setNumber("123412341234123");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card Number 13 digits CARD PAYMENT")
    void shouldGetErrorNumber13DigitCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setNumber("123412341234123");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Empty Card Number CARD PAYMENT")
    void shouldGetErrorEmptyNumberCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setNumber(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card month 13 CARD PAYMENT")
    void shouldGetErrorMonth13CardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setMonth("13");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Invalid Card month 00 CARD PAYMENT")
    void shouldGetErrorMonth00CardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setMonth("00");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
    }
    @Test
    @DisplayName("Empty Card Month CARD PAYMENT")
    void shouldGetErrorEmptyMonthCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setMonth(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH);
    }
    @Test
    @DisplayName("Invalid card last year CARD PAYMENT")
    void shouldGetErrorLastYearCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard(-12);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Invalid card more then 5 year CARD PAYMENT")
    void shouldGetErrorMore5YearCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard(12*6);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Неверно указан срок действия карты");
    }
    @Test
    @DisplayName("Empty Card Year CARD PAYMENT")
    void shouldGetErrorEmptyYearCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setYear(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR);
    }
    @Test
    @DisplayName("Invalid name with digit CARD PAYMENT")
    void shouldGetErrorNameWithDigitCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setName(info.getName()+"1");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Invalid name with incorrect symbol CARD PAYMENT")
    void shouldGetErrorNameWithSymbolCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setName(info.getName()+"!");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Invalid name with other lang CARD PAYMENT")
    void shouldGetErrorNameWithOtherLangCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setName(info.getName()+"Й");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }
    @Test
    @DisplayName("Empty Card Name CARD PAYMENT")
    void shouldGetErrorEmptyNameCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setName(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }
    @Test
    @DisplayName("Invalid cvv CARD PAYMENT")
    void shouldGetErrorCVVCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setCvv("12");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.CVV);
    }

    @Test
    @DisplayName("Empty Card CVV CARD PAYMENT")
    void shouldGetErrorEmptyCvvCardPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCardPayment();
        var info = dataHelper.getValidCard();
        info.setCvv(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.CVV);
    }
    @Test
    @DisplayName(" Credit Payment")
    void shouldCreditPayment() {
        PaymentPage paymentPage = new PaymentPage();
        DataHelper dataHelper = new DataHelper();
        paymentPage.chooseCreditPayment();
        paymentPage.fillFields(dataHelper.getAcceptedCard());
        paymentPage.checkNotificationOk();
    }


}
