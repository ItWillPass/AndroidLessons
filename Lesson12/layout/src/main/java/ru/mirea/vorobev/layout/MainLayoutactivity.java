package ru.mirea.vorobev.layout;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

public class MainLayoutactivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.constraint_layout);
        TextView myTextView = (TextView) findViewById(R.id.textView2);
        myTextView.setText("New text in MIREA");
        Button button = findViewById(R.id.button13);
        button.setText("MireaButton");
        CheckBox checkBox = findViewById(R.id.checkBox2);
        checkBox.setChecked(true);
    }
}