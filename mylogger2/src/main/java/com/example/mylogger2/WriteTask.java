package com.example.mylogger2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by user on 2016-11-15.
 */

public class WriteTask extends Activity {
    static DataBaseOpen dataBaseOpen;
    static SQLiteDatabase db;


    double latitude = 0;
    double longitude = 0;
    double tmp = -1;
    String actString = "none";
    String addressString;
    String detailString = "none";
    String dateString= "";
    String timeString ="";
    boolean checkL = true;
    boolean checkT = true;
    CheckBox checkboxT;
    CheckBox checkboxL;

    Intent mapIntent;
    int mYear, mMonth, mDay, mHour, mMinute;
    int sYear = 0;
    int sMonth= 0, sDay = 0, sHour= 0,sMinute= 0;
    static Button actButton;
    private MyLocation location;


    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_task);

        //db생성
        dataBaseOpen = new DataBaseOpen(this);
        db = dataBaseOpen.getWritableDatabase();


        location = new MyLocation(this);

        GregorianCalendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay  = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);


        if (location.isGetLocation()) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
        } else {
            location.showSettingsAlert();
        }


        findViewById(R.id.manualDateButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(WriteTask.this, dateSetListener, mYear, mMonth, mDay).show();
            }
        });

        findViewById(R.id.manualTimeButton).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new TimePickerDialog(WriteTask.this, timeSetListener, mHour, mMinute, false).show();
            }
        });



        //일과종류
        actButton = (Button)findViewById(R.id.ActButton);
        registerForContextMenu(actButton);

        //체크박스
       checkboxT = (CheckBox) findViewById(R.id.checkBoxLocation);
       checkboxL = (CheckBox) findViewById(R.id.checkBoxTime);

        findViewById(R.id.finishButton).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //현재위치정보확인
                if (checkboxL.isChecked()) {
                    location = new MyLocation(WriteTask.this);
                    if (location.isGetLocation()) {
                        //위치
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                    } else {
                        latitude = 0;
                        longitude = 0;
                        location.showSettingsAlert();
                    }
                }
                //현재시간정보확인
                if (checkboxT.isChecked()) {
                    long now = System.currentTimeMillis();
                    Date date = new Date(now);
                    SimpleDateFormat CurDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일");
                    SimpleDateFormat CurTimeFormat = new SimpleDateFormat("HH시 mm분");
                    dateString = CurDateFormat.format(date);
                    timeString = CurTimeFormat.format(date);
                } else {
                    dateString = Integer.toString(sYear) + "년 " + Integer.toString(sMonth) + "월 " + Integer.toString(sDay) + "일";
                    timeString = Integer.toString(sHour) + "시 " + Integer.toString(sMinute) + "분";
                }

                //Detail
                EditText editText = (EditText) findViewById(R.id.detailInput);

                detailString = editText.getText().toString();

                addressString = getAddress(latitude, longitude);
                insertData(dateString, timeString, addressString, actString, detailString, latitude, longitude);
                finish();

            }

        });

    }

    private DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            sYear = year;
            sMonth = monthOfYear+1;
            sDay = dayOfMonth;
        }
    };

    private TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            // TODO Auto-generated method stub
            sHour = hourOfDay;
            sMinute = minute;
        }
    };

    public void onClickManualLocation(View v){
        mapIntent = new Intent(this, GetLocation.class);
        startActivityForResult(mapIntent, 1);
    }


    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        // 컨텍스트 메뉴가 최초로 한번만 호출되는 콜백 메서드
        menu.setHeaderTitle("일과 종류");
        menu.add(0,1,0,"식사");
        menu.add(0,2,0,"공부");
        menu.add(0,3,0,"운동");
        menu.add(0,4,0,"사교활동");
        menu.add(0,5,0,"기타");
    }

    public boolean onContextItemSelected(MenuItem item) {
        // 롱클릭했을 때 나오는 context Menu 의 항목을 선택(클릭) 했을 때 호출
        switch(item.getItemId()) {
            case 1 :
                actString = "식사";
                return true;
            case 2 :
                actString = "공부";
                return true;
            case 3 :
                actString = "운동";
                return true;
            case 4:
                actString = "사교활동";
                return true;
            case 5 :
                actString = "기타";
                return true;
        }
        return super.onContextItemSelected(item);
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       latitude = data.getDoubleExtra("LATITUDE_KEY",0.00);
       longitude = data.getDoubleExtra("LONGITUDE_KEY",0.00);
    }

   public void insertData(String date, String time,
                           String address, String act, String detail, double latitude, double longitude) {
        db.execSQL("INSERT INTO t_table "
                + "VALUES(NULL, '" + date
                + "', '" + time
                + "', '" + address
                + "', '" + act
                + "', '" + detail
                + "', '" + latitude
                + "', '" + longitude
                + "');");
    }


    public String getAddress(double latitude, double longitude) {
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
            return "error";
        }
        return str;
    }





}
