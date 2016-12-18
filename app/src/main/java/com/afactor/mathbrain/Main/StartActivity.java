package com.afactor.mathbrain.Main;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.afactor.mathbrain.R;
import com.afactor.mathbrain.SQLlite.DBHelper;
import com.afactor.mathbrain.SQLlite.GetDataTable;


public class StartActivity extends AppCompatActivity {

    private TextView records;
    SharedPreferences sPref; //рекорды
    private final String key = "record";
    private int level=1; //по умолчанию уровень сложности 1

    //Метод что выполнять при создании формы
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_layout);

        sPref = getSharedPreferences(key, MODE_PRIVATE);   //получаем доступ к файлу рекордов
        records = (TextView) findViewById(R.id.records);

        RadioGroup radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case -1:
                        break;
                    case R.id.radioButtonOne:
                        level=1;
                        break;
                    case R.id.radioButtonTwo:
                        level=2;
                        break;
                    case R.id.radioButtonThird:
                        level=3;
                        break;
                    default:
                        break;
                }
            }
        });

        getRecords(); //выводим наши рекорды
    }


    //метод что выполнять при возвращении формы (когда мы закрываем вторую форму, и возвращаемся к этой, то запускается он)
    @Override
    protected void onRestart() {
        super.onRestart();
        getRecords();  //выводим наши рекорды
    }

    //метод получения рекорда
    public void getRecords() {
        int i;
        if (sPref.contains("HASH")) { //если данные содержатся
            i = sPref.getInt("HASH", 0);
            records.setText( getResources().getString( R.string.best_record)+" "+i); //выводим рекорды
        } else {
            records.setText(""); //выводим пустой текст
        }
    }

    //метод перехода к другой активити
    public void goGameStart(View v) {
        Intent intent = new Intent(getApplicationContext(), MainBrain.class);
        intent.putExtra("level", level);
        startActivity(intent);
    }

    //о разработчиках
    public void aboutDevelopers() {  // переименовать
        AlertDialog.Builder builder = new AlertDialog.Builder(StartActivity.this);
        builder.setTitle(R.string.developers)
                .setMessage(R.string.about_of_developers)
                .setIcon(R.drawable.cs) //сюда запихнуть иконку
                .setCancelable(false)
                .setNegativeButton("ОК",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    /*меню*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.start, menu);
        return true;
    }
    @Override
    // Обработка нажатия на пункт меню
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_record: {
                Intent intent = new Intent(getApplicationContext(), GetDataTable.class);
                startActivity(intent);
                return true;
            }
            case R.id.menu_record_del: { //удаляем рекорд
                sPref.edit().remove("HASH").commit();
                getRecords();
                DBHelper mDbHelper = new DBHelper(this);
                SQLiteDatabase db = mDbHelper.getWritableDatabase();
                db.delete(DBHelper.TABLE, null, null);
                Toast.makeText(getApplicationContext(),
                        R.string.clear_record, Toast.LENGTH_LONG).show();
                return true;
            }
            case R.id.menu_rezrabs: {
                //нажатие меню о разработчиках
                aboutDevelopers();
                return true;
            }
            case R.id.menu_exit: {
                super.finish(); //закрываем программу
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }
    }

















}
