package ru.mirea.vorobev.intentapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SecondActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_main);

        // получаем переданное время из объекта Intent
        String currentTime = getIntent().getStringExtra("dateString");

        // формируем строку для отображения в textView
        String text = "КВАДРАТ ЗНАЧЕНИЯ МОЕГО НОМЕРА ПО СПИСКУ В ГРУППЕ СОСТАВЛЯЕТ 36, а текущее время " + currentTime;

        // находим textView в макете
        TextView textView = (TextView) findViewById(R.id.textView2);

        // устанавливаем текст в textView
        textView.setText(text);
    }
}
