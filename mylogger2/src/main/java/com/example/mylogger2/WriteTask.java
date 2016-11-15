package com.example.mylogger2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 * Created by user on 2016-11-15.
 */

public class WriteTask extends Activity {
    double latitude = 0;
    double longitude = 0;
    int mYear, mMonth, mDay, mHour, mMinute;
    static Button dateButton;
    static Button timeButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_task);

        dateButton = (Button) findViewById(R.id.manualDateButton);
        timeButton = (Button) findViewById(R.id.manualTimeButton);

        Calendar cal = new GregorianCalendar();
        mYear = cal.get(Calendar.YEAR);
        mMonth = cal.get(Calendar.MONTH);
        mDay  = cal.get(Calendar.DAY_OF_MONTH);
        mHour = cal.get(Calendar.HOUR_OF_DAY);
        mMinute = cal.get(Calendar.MINUTE);

        DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                mYear = year;
                mMonth = monthOfYear;
                mDay =dayOfMonth;
              //  UpdateNow();
            }
        };

        TimePickerDialog.OnTimeSetListener mTimeSetListener =
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        mHour = hourOfDay;
                        mMinute = minute;
                        //UpdateNow();
                    }
                };


    }

    public void onClickManualLocation(View v){
        Intent intent = new Intent(this, GetLocation.class);
        startActivity(intent);
    }


}
