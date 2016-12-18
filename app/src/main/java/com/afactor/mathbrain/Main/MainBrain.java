package com.afactor.mathbrain.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.afactor.mathbrain.R;
import com.afactor.mathbrain.SQLlite.DBHelper;
import com.afactor.mathbrain.System.Random;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class MainBrain extends AppCompatActivity {

    private TextView equat, equat123, scores, digital_clock, txt_ans1, txt_ans2, txt_ans3;
    private Timer myTimer;

    private at.markushi.ui.CircleButton btn_answer1, btn_answer2, btn_answer3;

    private SQLiteDatabase db;

    Random ran = new Random();

    private SharedPreferences sPref; //данные для сохранения во внутренней памяти телефона

    private ArrayList<Integer> mass;

    private int mCurrentPeriod=30;  //значение в таймере
    String equation=""; //пример
    private int scr=0; //счетчик очков
    private int lavels; //уровень сложности
    private int max; //максимальное значение в рандоме
    private int min; //минимальное значение в рандоме
    private int specs; //количество очков при разных уровнях
    private int up=1;  //повышение уровеня сложности

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_brain_layout);

        sPref = getSharedPreferences("record", MODE_PRIVATE);   //получаем доступ к файлу рекордов
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);  //инициализируем для подключения кнопки назад

        equat=(TextView)findViewById(R.id.equation);
        scores=(TextView)findViewById(R.id.scores);
        digital_clock = (TextView) findViewById(R.id.digital_clock);

        btn_answer1=(at.markushi.ui.CircleButton)findViewById(R.id.btn_ans1);
        btn_answer2=(at.markushi.ui.CircleButton)findViewById(R.id.btn_ans2);
        btn_answer3=(at.markushi.ui.CircleButton)findViewById(R.id.btn_ans3);

        txt_ans1=(TextView)findViewById(R.id.txt_ans1);
        txt_ans2=(TextView)findViewById(R.id.txt_ans2);
        txt_ans3=(TextView)findViewById(R.id.txt_ans3);


        Intent intent = getIntent(); //получаем уровень сложности
        lavels = intent.getIntExtra(getResources().getString(R.string.lvl), 0);  //получаем уровень сложности

        //определяем сложность следствием "размытия рандома"
        if (lavels==1) {
            min=7; max=10; specs=5;}
        if (lavels==2) {
            min=10; max=15; specs=10;}
        if (lavels==3) {
            min=10; max=20; specs=15;}

        MainLogic(); //вызов основной процеруды для заполнения
        StartTimer(); //запускаем таймер
     }


    public void StartTimer() {
        myTimer = new Timer();
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }
        }, 0, 1000);
    };

    public void ResetTimer(){
        mCurrentPeriod = 30;
        if (myTimer != null)
            myTimer.cancel();
        digital_clock.setText(getResources().getString(R.string.txt_time));

    }

    public void TimerMethod(){
        this.runOnUiThread(Timer_Tick);
    }
    private Runnable Timer_Tick = new Runnable() {
        public void run() {
            mCurrentPeriod--;
            String temp = (new SimpleDateFormat("mm:ss")).format(new Date(
                    mCurrentPeriod * 1000));
            digital_clock.setText(temp);
            if( (mCurrentPeriod)==0){
                ResetTimer();
                wrongAnswer(getResources().getString(R.string.time_off));
            }
        }
    };



    public void MainLogic () {

        mass =ran.myArrayResponses(min, max); //передаем максимальнои и минимальное значение для размытия рандома

        //расставляем знаки
        if(mass.get(3)==1 && mass.get(4)==1) {
            equation=mass.get(0)+" - "+ mass.get(1)+" - "+mass.get(2)+" =";
        }

        if(mass.get(3)==1 && mass.get(4)==0) {
            equation=mass.get(0)+" - "+ mass.get(1)+" + "+mass.get(2)+" =";
        }

        if(mass.get(3)==0 && mass.get(4)==1) {
            equation=mass.get(0)+" + "+ mass.get(1)+" - "+mass.get(2)+"=";
        }

        if(mass.get(3)==0 && mass.get(4)==0) {
            equation=mass.get(0)+" + "+ mass.get(1)+" + "+mass.get(2)+"=";
        }

        //берем 8-й элемент из массива и смотрим в какую из кнопок его поместить
        switch(mass.get(8)) {
            case 0: {
                txt_ans1.setText(Integer.toString(mass.get(5)));
                txt_ans2.setText(Integer.toString(mass.get(7)));
                txt_ans3.setText(Integer.toString(mass.get(6)));
                break;
            }
            case 1: {
                txt_ans1.setText(Integer.toString(mass.get(7)));
                txt_ans2.setText(Integer.toString(mass.get(5)));
                txt_ans3.setText(Integer.toString(mass.get(6)));
                break;
            }
            case 2: {
                txt_ans1.setText(Integer.toString(mass.get(7)));
                txt_ans2.setText(Integer.toString(mass.get(6)));
                txt_ans3.setText(Integer.toString(mass.get(5)));
                break;
            }
        }
        //вывод уравнения
        equat.setText(equation);
    }


    public void responseTest(View view) {
        ResetTimer();
        //если нажата эта кнопка, и ответ совпадает с 5-м элементво в массиве
        if(view.getId()==R.id.btn_ans1){
            if(Integer.parseInt(txt_ans1.getText().toString())==mass.get(5)){
                correctAvswer();
            }
            else {
                wrongAnswer(getResources().getString(R.string.wrong_ans));
                return;
            }
        }
        //если нажата эта кнопка, и ответ совпадает с 5-м элементво в массиве
        if(view.getId()==R.id.btn_ans2) {
            if (Integer.parseInt(txt_ans2.getText().toString()) == mass.get(5)) {
                correctAvswer();
            } else {
                wrongAnswer(getResources().getString(R.string.wrong_ans));
                return;
            }
        }
        //если нажата эта кнопка, и ответ совпадает с 5-м элементво в массиве
        if(view.getId()==R.id.btn_ans3){
            if(Integer.parseInt(txt_ans3.getText().toString())==mass.get(5)){
                correctAvswer();
            }
            else{
                wrongAnswer(getResources().getString(R.string.wrong_ans));
                return;
            }
        }
        scores.setText(getResources().getString(R.string.points) + scr); //выводим очки
        MainLogic();//вызываем метод вставки значений снова
        StartTimer();
    }

    
    //если ответ верный
    public void correctAvswer () {
        scr=scr+specs;
        levelup(scr);
    }


    //если ответ неправильный
    public void wrongAnswer(String text) {
        SaveRecord(scr);
        AlertDialog.Builder builder = new AlertDialog.Builder(MainBrain.this);
        builder.setTitle(text)
                .setMessage( equation+mass.get(5)+"\n"+
                        getResources().getString(R.string.get_points)+" " + scr  +" "+ getResources().getString(R.string.txt_points)+" \n" +
                        getResources().getString( R.string.txt_get_points))
                .setCancelable(false);
        builder.setPositiveButton("Нет", new DialogInterface.OnClickListener() {   //Если ответ "нет" то закрываем данную форму, и возвращаемся на старт экран
            public void onClick(DialogInterface dialog, int arg1) {
                MainBrain.super.finish();
            }
        });
        builder.setNegativeButton("Да", new DialogInterface.OnClickListener() {  //запускаем все заново
            public void onClick(DialogInterface dialog, int arg1) {
                MainLogic(); StartTimer();//вызываем метод вставки значений снова
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
        scr=0;
        scores.setText(getResources().getString(R.string.points + scr));
    }


    //увеличиваем сложность со временем
    public void levelup (int scr) {
        Toast.makeText(getApplicationContext(),
                "Верно", Toast.LENGTH_LONG).show();
        //каждые 100 очков уровень повышается. (повышаем макс на +1)
        double k=scr/100;
        if (k>=up) {max=max+1; up=up+1; }
        else {return;}

    }

    //Сохранение основного рекорда
    public void SaveRecord (int k) {
        int i;
        if(sPref.contains("HASH")) { //если данные содержатся
            // проверяем, больше ли данные в префе, чем у нас
            i=sPref.getInt("HASH",0);
            if (i<k) { //если наш рекорд больше, чем был раньше, то перезаписываем
                SharedPreferences.Editor sh = sPref.edit();
                sh.putInt("HASH", k);
                InsertResult(k);
                sh.commit();
                //выводим сообщение, что поставили новый рекорд
                Toast.makeText(getApplicationContext(),
                        getResources().getString( R.string.new_record), Toast.LENGTH_LONG).show();
            } else {
                return;
            }
        } else { //если не содержатся то запхиваем в преф данные
            SharedPreferences.Editor sh = sPref.edit();
            InsertResult(k);
            sh.putInt("HASH", k);
            sh.commit();
        }
    }




    //что-то с базой данных
    private void InsertResult(int points) {
        DBHelper mDbHelper = new DBHelper(this);
        db = mDbHelper.getWritableDatabase();   //связываем с базой
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yy"); //формировка
        String strTime = simpleDateFormat.format(new Date());
        db.execSQL("INSERT INTO "+ DBHelper.TABLE+" (" + DBHelper.COLUMN_POINTS
                + ", " + DBHelper.COLUMN_DATE  + ") VALUES (+ '"+points+"','"+strTime+"');"); //вставляем запрос
    }


    //метод при закрытии на кнопки
    public void exitMess() {
        ResetTimer(); //останавливем таймер
        if (scr==0) {
            MainBrain.super.finish();
        }
        else {
            SaveRecord(scr);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainBrain.this);
            builder.setTitle(getResources().getString(R.string.exit))
                    .setMessage(getResources().getString(R.string.get_points)+" " + scr+" "  + getResources().getString(R.string.txt_points)+" \n")
                    .setCancelable(false);
            builder.setNegativeButton("Ок", new DialogInterface.OnClickListener() {  //запускаем все заново
                public void onClick(DialogInterface dialog, int arg1) {
                    MainBrain.super.finish();
                }
            });
            AlertDialog alert = builder.create();
            alert.show();}

    }


    // как кнопка "Назад" вверху панели
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        exitMess();
        return(super.onOptionsItemSelected(item));
    }


    //закрытие приложения по нажатию системной кнопки "назад"
    @Override
    public void onBackPressed() {
        exitMess();
    }




}
