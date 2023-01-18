package ru.netology.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import ru.netology.data.CardInfo;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement paymentHead = $(byText("Путешествие дня"));
    private SelenideElement continueButton = $(byText("Продолжить"));
    private SelenideElement payMethod = $("#root > div > h3");
    private SelenideElement numberField = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement numberFieldError = numberField.parent().sibling(0);
    private SelenideElement monthField = $("[placeholder='08']");
    private SelenideElement monthFieldError = monthField.parent().sibling(0);
    private SelenideElement yearField = $("[placeholder='22']");
    private SelenideElement yearFieldError = yearField.parent().sibling(0);
    private SelenideElement cvvField = $("[placeholder='999']");
    private SelenideElement cvvFieldError = cvvField.parent().sibling(0);

    private SelenideElement nameField = $$(".input__control").get(3);
    private SelenideElement spinner = $(".spin").parent();

    private SelenideElement notificationOk = $(".notification_status_ok");

    private SelenideElement closeNotificationOk = notificationOk.$("button.notification__closer");

    private SelenideElement notificationError = $(".notification_status_error");

    private SelenideElement closeNotificationError = notificationError.$("button.notification__closer");
    private long durationNotificationS = 15; //Время ожидания появления уведомления об успешной/неуспешной оплате

    public enum Field {
        NUMBER,
        MONTH,
        YEAR,
        NAME,
        CVV
    }


    public PaymentPage() {
        paymentHead.shouldBe(visible);
    }


    public void fillFields(CardInfo info) {
        numberField.setValue(info.getNumber());
        monthField.setValue(info.getMonth());
        yearField.setValue(info.getYear());
        nameField.setValue(info.getName());
        cvvField.setValue(info.getCvv());
        continueButton.click();
    }

    public void checkValidationOk() {
        spinner.shouldHave(Condition.text("Отправляем запрос в Банк..."))
                .shouldBe(visible);
    }

    public void checkNotificationOk() {
        notificationOk.shouldHave(Condition.text("Успешно"), Duration.ofSeconds(durationNotificationS))
                .shouldHave(Condition.text("Операция одобрена Банком."), Duration.ofSeconds(durationNotificationS))
                .shouldBe(visible, Duration.ofSeconds(durationNotificationS));
        closeNotificationOk.click();
        notificationOk.shouldNotBe(visible);
    }

    public void checkNotificationError() {
        notificationError.shouldHave(Condition.text("Ошибка"), Duration.ofSeconds(durationNotificationS))
                .shouldHave(Condition.text("Ошибка! Банк отказал в проведении операции."), Duration.ofSeconds(durationNotificationS))
                .shouldBe(visible, Duration.ofSeconds(durationNotificationS));
        closeNotificationError.click();
        notificationError.shouldNotBe(visible);
    }

    public void checkFieldError(Field field, String message) {

        SelenideElement element = null;
        switch (field) {
            case NUMBER:
                element = numberField;
                break;
            case MONTH:
                element = monthField;
                break;
            case YEAR:
                element = yearField;
                break;
            case NAME:
                element = nameField;
                break;
            case CVV:
                element = cvvField;
                break;
        }
        assert element != null;
        element.parent().sibling(0).shouldHave(Condition.text(message))
                .shouldBe(visible);
    }

    public void checkFieldError(Field field) {
        checkFieldError(field, "Неверный формат"); //Перегрузка метода с параметром текста сообщения ошибки
    }
}