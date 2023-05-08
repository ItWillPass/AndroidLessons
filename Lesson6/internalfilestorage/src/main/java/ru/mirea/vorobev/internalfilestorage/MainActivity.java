package ru.mirea.vorobev.internalfilestorage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String fileName = "mirea.txt";
    private TextView tv;
    private EditText editTextHistoryDate;
    private Button saveButton;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.textView);
        String string = "Hello mirea!";
        FileOutputStream outputStream;
        editTextHistoryDate = findViewById(R.id.editTextHistoryDate);
        saveButton = findViewById(R.id.saveButton);
        preferences = getSharedPreferences("MySaveVorobev", MODE_PRIVATE);
        editTextHistoryDate.setText(preferences.getString("Описание", ""));
        try {
            outputStream = openFileOutput(fileName, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            public void run() {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tv.post(new Runnable() {
                    public void run() {
                        tv.setText(getTextFromFile());
                    }
                });
            }
        }).start();

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Date", "09.05.1945");
                editor.putString("history", editTextHistoryDate.getText().toString());
                editor.apply();
                Toast.makeText(MainActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
            }
        });
    }
    // открытие файла
    public String getTextFromFile() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(fileName);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            Log.d(LOG_TAG, text);
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return null;
    }

}