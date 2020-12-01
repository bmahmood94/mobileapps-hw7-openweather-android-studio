package com.example.hw7_openweater;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_TEXT = "com.example.hw7_openweater.EXTRA_TEXT";
    public static final String EXTRA_NUMBER = "com.example.hw7_openweater.EXTRA_NUMBER";

    private Button City;
    private EditText c_in,result;
    private static final String API_KEY="e3fed2d026dba4e952cf2300145e65c1";
    String url1="api.openweathermap.org/data/2.5/weather?q=&appid=";
    String url2 ="api.openweathermap.org/data/2.5/weather?zip=&appid=";
    String url3= "api.openweathermap.org/data/2.5/weather?lat=&lon=&appid=";




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button1 = (Button) findViewById(R.id.city_btn);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            openCity();
            }
        });

        Button button2 = (Button) findViewById(R.id.zip_btn);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openZip();
            }
        });

    };
    public void openCity() {
        EditText editText1 = (EditText) findViewById(R.id.city_in);
        String text = editText1.getText().toString();
       // EditText editText2 = (EditText) findViewById(R.id.edittext2);
      //  int number = Integer.parseInt(editText2.getText().toString());
        Intent intent = new Intent(this, City.class);
        intent.putExtra(EXTRA_TEXT, text);
        //intent.putExtra(EXTRA_NUMBER, number);
        startActivity(intent);
    }
    public void openZip(){

        EditText editText2 = (EditText) findViewById(R.id.zip_in);
        int number = Integer.parseInt(editText2.getText().toString());
        Intent intent = new Intent(this, Zip.class);
        intent.putExtra(EXTRA_NUMBER, number);
        //intent.putExtra(EXTRA_NUMBER, number);
        startActivity(intent);
    };
    }



