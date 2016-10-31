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

    public  MyData(){
    }

    public MyData(String d, String t, String ad,
                  double latitude, double longitude){
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

   /* public MyData(Parcel in) {
        readFromParcel(in);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(date);
        dest.writeString(time);
        dest.writeString(address);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
    }

    private void readFromParcel(Parcel in){
        date= in.readString();
        time = in.readString();
        address = in.readString();
        latitude = in.readDouble();
        longitude = in.readDouble();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public MyData createFromParcel(Parcel in) {
            return new MyData(in);
        }

        public MyData[] newArray(int size) {
            return new MyData[size];
        }
    };*/

}
