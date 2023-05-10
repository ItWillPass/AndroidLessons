package ru.mirea.vorobev.securesharedpreferences;

import androidx.appcompat.app.AppCompatActivity;
import androidx.security.crypto.EncryptedSharedPreferences;
import androidx.security.crypto.MasterKeys;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.security.keystore.KeyGenParameterSpec;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class MainActivity extends AppCompatActivity {
    private ImageView poetImage;
    private TextView poetName;
    private SharedPreferences secureSharedPreferences;

    private String hide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        poetImage = findViewById(R.id.poetImage);
        poetName = findViewById(R.id.poet_name);
        try{
            KeyGenParameterSpec keyGenParameterSpec = MasterKeys.AES256_GCM_SPEC;
            String mainKeyAlias = MasterKeys.getOrCreate(keyGenParameterSpec);
            secureSharedPreferences = EncryptedSharedPreferences.create(
                    "MyPreferences",
                    mainKeyAlias,
                    getBaseContext(),
                    EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                    EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM);
        secureSharedPreferences.edit().putString("secure", "Том Круз").apply();
        }
         catch (GeneralSecurityException | IOException e) {
             throw new RuntimeException(e);
         }
        hide = secureSharedPreferences.getString("secure", "Том Круз");
        poetName.setText(hide);

    }

}
