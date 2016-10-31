package com.example.myapplication;

/**
 * Created by user on 2016-10-31.
 */

public class MyData {

    private String date;
    private String time;
    private String address;
    private double latitude;
    private double longitude;

    public MyData(String d, String t, String ad, double latitude, double longitude){
        date = d;
        time = t;
        address = ad;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getDate() {
        return date;
    }
    public String getTime() {
        return time;
    }
    public String getAddress() {
        return address;
    }

    public double getLatitude(){
        return latitude;
    }

    public double getLongitude(){
        return longitude;
    }

    public String getPrint() {
        return date + " " + time + "\n" + address + "\n" +
                "위도: " + Double.toString(latitude) + " , 경도: " + Double.toString(longitude);
    }

}
