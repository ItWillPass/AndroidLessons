package ru.mirea.vorobev.looper;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;

import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {
    MyLooper myLooper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myLooper = new MyLooper();
        myLooper.start();
    }
    public void onClick(View view) {
        Message msg = new Message();
        Bundle bundle = new Bundle();
        bundle.putString("KEY", "mirea");
        msg.setData(bundle);
        if (myLooper != null) {
            myLooper.handler.sendMessage(msg);
        }

        final int[] delayTime = {1};
        Runnable runnable = new Runnable() {
            public void run() {
                try {
                    Log.d("Looper","Cпециалист Воробьев И.А., работаю DevOps инженером, мне "+ delayTime[0]+" год ");
                    TimeUnit.SECONDS.sleep(delayTime[0]);
                    delayTime[0] +=1;
                    TimeUnit.SECONDS.sleep(delayTime[0]);
                    Log.d("Looper","Cпециалист Воробьев И.А., работаю DevOps инженером, мне "+ delayTime[0]+" год ");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread = new Thread(runnable);
        thread.start();
    }
}