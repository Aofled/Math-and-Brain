package com.afactor.mathbrain.System;



public class MyMans {


    //получение значения месяца прописью
    public static String getManth(int manthnamb) {
        String mansB=null;

        switch(manthnamb) {
            case 1:
                mansB=" января ";
                break;
            case 2:
                mansB=" февраля ";
                break;
            case 3:
                mansB=" марта ";
                break;
            case 4:
                mansB=" апреля ";
                break;
            case 5:
                mansB=" майя ";
                break;
            case 6:
                mansB=" июня ";
                break;
            case 7:
                mansB=" июля ";
                break;
            case 8:
                mansB=" августа ";
                break;
            case 9:
                mansB=" сенября ";
                break;
            case 10:
                mansB=" октября ";
                break;
            case 11:
                mansB=" ноября ";
                break;
            case 12:
                mansB=" декабря ";
                break;
        }
        return mansB;
    }








}
