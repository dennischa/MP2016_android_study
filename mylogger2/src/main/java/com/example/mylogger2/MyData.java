package com.example.mylogger2;

/**
 * Created by user on 2016-11-15.
 */

public class MyData {
    private String date;
    private String time;
    private String address;
    private String act;
    private String detail;
    private double latitude;
    private double longitude;

    public  MyData(){
    }

    public MyData(String d, String t, String ad, String ac, String de,
                  double latitude, double longitude){
        date = d;
        time = t;
        address = ad;
        act = ac;
        detail = de;
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
    public String getAct() { return act;}
    public String getDetail() { return detail;}
    public double getLatitude(){  return latitude;    }
    public double getLongitude(){
        return longitude;
    }
}
