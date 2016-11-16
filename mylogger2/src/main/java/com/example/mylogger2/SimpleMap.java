package com.example.mylogger2;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * Created by user on 2016-11-16.
 */
public class SimpleMap extends Activity implements OnMapReadyCallback{

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    double latitude, longitude;
    String detail;
    private GoogleMap googleMap;
    @Override

    public void onMapReady(final GoogleMap map)  {
        googleMap = map;
        Intent intent = getIntent();
        latitude =  intent.getDoubleExtra("LATITUDE_KEY",0.00);
        longitude = intent.getDoubleExtra("LONGITUDE_KEY",0.00);
        detail = intent.getStringExtra("DETAIL_KEY");
        LatLng tmp = new LatLng(latitude,longitude);

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( tmp, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
        map.addMarker(new MarkerOptions()
                .position(tmp)
                .title("일과 위치")
                .snippet(detail));

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplemap);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

}