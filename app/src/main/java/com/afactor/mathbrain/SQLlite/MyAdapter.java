package com.afactor.mathbrain.SQLlite;

import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.afactor.mathbrain.R;




public class MyAdapter extends BaseAdapter {

    ArrayList<RecordTable> data = new ArrayList<RecordTable>();
    Context context;

    //Конструктор
    public MyAdapter(Context context, ArrayList<RecordTable> arr) {
        if (arr != null) {
            data = arr;
        }
        this.context = context;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Получение объекта inflater из контекста
        LayoutInflater inflater = LayoutInflater.from(context);
        //Если someView (View из ListView) вдруг оказался равен
        //null тогда мы загружаем его с помошью inflater
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item, parent, false);
        }
        //Обявляем наши текствьюшки и связываем их с разметкой
        TextView header = (TextView) convertView.findViewById(R.id.tvDescr);
        TextView subHeader = (TextView) convertView.findViewById(R.id.tvPrice);

        //Устанавливаем в каждую текствьюшку соответствующий текст
        // сначала заголовок
        header.setText(data.get(position).points);
        // потом подзаголовок
        subHeader.setText(data.get(position).date);
        return convertView;
    }
}
