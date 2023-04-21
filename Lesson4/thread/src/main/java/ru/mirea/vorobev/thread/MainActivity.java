package ru.mirea.vorobev.thread;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int counter = 0;
    TextView resultTextView;
    EditText lessonsEdit;
    EditText daysEdit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView infoTextView = findViewById(R.id.textView);
        resultTextView = findViewById(R.id.textView2);
        lessonsEdit = findViewById(R.id.lessonsEdit);
        daysEdit = findViewById(R.id.daysEdit);
        Thread mainThread = Thread.currentThread();
        infoTextView.setText("Текущий поток: " + mainThread.getName());
        mainThread.setName("MireaThread");
        infoTextView.append("\n Новое имя потока: " + mainThread.getName());
    }
    public void onClick(View view) {
        Runnable runnable = new Runnable() {
            public void run() {
                int numberThread = counter++;
                Log.i("ThreadProject", String.format("Запущен поток No %d студентом группы № %s номер по списку № 6 %d ", numberThread, "БСБО-17-20", -1));
                long endTime = System.currentTimeMillis()
                        + 20 * 1000;
                while (System.currentTimeMillis() < endTime) {
                    synchronized (this) {
                        try {
                            wait(endTime -
                                    System.currentTimeMillis());
                        } catch (Exception e) {
                        }
                    }
                }
                Log.i("ThreadProject", "Выполнен поток № " + numberThread);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
    public void getCount(View view)
    {
        Runnable runnable = new Runnable() {
            public void run() {
                double result =Double.parseDouble(lessonsEdit.getText().toString())/Double.parseDouble(daysEdit.getText().toString());
                Log.i("ThreadProject"," Среднее количество пар в день = "+result);
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}