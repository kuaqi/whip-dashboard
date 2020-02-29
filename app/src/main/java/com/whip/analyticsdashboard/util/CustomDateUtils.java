package com.whip.analyticsdashboard.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateUtils {

    /**
     * Converts the yyyy-MM-dd date format to dd/MM/yyyy.
     */
    public static String formatDateDayMonthYear(String stringDate) {
        SimpleDateFormat previousDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat newDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Date date = null;
        try {
            date = previousDateFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return newDateFormat.format(date);
    }
}
