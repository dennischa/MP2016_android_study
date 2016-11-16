package com.example.mylogger2;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * Created by user on 2016-11-16.
 */

public class ActList extends Activity {
   static DataBaseOpen dataBaseOpen;
    static SQLiteDatabase db;
    ArrayList<MyData> arrayList;
    ArrayList<String> strList;
    Intent intent;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_list);
        intent = new Intent(this, SimpleMap.class);;
        //db 생성
       dataBaseOpen = new DataBaseOpen(this);
        db = dataBaseOpen.getWritableDatabase();

        readTable();
        listPrint();
    }

    public void readTable() {
        arrayList = new ArrayList<MyData>();
        String sql = "select * from t_table";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            String date = results.getString(1);
            String time = results.getString(2);
            String address = results.getString(3);
            String act = results.getString(4);
            String detail = results.getString(5);
            double latitude = results.getDouble(6);
            double longitude = results.getDouble(7);

            arrayList.add(new MyData(date, time, address, act, detail,latitude, longitude));
            results.moveToNext();
        }
        results.close();
    }

    public void listPrint() {
        strList = new ArrayList<String>();

        for (int i = 0; i < arrayList.size(); i++) {
            strList.add(arrayList.get(i).getPrint());
        }
        ArrayAdapter<String> Adapter;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strList);
        ListView list = (ListView) findViewById(R.id.list);
        list.setAdapter(Adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent.putExtra("LATITUDE_KEY", arrayList.get((int)id).getLatitude());
                intent.putExtra("LONGITUDE_KEY",arrayList.get((int)id).getLongitude());
                intent.putExtra("DETAIL_KEY", arrayList.get((int)id).getPrint());
                startActivity(intent);
            }
        });

    }




}
