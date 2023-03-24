package ru.mirea.vorobev.multiactivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SecondActivity2 extends AppCompatActivity {

    private TextView textViewName;



    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second2);
       textViewName = (TextView) findViewById(R.id.textView3);
        String name = getIntent().getStringExtra("name");
        textViewName.setText(name);
    }

}
