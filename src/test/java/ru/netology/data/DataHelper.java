package ru.netology.data;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataHelper {
    public CardInfo getValidCard(int plusMonth) {
        Faker faker = new Faker();
        String number = faker.regexify("[0-9]{16}");
        String month = LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("MM"));
        String year = LocalDate.now().plusMonths(plusMonth).format(DateTimeFormatter.ofPattern("yy"));
        String name = faker.name().fullName();
        String cvv = faker.regexify("[0-9]{3}");

        return new CardInfo(number, month, year, name, cvv);
    }

    public CardInfo getValidCard() { //Перегрузка метода со значениями  от +1 месяца до +5 лет по умолчанию
        return getValidCard(1 + (int) (Math.random() * 12 * 5));
    }

    public CardInfo getValidCardNameLength(int length) {
        Faker faker = new Faker();
        CardInfo card = getValidCard();
        card.setName(faker.regexify("[A-Z’-]{" + length + "}"));
        return card;
    }

    public CardInfo getAcceptedCard() {
        CardInfo card = getValidCard();
        card.setNumber("4444444444444441");
        return card;
    }

    public CardInfo getDeniedCard() {
        CardInfo card = getValidCard();
        card.setNumber("4444444444444442");
        return card;
    }
}