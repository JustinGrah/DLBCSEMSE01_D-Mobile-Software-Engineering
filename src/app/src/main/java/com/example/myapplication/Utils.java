package com.example.myapplication;

import android.os.Build;

import java.time.LocalDate;

public class Utils {

    public static LocalDate localDateFromString(String date) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return date == null ? null : LocalDate.parse(date);
        } else {
            return null;
        }
    }

    public static String localDateToString(LocalDate date) {
        return date == null ? null : date.toString();
    }
}
