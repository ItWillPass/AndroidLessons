package ru.mirea.vorobev.cryptoloader;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText mInputEditText;
    private Button mEncryptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mInputEditText = findViewById(R.id.edit_text_input);
        mEncryptButton = findViewById(R.id.button_encrypt);

        mEncryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String inputString = mInputEditText.getText().toString();

                // Здесь нужно выполнить шифрование введенной строки с помощью алгоритма AES
                String encryptedString = encryptString(inputString);

                // Создаем новый экземпляр нашего Loader и передаем ему зашифрованную строку
                Bundle bundle = new Bundle();
                bundle.putString("encrypted_string", encryptedString);

                getSupportLoaderManager().initLoader(0, bundle, new LoaderManager.LoaderCallbacks<String>() {
                    @NonNull
                    @Override
                    public Loader<String> onCreateLoader(int id, @Nullable Bundle args) {
                        return new DecryptLoader(MainActivity.this, args.getString("encrypted_string"));
                    }

                    @Override
                    public void onLoadFinished(@NonNull Loader<String> loader, String decryptedString) {
                        // Дешифрованная строка передается в Toast
                        Toast.makeText(MainActivity.this, decryptedString, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onLoaderReset(@NonNull Loader<String> loader) {

                    }
                });
            }
        });
    }

    private String encryptString(String inputString) {
        // Здесь нужно выполнить шифрование введенной строки с помощью алгоритма AES
        return "encrypted_string";
    }
}
