package com.test.helpdesk;

import com.test.helpdesk.model.User;
import com.test.helpdesk.util.DateConvert;

//import java.sql.Date;
import java.util.Date;
import java.util.regex.Pattern;

public class coba {

    public static void main(String[] args) {

//        for (int i = 1 ; i <= 10; i++ ){
//            String number = String.valueOf(i).length() == 1 ? "000"+String.valueOf(i) :
//                            String.valueOf(i).length() == 2 ? "00"+String.valueOf(i) :
//                            String.valueOf(i).length() == 3 ? "0"+String.valueOf(i) : String.valueOf(i);
//            String temp = new String("USR" + number );
//            System.out.println(temp);
//        }
//        User user = new User();
//        System.out.println(user == null);
//        String[] id = TimeZone.getAvailableIDs();
        System.out.println(Pattern.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[?!@#$%^&+=]).{8,}$", "Admins1!"));
//        System.out.println(TimeZone.getTimeZone("Asia/Jakarta"));
//        System.out.println(new String("Teknisi").equals("Teknisi"));
//        Date date = Date.valueOf("2021-03-18");
//        DateConvert dateConvert = new DateConvert();

        Date date = new Date();
        System.out.println(date);

    }
}
