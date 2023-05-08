package ru.mirea.vorobev.lesson6;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText groupEditText;
    private EditText numberEditText;
    private EditText filmEditText;
    private Button saveButton;

    private SharedPreferences preferences;
    @Override
    protected void onResume() {
        super.onResume();

        groupEditText.setText(preferences.getString("group", ""));
        numberEditText.setText(preferences.getString("number", ""));
        filmEditText.setText(preferences.getString("film", ""));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        groupEditText = findViewById(R.id.groupEditText);
        numberEditText = findViewById(R.id.numberEditText);
        filmEditText = findViewById(R.id.filmEditText);
        saveButton = findViewById(R.id.saveButton);

        preferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);

        groupEditText.setText(preferences.getString("Группа", ""));
        numberEditText.setText(preferences.getString("Номер", ""));
        filmEditText.setText(preferences.getString("Фильм", ""));


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("group", groupEditText.getText().toString());
                editor.putString("number", numberEditText.getText().toString());
                editor.putString("film", filmEditText.getText().toString());
                editor.apply();
                Toast.makeText(MainActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
            }
        });

    }
}