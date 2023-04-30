package com.haiph.restaurant_service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class main {
    public static void main(String[] args) {
        genarateOrderCode(LocalDateTime.of(2001, 12, 10, 20, 10, 00), "T1-B1", "RESTAURANT Detail");
    }

    public static String genarateOrderCode(LocalDateTime hour, String formCode, String type) {
        String[] genType = type.split(" ");
        System.out.println("genType = " + genType);
        String gentypes = "";
        for (int i = 0; i < genType.length; i++) {
            gentypes+= genType[i].substring(0,1);
        }
        System.out.println("gentypes = " + gentypes);
        int year = hour.getYear();
        int month = hour.getMonthValue();
        int day = hour.getDayOfMonth();
        int hours = hour.getHour();
        int minute = hour.getMinute();
        StringBuilder builder = new StringBuilder();
        builder.append(day).append(month).append(year).append(".").append(hours).append(minute);
        String result = gentypes+"."+builder+"."+formCode;
        System.out.println("result = " + result);
        return "hi";
    }
}
