package ru.mirea.vorobev.securesharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private ImageView poetImage;
    private TextView poetName;

    private Button saveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poetImage = findViewById(R.id.poetImage);
        poetName = findViewById(R.id.poet_name);
        saveButton = findViewById(R.id.button);

        // Получаем имя любимого поэта из SharedPreferences
        SharedPreferences preferences = getSharedPreferences("MyPreferences", MODE_PRIVATE);
        String favoritePoetName = preferences.getString("favorite_poet_name", "");

        // Отображаем имя поэта на экране
        poetName.setText(favoritePoetName);
        saveButton.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick (View v){
            SharedPreferences.Editor editor = preferences.edit();
            editor.putString("secure", "Том Круз");
            editor.apply();
            Toast.makeText(MainActivity.this, "Данные сохранены", Toast.LENGTH_SHORT).show();
        }
        });
    }

}