package ru.mirea.vorobev.mireaproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import androidx.fragment.app.Fragment;

import ru.mirea.vorobev.mireaproject.MapFragment;
import ru.mirea.vorobev.mireaproject.EstablishmentsFragment;


public class EstablishmentsFragment extends Fragment implements View.OnClickListener{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    public EstablishmentsFragment() {
        // Required empty public constructor
    }

    public static EstablishmentsFragment newInstance(String param1, String param2) {
        EstablishmentsFragment establishmentsFragment = new EstablishmentsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        establishmentsFragment.setArguments(args);
        return establishmentsFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    @Override
    public void onStop(){
        super.onStop();
    }
    @Override
    public void onStart(){
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_establishments, container, false);
        Button openMapButton = root.findViewById(R.id.button2);
        openMapButton.setOnClickListener(this);
        return root;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), MapFragment.class);
        startActivity(intent);
    }
}