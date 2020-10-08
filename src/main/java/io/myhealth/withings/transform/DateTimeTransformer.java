package io.myhealth.withings.transform;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeTransformer {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static LocalDate fromString(String date) {
        return LocalDate.parse(date, formatter);
    }
}
