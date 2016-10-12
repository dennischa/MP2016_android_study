package com.example.quickcoding01;

public class MaxValues extends MyValues {
    public int getValue() {
        int max = super.values[0];

        for (int i = 1; i < super.values.length; i++) {
            if (super.values[i] > max) {
                max = super.values[i];
            }
        }
        return max;
    }
}
