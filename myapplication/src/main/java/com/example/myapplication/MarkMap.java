package com.example.myapplication;

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

import java.util.ArrayList;


public class MarkMap extends Activity implements OnMapReadyCallback {

    static final LatLng SEOUL = new LatLng(37.56, 126.97);
    private GoogleMap googleMap;
    ArrayList<Double> latList;
    ArrayList<Double> longList;
    @Override

    public void onMapReady(final GoogleMap map) {
        googleMap = map;
        Intent intent = getIntent();
        latList =  (ArrayList<Double>) getIntent().getSerializableExtra("latitude");
        longList =  (ArrayList<Double>) getIntent().getSerializableExtra("longitude");

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            return;
        }
        googleMap.setMyLocationEnabled(true);
        for(int i=1; i<latList.size() - 1; i++) {
            LatLng tmp = new LatLng(latList.get(i), longList.get(i));
            map.addMarker(new MarkerOptions()
                    .position(tmp)
                    .title(Integer.toString(i)));
        }
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom( SEOUL, 15));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);

      MapFragment mapFragment = (MapFragment) getFragmentManager()
              .findFragmentById(R.id.map);
      mapFragment.getMapAsync(this);
    }


}
