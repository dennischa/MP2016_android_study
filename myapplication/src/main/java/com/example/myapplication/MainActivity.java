package com.example.myapplication;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static TextView textView;
    static Button updateButton;

    private MyLocation location;
    double latitude = 0;
    double longitude = 0;
    double count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

         textView = (TextView) findViewById(R.id.textView);
        location = new MyLocation(this);
        updateButton = (Button) findViewById(R.id.updateButton);

        if (location.isGetLocation()) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            textView.setText(Double.toString(latitude) + " and "  + Double.toString(longitude));
            //textView.setText(getAddress(latitude, longitude));
        } else {
            textView.setText("GPS 를 켜주세요.");
            location.showSettingsAlert();
        }


        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                location = new MyLocation(MainActivity.this);

                if (location.isGetLocation()) {
                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    System.out.println(latitude);
                    textView.setText(Double.toString(latitude) + " and " + Double.toString(longitude));

                   // textView.setText(getAddress(latitude, longitude));
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
}

