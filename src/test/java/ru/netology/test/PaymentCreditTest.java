package ru.netology.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
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
        void shouldAcceptCardPayment() {
            cleanDB();
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            paymentPage.fillFields(dataHelper.getAcceptedCard());
            paymentPage.checkNotificationOk();
            String dataSQLPayment = getCreditStatus();
            assertEquals("APPROVED", dataSQLPayment);
        }

        @Test
        @DisplayName("Denied Credit Payment")
        void shouldDeniedCardPayment() {
            cleanDB();
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            paymentPage.fillFields(dataHelper.getDeniedCard());
            paymentPage.checkNotificationError();
            String dataSQLPayment = getCreditStatus();
            assertEquals("DECLINED", dataSQLPayment);
        }
        @Test
        @DisplayName("Validation Valid Credit Payment")
        void shouldValidCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            paymentPage.fillFields(dataHelper.getAcceptedCard());
            paymentPage.checkValidationOk();
        }

        @Test
        @DisplayName("Invalid Card Number Credit PAYMENT with char")
        void shouldGetErrorNumberCardPaymentWithChar() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setNumber("123412341234123A");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
        }

        @Test
        @DisplayName("Invalid Card Number 13 digits Credit PAYMENT")
        void shouldGetErrorNumber13DigitsCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setNumber("123412341234123");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
        }

        @Test
        @DisplayName("Invalid Card Number 13 digits Credit PAYMENT")
        void shouldGetErrorNumber13DigitCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setNumber("123412341234123");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
        }

        @Test
        @DisplayName("Empty Card Number Credit PAYMENT")
        void shouldGetErrorEmptyNumberCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setNumber(null);
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NUMBER);
        }

        @Test
        @DisplayName("Invalid Card month 13 Credit PAYMENT")
        void shouldGetErrorMonth13CardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setMonth("13");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
        }

        @Test
        @DisplayName("Invalid Card month 00 Credit PAYMENT")
        void shouldGetErrorMonth00CardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setMonth("00");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.MONTH, "Неверно указан срок действия карты");
        }
        @Test
        @DisplayName("Empty Card Month Credit PAYMENT")
        void shouldGetErrorEmptyMonthCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setMonth(null);
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.MONTH);
        }
        @Test
        @DisplayName("Invalid card last year Credit PAYMENT")
        void shouldGetErrorLastYearCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard(-12);
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Истёк срок действия карты");
        }

        @Test
        @DisplayName("Invalid card more then 5 year Credit PAYMENT")
        void shouldGetErrorMore5YearCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard(12*6);
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.YEAR, "Неверно указан срок действия карты");
        }
        @Test
        @DisplayName("Empty Card Year Credit PAYMENT")
        void shouldGetErrorEmptyYearCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setYear(null);
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.YEAR);
        }

        @Test
        @DisplayName("Valid Card Name 1 symbol Credit PAYMENT")
        void shouldGetOkName1symbolCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCardNameLength(1);
            paymentPage.fillFields(info);
            paymentPage.checkValidationOk();
        }

        @Test
        @DisplayName("Valid Card Name 26 symbols Credit PAYMENT")
        void shouldGetOkName26symbolsCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCardNameLength(26);
            paymentPage.fillFields(info);
            paymentPage.checkValidationOk();
        }
        @Test
        @DisplayName("Invalid name with digit Credit PAYMENT")
        void shouldGetErrorNameWithDigitCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setName(info.getName()+"1");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NAME);
        }

        @Test
        @DisplayName("Invalid name with incorrect symbol Credit PAYMENT")
        void shouldGetErrorNameWithSymbolCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setName(info.getName()+"!");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NAME);
        }

        @Test
        @DisplayName("Invalid name with other lang Credit PAYMENT")
        void shouldGetErrorNameWithOtherLangCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setName(info.getName()+"Й");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NAME);
        }

        @Test
        @DisplayName("Empty Card Name Credit PAYMENT")
        void shouldGetErrorEmptyNameCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setName(null);
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.NAME);
        }


        @Test
        @DisplayName("Invalid cvv Credit PAYMENT")
        void shouldGetErrorCVVCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setCvv("12");
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.CVV);
        }

        @Test
        @DisplayName("Empty Card CVV Credit PAYMENT")
        void shouldGetErrorEmptyCvvCardPayment() {
            MainPage mainPage = new MainPage();
            DataHelper dataHelper = new DataHelper();
            var paymentPage = mainPage.chooseCreditPayment();
            var info = dataHelper.getValidCard();
            info.setCvv(null);
            paymentPage.fillFields(info);
            paymentPage.checkFieldError(PaymentPage.Field.CVV);
        }
}
