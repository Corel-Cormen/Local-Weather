package com.example.pogodynka.weatherStatistic;

import com.google.gson.annotations.SerializedName;

public class Clouds {
    @SerializedName("all")
    private float all;

    public float getAll() {
        return all;
    }

    @Override
    public String toString() {
        return "Clouds{" +
                "all=" + all +
                '}';
    }
}
