package com.example.pogodynka.weatherStatistic;

import com.google.gson.annotations.SerializedName;

public class Wind {
    @SerializedName("speed")
    private float speed;
    @SerializedName("deg")
    private float deg;

    public float getSpeed() {
        return speed;
    }

    public float getDeg() {
        return deg;
    }

    @Override
    public String toString() {
        return "Wind{" +
                "speed=" + speed +
                ", deg=" + deg +
                '}';
    }
}
