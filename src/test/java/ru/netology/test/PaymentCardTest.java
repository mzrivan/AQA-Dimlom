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

public class PaymentCardTest {
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
    @DisplayName("Accepted Card Payment")
    void shouldAcceptCardPayment() {
        cleanDB();
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        paymentPage.fillFields(dataHelper.getAcceptedCard());
        paymentPage.checkNotificationOk();
        String dataSQLPayment = getPaymentStatus();
        assertEquals("APPROVED", dataSQLPayment);
        String amount = getPaymentAmount();
        assertEquals("4500000", amount);
    }

    @Test
    @DisplayName("Denied Card Payment")
    void shouldDeniedCardPayment() {
        cleanDB();
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        paymentPage.fillFields(dataHelper.getDeniedCard());
        paymentPage.checkNotificationError();
        String dataSQLPayment = getPaymentStatus();
        assertEquals("DECLINED", dataSQLPayment);
    }

    @Test
    @DisplayName("Validation Valid Card Payment")
    void shouldValidCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        paymentPage.fillFields(dataHelper.getAcceptedCard());
        paymentPage.checkValidationOk();
    }

    @Test
    @DisplayName("Invalid Card Number Card Payment with char")
    void shouldGetErrorNumberCardPaymentWithChar() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber("123412341234123A");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card Number 13 digits Card Payment")
    void shouldGetErrorNumber13DigitsCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber("123412341234123");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card Number 13 digits Card Payment")
    void shouldGetErrorNumber13DigitCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber("123412341234123");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Empty Card Number Card Payment")
    void shouldGetErrorEmptyNumberCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setNumber(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
    }

    @Test
    @DisplayName("Invalid Card month 13 Card Payment")
    void shouldGetErrorMonth13CardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setMonth("13");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Invalid Card month 00 Card Payment")
    void shouldGetErrorMonth00CardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setMonth("00");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty Card Month Card Payment")
    void shouldGetErrorEmptyMonthCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setMonth(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.MONTH);
    }

    @Test
    @DisplayName("Invalid card last year Card Payment")
    void shouldGetErrorLastYearCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard(-12);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Истёк срок действия карты");
    }

    @Test
    @DisplayName("Invalid card more then 5 year Card Payment")
    void shouldGetErrorMore5YearCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard(12 * 6);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Неверно указан срок действия карты");
    }

    @Test
    @DisplayName("Empty Card Year Card Payment")
    void shouldGetErrorEmptyYearCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setYear(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.YEAR);
    }

    @Test
    @DisplayName("Valid Card Name 1 symbol Card Payment")
    void shouldGetOkName1symbolCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCardNameLength(1);
        paymentPage.fillFields(info);
        paymentPage.checkValidationOk();
    }

    @Test
    @DisplayName("Valid Card Name 26 symbols Card Payment")
    void shouldGetOkName26symbolsCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCardNameLength(26);
        paymentPage.fillFields(info);
        paymentPage.checkValidationOk();
    }

    @Test
    @DisplayName("Invalid name with digit Card Payment")
    void shouldGetErrorNameWithDigitCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(info.getName() + "1");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Invalid name with incorrect symbol Card Payment")
    void shouldGetErrorNameWithSymbolCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(info.getName() + "!");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Invalid name with other lang Card Payment")
    void shouldGetErrorNameWithOtherLangCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(info.getName() + "Й");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }

    @Test
    @DisplayName("Empty Card Name Card Payment")
    void shouldGetErrorEmptyNameCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setName(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.NAME);
    }


    @Test
    @DisplayName("Invalid cvv Card Payment")
    void shouldGetErrorCVVCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setCvv("12");
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.CVV);
    }

    @Test
    @DisplayName("Empty Card CVV Card Payment")
    void shouldGetErrorEmptyCvvCardPayment() {
        MainPage mainPage = new MainPage();
        DataHelper dataHelper = new DataHelper();
        PaymentPage paymentPage = mainPage.chooseCardPayment();
        CardInfo info = dataHelper.getValidCard();
        info.setCvv(null);
        paymentPage.fillFields(info);
        paymentPage.checkFieldError(PaymentPage.Field.CVV);
    }
}
