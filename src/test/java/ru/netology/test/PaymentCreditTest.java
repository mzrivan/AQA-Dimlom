package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.data.CardInfo;
import ru.netology.data.DataHelper;
import ru.netology.page.MainPage;
import ru.netology.page.PaymentPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.data.DBhelper.*;

public class PaymentCreditTest {
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
    @DisplayName("Accepted Credit Payment")
    void shouldAcceptCreditPayment() {
        cleanDB();
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        paymentPage.fillFields(dataHelper.getAcceptedCard());
        paymentPage.checkNotificationOk();
        String dataSQLPayment = getCreditStatus();
        assertEquals("APPROVED", dataSQLPayment);
    }

    @Test
    @DisplayName("Denied Credit Payment")
    void shouldDeniedCreditPayment() {
        cleanDB();
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        paymentPage.fillFields(dataHelper.getDeniedCard());
        paymentPage.checkNotificationError();
        String dataSQLPayment = getCreditStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test
    @DisplayName("Validation Valid Credit Payment")
    void shouldValidCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        paymentPage.fillFields(dataHelper.getAcceptedCard());
        paymentPage.checkValidationOk();
    }

    @Test
    @DisplayName("Invalid Card Number Credit PAYMENT with char")
    void shouldGetErrorNumberCreditPaymentWithChar() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber("123412341234123A");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card Number 13 digits Credit PAYMENT")
    void shouldGetErrorNumber13DigitsCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber("123412341234123");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card Number 13 digits Credit PAYMENT")
    void shouldGetErrorNumber13DigitCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber("123412341234123");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Empty Card Number Credit PAYMENT")
    void shouldGetErrorEmptyNumberCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card month 13 Credit PAYMENT")
    void shouldGetErrorMonth13CreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setMonth("13");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Invalid Card month 00 Credit PAYMENT")
    void shouldGetErrorMonth00CreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setMonth("00");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty Card Month Credit PAYMENT")
    void shouldGetErrorEmptyMonthCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setMonth(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH);
    }

    @Test
    @DisplayName("Invalid card last year Credit PAYMENT")
    void shouldGetErrorLastYearCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard(-12);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Invalid card more then 5 year Credit PAYMENT")
    void shouldGetErrorMore5YearCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard(12 * 6);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty Card Year Credit PAYMENT")
    void shouldGetErrorEmptyYearCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setYear(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR);
    }

    @Test
    @DisplayName("Valid Card Name 1 symbol Credit PAYMENT")
    void shouldGetOkName1symbolCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCardNameLength(1);
        paymentPage.fillFields(info);
        paymentPage.checkValidationOk();
    }

    @Test
    @DisplayName("Valid Card Name 26 symbols Credit PAYMENT")
    void shouldGetOkName26symbolsCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCardNameLength(26);
        paymentPage.fillFields(info);
        paymentPage.checkValidationOk();
    }

    @Test
    @DisplayName("Invalid name with digit Credit PAYMENT")
    void shouldGetErrorNameWithDigitCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(info.getName() + "1");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Invalid name with incorrect symbol Credit PAYMENT")
    void shouldGetErrorNameWithSymbolCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(info.getName() + "!");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Invalid name with other lang Credit PAYMENT")
    void shouldGetErrorNameWithOtherLangCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(info.getName() + "Й");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Empty Card Name Credit PAYMENT")
    void shouldGetErrorEmptyNameCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }


    @Test
    @DisplayName("Invalid cvv Credit PAYMENT")
    void shouldGetErrorCVVCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setCvv("12");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.CVV);
    }

    @Test
    @DisplayName("Empty Card CVV Credit PAYMENT")
    void shouldGetErrorEmptyCvvCreditPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCreditPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setCvv(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.CVV);
    }
}
