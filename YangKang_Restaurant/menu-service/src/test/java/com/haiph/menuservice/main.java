package com.haiph.menuservice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class main {
    public static void main(String[] args) {
        System.out.println("genDisscountCode(LocalDateTime.of(2022,12,10,12,00,00),LocalDateTime.of(2023,01,10,12,00,00),\"Hai Cut\") = " + genDisscountCode(LocalDateTime.of(2022, 12, 10, 12, 00, 00), LocalDateTime.of(2023, 01, 10, 12, 00, 00), "Hai Cut"));
    }

    public static String genDisscountCode(LocalDateTime startDate,LocalDateTime endDate,String infoName) {
        String[] gendisscountCode = infoName.split(" ");
        String discountCode = "";
        for (int i = 0; i < gendisscountCode.length; i++) {
            discountCode+= gendisscountCode[i].substring(0,1);
        }
        StringBuilder builder = new StringBuilder();
        int mounthStart = startDate.getMonth().getValue();
        int dayStart = startDate.getDayOfMonth();
        builder.append(mounthStart).append(dayStart);
        int mounthEnd = endDate.getMonth().getValue();
        int dayEnd = endDate.getDayOfMonth();
        builder.append(discountCode).append(mounthEnd).append(dayEnd);
        return builder.toString().concat("-" + 1);
    }
    public class menu{
        int stamen;

        public menu(int stamen) {
            this.stamen = stamen;
        }

        public int getStamen() {
            return stamen;
        }

        public void setStamen(int stamen) {
            this.stamen = stamen;
        }
    }
}
