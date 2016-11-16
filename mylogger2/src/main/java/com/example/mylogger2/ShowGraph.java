package com.example.mylogger2;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.LinearLayout;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import java.util.ArrayList;

/**
 * Created by user on 2016-11-16.
 */

public class ShowGraph extends Activity {
    static DataBaseOpen dataBaseOpen;
    static SQLiteDatabase db;
    ArrayList<MyData> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        dataBaseOpen = new DataBaseOpen(this);
        db = dataBaseOpen.getWritableDatabase();

        readTable();

       int [] count = new int[6];
       for(int i = 0; i < 6; i++)
            count[i] = 0;
        for(int i = 0; i < arrayList.size(); i++){
            switch (arrayList.get(i).getAct()){
                case "식사":
                    count[0]++;
                    break;
                case "공부":
                    count[1]++;
                    break;
                case "운동":
                    count[2]++;
                    break;
                case "사교활동":
                    count[3]++;
                    break;
                case "기타":
                    count[4]++;
                    break;
                case "none":
                    count[5]++;
                    break;
            }
        }
        LinearLayout imageView = (LinearLayout) findViewById(R.id.chart_area);
        DefaultRenderer renderer = new DefaultRenderer(); // 그래프를 그릴 랜더러 선언

        CategorySeries series = new CategorySeries("그래프내용");

        series.add( "식사", count[0]);
        series.add( "공부", count[1]);
        series.add( "운동", count[2]);
        series.add( "사교활동", count[3]);
        series.add( "기타", count[4]);
        series.add( "미선택", count[5]);

        renderer.setLabelsTextSize(15);
        renderer.setLegendTextSize(25);

        int[] colors = { Color.BLUE, Color.GREEN, Color.MAGENTA, Color.YELLOW , Color.BLACK, Color.CYAN }; // 그래프
        // 컬러
        for (int i = 0; i < colors.length; i++) {
            SimpleSeriesRenderer r = new SimpleSeriesRenderer();
            r.setColor(colors[i]);
            renderer.addSeriesRenderer(r);
        }

        GraphicalView view = ChartFactory.getPieChartView(this, series,
                renderer);
        imageView.addView(view);
    }

    public void readTable() {
        arrayList = new ArrayList<MyData>();
        String sql = "select * from t_table";
        Cursor results = db.rawQuery(sql, null);
        results.moveToFirst();

        while (!results.isAfterLast()) {
            String date = results.getString(1);
            String time = results.getString(2);
            String address = results.getString(3);
            String act = results.getString(4);
            String detail = results.getString(5);
            double latitude = results.getDouble(6);
            double longitude = results.getDouble(7);

            arrayList.add(new MyData(date, time, address, act, detail,latitude, longitude));
            results.moveToNext();
        }
        results.close();
    }
}