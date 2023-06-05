package com.haiph.menuservice;

import com.haiph.menuservice.dto.response.DiscountResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class main {
    public static void main(String[] args) {
        genPaymentCode(LocalDate.now(), "HAIPQ");
    }

//    private String checkCode(String menuName) {
//        String code = gennareateCode(menuName);
//        String codeBefore = code;
//        Integer number = 0;
//        while (!menuRepository.findByCode(code).isPresent()) {
//            number++;
//            code = codeBefore+"-"+number;
//        }
//        return code;
//    }
    private String gennareateCode(String menuName) {
        String[] arrMenuName = menuName.split(" ");
        String newCode= "";
        for (int i = 0; i < arrMenuName.length; i++) {
            newCode+=arrMenuName[i].substring(0,1).toUpperCase();
        }
        StringBuilder result = new StringBuilder();
        result.append("MENU").append("-").append(newCode);
        return result.toString();

    }

    public static String genPaymentCode(LocalDate createDate, String personCode) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String createDateStr = createDate.format(formatter).replace("-", "");
        String genPaymentCode = personCode + "-" + createDateStr;
        System.out.println("genPaymentCode = " + genPaymentCode);
        return genPaymentCode;
    }

    public static String genDisscountCode(LocalDateTime startDate, LocalDateTime endDate, String infoName) {
        String[] gendisscountCode = infoName.split(" ");
        String discountCode = "";
        for (int i = 0; i < gendisscountCode.length; i++) {
            discountCode += gendisscountCode[i].substring(0, 1);
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

    public class menu {
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
