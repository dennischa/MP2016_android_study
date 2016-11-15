package com.example.mylogger2;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {

    String ptr;
    static TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        textView = (TextView) findViewById(R.id.textView);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void onClickWriteTask(View v){
        Intent intent = new Intent(this, WriteTask.class);
        startActivity(intent);
    }

}