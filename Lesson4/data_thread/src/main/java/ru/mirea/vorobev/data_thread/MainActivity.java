package ru.mirea.vorobev.data_thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.textView);

        // запуск операций с использованием различных методов
        runOnUiThreadExample();
        postDelayedExample();
        postExample();
    }

    // пример использования метода runOnUiThread
    private void runOnUiThreadExample() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                textView.setText("Пример с использованием метода runOnUiThread");
            }
        });
    }

    // пример использования метода postDelayed
    private void postDelayedExample() {
        textView.postDelayed(new Runnable() {
            @Override
            public void run() {
                textView.setText("Пример с использованием метода postDelayed");
            }
        }, 3000); // задержка в 3 секунды (3000 миллисекунд)
    }

    // пример использования метода post
    private void postExample() {
        textView.post(new Runnable() {
            @Override
            public void run() {
                textView.setText("Пример с использованием метода post");
            }
        });
    }
}
