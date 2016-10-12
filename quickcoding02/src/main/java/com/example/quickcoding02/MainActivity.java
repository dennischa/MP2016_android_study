package com.example.quickcoding02;
// sample
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int myNumber = 333;
    int start = 1;
    int end = 500;
    int count = 0;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        final TextView tvText = (TextView) findViewById(R.id.textView);
        tvText.setText(String.valueOf(reasoning()).concat("??"));

        Button big = (Button) findViewById(R.id.big);
        big.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                start = reasoning();
                tvText.setText(String.valueOf(reasoning()).concat("??"));
                count++;
            }
        });

        Button small = (Button) findViewById(R.id.small);
        small.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                end = reasoning();
                tvText.setText(String.valueOf(reasoning()).concat("??"));
                count++;
            }
        });

        Button bingo = (Button) findViewById(R.id.bingo);
        bingo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                count++;
                tvText.setText("Congratuation! count :".concat(String.valueOf(count)));
            }
        });


    }

    public int reasoning()
    {
        return (start + end) / 2;
    }
}
