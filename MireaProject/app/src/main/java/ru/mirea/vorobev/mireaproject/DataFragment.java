package ru.mirea.vorobev.mireaproject;


import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;


import androidx.fragment.app.Fragment;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;


public class DataFragment extends Fragment {
    private TextView tTextView;
    private TextView dTextView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_data, container, false);
        // Настройка отображения элементов и добавление обработчиков событий
        tTextView = view.findViewById(R.id.title);
        dTextView = view.findViewById(R.id.description);
        tTextView.setText("Игры");
        dTextView.setText("Doodle Jump");
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.UNMETERED)
                //.setRequiresCharging(true)
                .build();
        WorkRequest uploadWorkRequest =
                new OneTimeWorkRequest.Builder(MyWorker.class)
                        .setConstraints(constraints)
                        .build();
        WorkManager
                .getInstance()
                .enqueue(uploadWorkRequest);
        return view;

    }


}
