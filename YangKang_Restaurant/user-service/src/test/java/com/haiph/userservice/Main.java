package com.haiph.userservice;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        System.out.println("genUserCode(\"Phạm Quang Hải\") = " + genUserCode("Phạm Quang Hải"));
    }
    public static String genUserCode(String fullName) {
        String temp = Normalizer.normalize(fullName, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
        String output = pattern.matcher(temp).replaceAll("").replace('đ', 'd').replace('Đ', 'D');
        String[] user = output.trim().split(" ");
        String userCode="";
        String firstUser = user[user.length-1];
        userCode+=firstUser;
        for (int i = 0; i < user.length-1; i++) {
            String lastUser= user[i].substring(0,1);
            userCode+=lastUser;
        }
        return userCode.toUpperCase();
    }
}
