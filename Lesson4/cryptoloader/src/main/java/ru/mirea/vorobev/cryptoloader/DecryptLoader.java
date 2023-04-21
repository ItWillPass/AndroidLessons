package ru.mirea.vorobev.cryptoloader;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class DecryptLoader extends AsyncTaskLoader<String> {

    private String mEncryptedString;

    public DecryptLoader(@NonNull Context context, String encryptedString) {
        super(context);
        mEncryptedString = encryptedString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        // Здесь нужно выполнить дешифровку зашифрованной строки с помощью алгоритма AES
        return "decrypted_string";
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }
}

