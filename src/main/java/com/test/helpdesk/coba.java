package com.test.helpdesk;

import java.util.TimeZone;

public class coba {

    public static void main(String[] args) {

//        for (int i = 1 ; i <= 10; i++ ){
//            String number = String.valueOf(i).length() == 1 ? "000"+String.valueOf(i) :
//                            String.valueOf(i).length() == 2 ? "00"+String.valueOf(i) :
//                            String.valueOf(i).length() == 3 ? "0"+String.valueOf(i) : String.valueOf(i);
//            String temp = new String("USR" + number );
//            System.out.println(temp);
//        }
        String[] id = TimeZone.getAvailableIDs();
        System.out.println(TimeZone.getTimeZone("Asia/Jakarta"));
        System.out.println(new String("Teknisi").equals("Teknisi"));

    }
}
