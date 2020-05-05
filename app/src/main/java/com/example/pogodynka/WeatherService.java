package com.example.pogodynka;

import com.example.pogodynka.weatherStatistic.WeatherResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WeatherService {
    @GET("data/2.5/weather?")
    Call<WeatherResponse> getCurrentWeatherData(@Query("q") String city, @Query("units") String metric, @Query("APPID") String app_id);
}
