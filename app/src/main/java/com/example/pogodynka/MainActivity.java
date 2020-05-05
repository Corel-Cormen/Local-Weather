package com.example.pogodynka;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pogodynka.weatherStatistic.WeatherResponse;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCity();

        Button button = findViewById(R.id.button);
        if (!internet()) {
            button.setEnabled(false);
            Toast.makeText(getApplicationContext(), "Brak połączenia z internetem", Toast.LENGTH_SHORT).show();
            System.out.println("Brak polaczenia");
        } else {
            button.setEnabled(true);
            System.out.println("Uzyskano polaczenie");
        }
    }

    public void goToWeather(View view) {
        EditText editText = findViewById(R.id.myCity);
        String myCity = editText.getText().toString();

        Intent intent = new Intent(this, WeatherActivity.class);
        intent.putExtra("KEY_MY_CITY", myCity);
        saveCity(myCity);

        startActivity(intent);
    }

    private void saveCity(String output) {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("KEY_MY_CITY", output);
        editor.apply();
    }

    private void loadCity() {
        SharedPreferences sharedPreferences = getSharedPreferences("shared preferences", MODE_PRIVATE);
        String date = sharedPreferences.getString("KEY_MY_CITY", "");
        TextView textView = findViewById(R.id.myCity);
        textView.setText(date);
    }

    private boolean internet(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetwork() != null;
    }
}
