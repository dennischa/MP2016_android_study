package com.example.quickcoding01;

public class MinValues extends MyValues {

    public int getValue() {
        int min = super.values[0];
        for (int i = 1; i < super.values.length; i++) {
            if (super.values[i] < min) {
                min = super.values[i];
            }
        }
        return min;
    }
}
