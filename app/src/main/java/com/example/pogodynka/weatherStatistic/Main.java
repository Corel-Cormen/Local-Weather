package com.example.pogodynka.weatherStatistic;

import com.google.gson.annotations.SerializedName;

public class Main {
    @SerializedName("temp")
    private float temp;
    @SerializedName("humidity")
    private float humidity;
    @SerializedName("pressure")
    private float pressure;
    @SerializedName("temp_min")
    private float temp_min;
    @SerializedName("temp_max")
    private float temp_max;

    public float getTemp() {
        return temp;
    }

    public float getHumidity() {
        return humidity;
    }

    public float getPressure() {
        return pressure;
    }

    public float getTemp_min() {
        return temp_min;
    }

    public float getTemp_max() {
        return temp_max;
    }

    @Override
    public String toString() {
        return "Main{" +
                "temp=" + temp +
                ", humidity=" + humidity +
                ", pressure=" + pressure +
                ", temp_min=" + temp_min +
                ", temp_max=" + temp_max +
                '}';
    }
}
