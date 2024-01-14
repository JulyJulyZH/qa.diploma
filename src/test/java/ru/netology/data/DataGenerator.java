package ru.netology.data;

import com.github.javafaker.CreditCardType;
import com.github.javafaker.Faker;
import lombok.Value;

import java.time.LocalDate;
import java.util.Locale;

public final class DataGenerator {
    private static Faker faker = new Faker(new Locale("en"));
    private static String approvedCard = "4444 4444 4444 4441";
    private static String declinedCard = "4444 4444 4444 4442";

    private static int thisMonthInt = LocalDate.now().getMonth().getValue();
    private static String lastMonth = Integer.toString(thisMonthInt - 1);
    private static final int MONTHS_COUNT = 12;
    private static int monthInt = rnd(thisMonthInt, MONTHS_COUNT);
    private static String month = Integer.toString(monthInt);
    private static final int MIN_TWO_NUM = 10;

    static {
        if (monthInt < MIN_TWO_NUM) {
            month = "0" + month;
        }
    }

    private static final int MAX_TWO_NUM = 99;
    private static String wrongMonth = Integer.toString(rnd(MONTHS_COUNT, MAX_TWO_NUM));

    private static final int MIN_THREE_NUM = 100;
    private static final int MAX_THREE_NUM = 999;
    private static String cvc = Integer.toString(rnd(MIN_THREE_NUM, MAX_THREE_NUM));
    private static final int HUNDRED = 100;
    private static int thisYear = (LocalDate.now().getYear()) % HUNDRED; // получаем последние цифры текущего года
    private static String thisYearStr = Integer.toString(thisYear);

    private static final String lastYearStr = Integer.toString(thisYear - 1);
    private static final int VALIDITY_PERIOD = 5;
    private static String year = Integer.toString(rnd(thisYear, thisYear + VALIDITY_PERIOD));
    private static String wrongYear = Integer.toString(rnd(0, thisYear - 1));

    private static String bigYear = Integer.toString(rnd(thisYear + VALIDITY_PERIOD + 1, MAX_TWO_NUM));

    public static int rnd(final int min, final int max) {
        int maxR = max;
        maxR -= min;
        return (int) (Math.random() * ++maxR) + min;
    }

    private DataGenerator() {

    }

    public static CardInformation getApprovedCardInformation() {
        return new CardInformation(approvedCard, month, year, faker.name().fullName(), cvc);
    }

    public static CardInformation getDeclinedCardInformation() {
        return new CardInformation(declinedCard, month, year, faker.name().fullName(), cvc);
    }

    public static CardInformation getAnknownCardInformation() {
        return new CardInformation(faker.finance().creditCard(CreditCardType.MASTERCARD), month, year, faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithWrongLongCardNumber() {
        return new CardInformation("1111 1111 1111 111", month, year, faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithEmptyCardNumber() {
        return new CardInformation("", month, year, faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithWrongMonth() {
        return new CardInformation(approvedCard, wrongMonth, year, faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithEmptyMonth() {
        return new CardInformation(approvedCard, "", year, faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithLastMonth() {

        if (thisMonthInt - 1 == 0) {
            lastMonth = Integer.toString(12);
            thisYearStr = Integer.toString(thisYear - 1);
        } // ложноположительно проходит, т.к. истекло по году, нужен более сложный тест с подменой текущей даты

        return new CardInformation(approvedCard, lastMonth, thisYearStr, faker.name().fullName(), cvc);
    }


    public static CardInformation getCardInformationWithWrongYear() {
        return new CardInformation(approvedCard, month, wrongYear, faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithBigYear() {
        return new CardInformation(approvedCard, month, bigYear, faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithEmptyYear() {
        return new CardInformation(approvedCard, month, "", faker.name().fullName(), cvc);
    }

    public static CardInformation getCardInformationWithEmptyHolderName() {
        return new CardInformation(approvedCard, month, year, "", cvc);
    }

    public static CardInformation getCardInformationWithKirillicHolderName() {
        return new CardInformation(approvedCard, month, year, "Юра Петров", cvc);
    }

    public static CardInformation getCardInformationWithNumberHolderName() {
        return new CardInformation(approvedCard, month, year, "123SVETA", cvc);
    }

    public static CardInformation getCardInformationWithSymbolsHolderName() {
        return new CardInformation(approvedCard, month, year, "VALera()", cvc);
    }

    public static CardInformation getCardInformationWithEmptyCvc() {
        return new CardInformation(approvedCard, month, year, faker.name().fullName(), "");
    }

    public static CardInformation getCardInformationWithShortCvc() {
        return new CardInformation(approvedCard, month, year, faker.name().fullName(), year);
    }


    @Value
    public static class CardInformation {
        private String number;
        private String month;
        private String year;
        private String holder;
        private String cvc;
    }
}
