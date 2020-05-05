package com.example.pogodynka;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.pogodynka.weatherStatistic.Main;
import com.example.pogodynka.weatherStatistic.Weather;
import com.example.pogodynka.weatherStatistic.WeatherResponse;
import com.squareup.picasso.Picasso;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WeatherActivity extends AppCompatActivity {

    public static String BaseUrl = "https://api.openweathermap.org/";
    public static String AppId = "749561a315b14523a8f5f1ef95e45864";
    public static String units = "metric";
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather);

        final SwipeRefreshLayout swipeRefresh;

        Intent intent = getIntent();
        String city = intent.getStringExtra("KEY_MY_CITY");

        imageView = findViewById(R.id.imageWeather);
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
        WeatherService service = retrofit.create(WeatherService.class);
        Call<WeatherResponse> call = service.getCurrentWeatherData(city, units, AppId);

        CreateWeather(call);

        swipeRefresh = findViewById(R.id.pullToRefresh);
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Intent intent = getIntent();
                String city = intent.getStringExtra("KEY_MY_CITY");

                imageView = findViewById(R.id.imageWeather);
                Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                WeatherService service = retrofit.create(WeatherService.class);
                Call<WeatherResponse> call = service.getCurrentWeatherData(city, units, AppId);
                CreateWeather(call);
                System.out.println("Udane odświerzenie");
                swipeRefresh.setRefreshing(false);
            }
        });

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            while (true) {
                                Intent intent = getIntent();
                                String city = intent.getStringExtra("KEY_MY_CITY");

                                imageView = findViewById(R.id.imageWeather);
                                Retrofit retrofit = new Retrofit.Builder().baseUrl(BaseUrl).addConverterFactory(GsonConverterFactory.create()).build();
                                WeatherService service = retrofit.create(WeatherService.class);
                                Call<WeatherResponse> call = service.getCurrentWeatherData(city, units, AppId);
                                CreateWeather(call);
                                System.out.println("Udane odswierzenie co 5 minut");
                            }
                        }
                    }, 300000);
            }
        });
    }

    public void CreateWeather(Call call) {
        call.enqueue(new Callback<WeatherResponse>() {
            @Override
            public void onResponse(Call<WeatherResponse> call, Response<WeatherResponse> response) {
                if (response.code() == 200) {
                    WeatherResponse weatherResponse = response.body();
                    assert weatherResponse != null;

                    TextView city = findViewById(R.id.city);
                    city.setText(weatherResponse.getName());

                    TextView clock = findViewById(R.id.clock);
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.HOUR, weatherResponse.getTimezone() / 3600);
                    Date date = calendar.getTime();
                    String timeString = simpleDateFormat.format(date);
                    clock.setText(timeString);

                    Main main = weatherResponse.getMain();
                    TextView temp = findViewById(R.id.temp);
                    temp.setText(Integer.toString(Math.round(main.getTemp())) + "°C");

                    TextView tempmin = findViewById(R.id.tempMin);
                    tempmin.setText(Integer.toString(Math.round(main.getTemp_min())) + "°C");

                    TextView tempmax = findViewById(R.id.tempMax);
                    tempmax.setText(Integer.toString(Math.round(main.getTemp_max())) + "°C");

                    TextView pressure = findViewById(R.id.pressure);
                    pressure.setText(Integer.toString(Math.round(main.getPressure())) + "HPa");

                    TextView humidity = findViewById(R.id.humidity);
                    humidity.setText(Integer.toString(Math.round(main.getHumidity())) + "%");

                    Weather weather = weatherResponse.getWeather().get(0);
                    String iconUrl = "https://openweathermap.org/img/w/" + weather.getIcon() + ".png";
                    System.out.println(iconUrl);
                    loadImageFromUrl(iconUrl);
                } else {
                    navigateUpTo(new Intent(getBaseContext(), MainActivity.class));
                }
            }

        @Override
        public void onFailure(Call<WeatherResponse> call, Throwable t) {
        }

        public void loadImageFromUrl(String url) {
            Picasso.get().load(url).into(imageView);
        }
        });
    }
}
