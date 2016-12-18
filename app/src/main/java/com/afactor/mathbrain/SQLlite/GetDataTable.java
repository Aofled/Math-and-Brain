package com.afactor.mathbrain.SQLlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;

import com.afactor.mathbrain.System.MyMans;
import com.afactor.mathbrain.R;

import java.util.ArrayList;


public class GetDataTable extends AppCompatActivity {

    ListView mList;
    TextView header;
    DBHelper sqlHelper;
    SQLiteDatabase db;
    ArrayList<RecordTable> data = new ArrayList<RecordTable>();
    MyAdapter userAdapter;

    //для перевода даты в текстовый вариант
    private MyMans mymans = new MyMans();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_table_layout);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        header = (TextView) findViewById(R.id.header);
        mList = (ListView) findViewById(R.id.list);
        // Создаём объект для работы с БД
        sqlHelper = new DBHelper(getApplicationContext());

    }


    @Override
    public void onResume() {
        super.onResume();
        fillData();
        header.setText(getResources().getString(R.string.cnt_game)+" "+String.valueOf(data.size())); // + String.valueOf(userCursor.getCount()));
        userAdapter = new MyAdapter(this,data);   //Передаём данные ил ArrayList  в кастомный адаптер
        mList.setAdapter(userAdapter);
    }


    void fillData(){
        // открываем подключение
        db = sqlHelper.getReadableDatabase();
        //Запрос на получение данныз из SQLITE
        String query = "SELECT " + sqlHelper.COLUMN_POINTS + ", "
                + sqlHelper.COLUMN_DATE + " FROM " + sqlHelper.TABLE;
        Cursor recordcursor = db.rawQuery(query, null);  // Отправляем запрос и получаем курсор на полученные данные

        recordcursor.moveToFirst();  // Устанавливаем курсор на первый элемент таблицы
        String points;
        String date;



        //Получаем данные из таблицы пока не достигнем последнего элемента
        while (recordcursor.isAfterLast() == false) {

            points = recordcursor.getString(recordcursor
                    .getColumnIndex(sqlHelper.COLUMN_POINTS));
            date = recordcursor.getString(recordcursor
                    .getColumnIndex(sqlHelper.COLUMN_DATE));

            String day = date.substring(0, 2);
            String year = date.substring(6, 8);
            Integer i2 = Integer.valueOf(date.substring(3, 5));
            date = (day.concat(mymans.getManth(i2)).concat("20").concat(year));

            data.add(new RecordTable(points,date));
            recordcursor.moveToNext();
        }
        recordcursor.close();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Закрываем подключения
        db.close();

    }

    //кнопка "Домой" - как кнопка "Назад" вверху панели
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return(super.onOptionsItemSelected(item));
    }



}
