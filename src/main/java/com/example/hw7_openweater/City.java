package com.example.hw7_openweater;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class City extends AppCompatActivity {

    private String c_in;
    private EditText result;
    private static final String API_KEY="e3fed2d026dba4e952cf2300145e65c1";
    String url1="api.openweathermap.org/data/2.5/weather?q=&appid=";
    String url2 ="api.openweathermap.org/data/2.5/weather?zip=&appid=";
    String url3= "api.openweathermap.org/data/2.5/weather?lat=&lon=&appid=";


    class Weather extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... address) {
            //String... means multiple address can be send. It acts as array
            String content;
            try {
                Intent intent = getIntent();
                String c_in = intent.getStringExtra(MainActivity.EXTRA_TEXT);
                URL url = new URL("https://api.openweathermap.org/data/2.5/weather?q="+ c_in +"&units=imperial&appid=e3fed2d026dba4e952cf2300145e65c1");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                //Establish connection with address
                connection.connect();

                //retrieve data from url
                InputStream is = connection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);

                //Retrieve data and return it as String
                int data = isr.read();
                content = "";
                char ch;
                while (data != -1) {
                    ch = (char) data;
                    content = content + ch;
                    data = isr.read();
                }
                return content;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }
    }
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city);
        Intent intent = getIntent();
        String c_in = intent.getStringExtra(MainActivity.EXTRA_TEXT);
      //  c_in = (EditText) findViewById(R.id.city_in);
        result = (EditText) findViewById(R.id.results);

                if (c_in.equals("")) {

                    result.setText(String.valueOf("Please enter a City"));

                } else {

                    String content;

                    City.Weather weather = new City.Weather();
                    try {


                        content = weather.execute("https://api.openweathermap.org/data/2.5/weather?q={" + c_in + "}&units=imperial&appid=e3fed2d026dba4e952cf2300145e65c1").get();
                        //First we will check data is retrieve successfully or not
                        Log.i("contentData", content);

                        JSONObject jsonObject = new JSONObject(content);
                        String weatherData = jsonObject.getString("weather");
                        String mainTemperature = jsonObject.getString("main");
                        String city_id= jsonObject.getString("id");
                        String city_name= jsonObject.getString("name");
                        double visibility, lat, lon, pressure, temp, humidity, wind_speed;
                        //weather data is in Array
                        JSONArray array = new JSONArray(weatherData);

                        String main = "";
                        String description = "";
                        String temperature = "";




                        for (int i = 0; i < array.length(); i++) {
                            JSONObject weatherPart = array.getJSONObject(i);
                            main = weatherPart.getString("main");
                            description = weatherPart.getString("description");
                        }
                        JSONObject sys = jsonObject.getJSONObject("sys");
                        JSONObject coordinates = jsonObject.getJSONObject("coord");
                        JSONObject wind = jsonObject.getJSONObject("wind");
                        JSONObject mainPart = new JSONObject(mainTemperature);
                        temperature = mainPart.getString("temp");
                        pressure = Double.valueOf(mainPart.getString("pressure"));
                        humidity = Double.valueOf(mainPart.getString("humidity"));
                        wind_speed = wind.getDouble("speed");
                        lat = coordinates.getDouble("lat");
                        lon = coordinates.getDouble("lon");
                        String country = sys.getString("country");
                        String city = jsonObject.getString("name");



                        visibility = Double.parseDouble(jsonObject.getString("visibility"));
//By default visibility is in meter
                        int visibiltyInKilometer = (int) visibility / 1000;
                        result.setText("City Name: "+ city + "\nCountry: "+ country + "\nTemperature: " + temperature +"\u00B0F" + "\nCity ID: " + city_id + "\nPressure: " + pressure + "\nHumidity: " + humidity +  "\nLatitude: " + lat + "\nLongitude: " + lon + "\nWind Speed: " + wind_speed + "\nDescription: " + description);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

        }
};


