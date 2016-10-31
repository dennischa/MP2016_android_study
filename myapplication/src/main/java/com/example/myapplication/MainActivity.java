package com.example.myapplication;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    static DataBaseOpen dataBaseOpen;
    static SQLiteDatabase db;
    ArrayList<MyData> arrayList;
    ArrayList<String> strList;

    static TextView textView;
    static Button updateButton;
    static Button listButton;

    long now = System.currentTimeMillis();
    Date date = new Date(now);
    SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
    SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH시 mm분");
    String strCurDate = CurDateFormat.format(date);
    String strCurTime = CurTimeFormat.format(date);

    String address;

    private MyLocation location;
    double latitude = 0;
    double longitude = 0;
    double count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //db 생성
        dataBaseOpen = new DataBaseOpen(this);
        db = dataBaseOpen.getWritableDatabase();

        textView = (TextView) findViewById(R.id.textView);
        location = new MyLocation(this);
        updateButton = (Button) findViewById(R.id.updateButton);
        listButton = (Button) findViewById(R.id.getlist);

        if (location.isGetLocation()) {
            //dataBaseOpen.onUpgrade(db,0,1);
            latitude = location.getLatitude();
            longitude = location.getLongitude();
           // textView.setText(Double.toString(latitude) + " and "  + Double.toString(longitude));
            textView.setText(getAddress(latitude, longitude));
            insertData(strCurDate,strCurTime,address,latitude,longitude);
        } else {
            textView.setText("GPS 를 켜주세요.");
            location.showSettingsAlert();
        }



        listButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
            listPrint();
            }
        });


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = new MyLocation(MainActivity.this);

                if (location.isGetLocation()) {
                    //시간
                    now = System.currentTimeMillis();
                    date = new Date(now);
                    strCurTime = CurTimeFormat.format(date);
                    strCurDate = CurDateFormat.format(date);
                    //위치
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    address = getAddress(latitude,longitude);
                    insertData(strCurDate,strCurTime,address,latitude,longitude);

                    //textView.setText(getAddress(latitude, longitude));

                } else {
                    latitude = 0;
                    longitude = 0;
                    textView.setText("GPS 를 켜주세요.");
                    location.showSettingsAlert();
                }
            }
        });
    }

    public String getAddress(double latitude, double longitude){
        String str = "주소를 찾는 중 입니다.";
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> list;

        try {
            if (geocoder != null) {
                list = geocoder.getFromLocation(latitude, longitude, 1);
                if (list != null && list.size() > 0) {
                    str = list.get(0).getAddressLine(0).toString();
                }
                return str;
            }
        } catch (IOException e) {
            return str;
        }
        return str;
    }

    public void insertData(String date, String time, String address, double latitude, double longitude){
        db.execSQL("INSERT INTO db_table "
                + "VALUES(NULL, '" + date
                + "', '" + time
                + "', '" + address
                + "', '" + latitude
                + "', '" + longitude
                + "');");
    }

    //모든 데이터 읽기
    public void readTable(){
        arrayList = new ArrayList<MyData>();
        String sql = "select * from db_table";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            String date =results.getString(1);
            String time = results.getString(2);
            String address =results.getString(3);

            double latitude = results.getDouble(4);
            double longitude = results.getDouble(5);

            arrayList.add(new MyData(date,time,address,latitude,longitude));
            results.moveToNext();
        }
        results.close();
    }

    public void listPrint(){
        readTable();
        strList = new ArrayList<String>();
        //최근 5번의 기록 출력
        for(int i=0; i<arrayList.size() ; i++) {
            strList.add(arrayList.get(i).getPrint());
            //if (arrayList.size() == i + 1)
               // break;
        }

        ArrayAdapter<String> Adapter ;
        Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strList);

        ListView list = (ListView)findViewById(R.id.list);
        list.setAdapter(Adapter);

    }
}