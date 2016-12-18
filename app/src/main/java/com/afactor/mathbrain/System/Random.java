package com.afactor.mathbrain.System;

import java.text.SimpleDateFormat;
import java.util.ArrayList;


public class Random {

    int answer = 0;


    public ArrayList myArrayResponses(int Max, int Min) {
        ArrayList<Integer> mylist = new ArrayList<Integer>();

        //получаем дату для большего размытия в рандоме (в дальнейшем исправить!)
        long date = System.currentTimeMillis();
        SimpleDateFormat sdf = new SimpleDateFormat("SSS"); //получаем милисикунды

        //вырезаем первую секунду, и прибовляем 1 что бы число не было ранов 0
        int i1 = Integer.parseInt( sdf.format(date).substring(0, 1))+1;
        //вырезаем вторую секунду, и прибовляем 1 что бы число не было ранов 0
        int i2 = Integer.parseInt(sdf.format(date).substring(1, 2))+1;
        //вырезаем третью секунду, и прибовляем 1 что бы число не было ранов 0
        int i3 = Integer.parseInt(sdf.format(date).substring(2, 3))+1;

        //числа // умнажаем наши милисекунды на полученный диапозон
        mylist.add(i1*Rand(Min,Max));
        mylist.add(i2*Rand(Min,Max));
        mylist.add(i3*Rand(Min,Max));

        //знаки (рандом - диапозон от 0 до 1)
        mylist.add((int)(Math.random() * 2));
        mylist.add((int)(Math.random() * 2));

        //просчет знаков и вывод ответа
        if(mylist.get(3)==1 && mylist.get(4)==1) {
            answer = mylist.get(0) - mylist.get(1) - mylist.get(2);
        }

        if(mylist.get(3)==1 && mylist.get(4)==0) {
            answer = mylist.get(0) - mylist.get(1) + mylist.get(2);
        }

        if(mylist.get(3)==0 && mylist.get(4)==1) {
            answer = mylist.get(0) + mylist.get(1) - mylist.get(2);
        }

        if(mylist.get(3)==0 && mylist.get(4)==0) {
            answer = mylist.get(0) + mylist.get(1) + mylist.get(2);
        }

        if(answer<=0) {//если ответ отрецательный, то перезапускаем метод
           mylist.clear();
           return   myArrayResponses(Max,Min);
        }
        //ответы
        mylist.add(answer); //правильный ответ
        mylist.add(wrongAnswer(answer)); //неправильный ответ №1
        mylist.add(wrongAnswer(answer)); //неправильный ответ №2
        //номер кнопки с правильным ответом
        mylist.add( Rand(0,2));
        // возвращаем массив
        return mylist;
    }

    //метод неправильного ответа
    private int wrongAnswer(int answer) {
        int wr=0;
        int wrans=Rand(answer-30, answer+30); //формируем число
        if ((wrans==answer)||(wrans<0) || (wrans==0)) {
            return   wrongAnswer(answer);} //если число равно ответу, или число меньше нуля, или число равно 0 то повторяем попытку (рекурсия)
        else {
            wr=wrans;
        }
        return  wr;
    }


    //процедура получения рандома
    private int Rand (int Min, int Max) {
        int r = Min + (int)(Math.random() * ((Max - Min) + 1)) ;
        return r;
    }

}
