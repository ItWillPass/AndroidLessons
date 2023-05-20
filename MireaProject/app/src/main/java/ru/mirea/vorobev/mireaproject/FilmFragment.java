package ru.mirea.vorobev.mireaproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class FilmFragment extends Fragment {
    private EditText filmEditText;
    private EditText godEditText;
    private EditText actorEditText;
    private EditText reitingEditText;
    private Button shifrButton;

    private SharedPreferences preferences;
    @Override
    public void onResume() {
        super.onResume();

        filmEditText.setText(preferences.getString("Film", ""));
        godEditText.setText(preferences.getString("Year", ""));
        actorEditText.setText(preferences.getString("Actor", ""));
        reitingEditText.setText(preferences.getString("Reiting", ""));
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_films, container, false);
        filmEditText = view.findViewById(R.id.filmEditText);
        godEditText = view.findViewById(R.id.godEditText);
        actorEditText = view.findViewById(R.id.actorEditText);
        reitingEditText= view.findViewById(R.id.reitingEditText);

        preferences = getActivity().getSharedPreferences("MyFilms", MODE_PRIVATE);

        filmEditText.setText(preferences.getString("Фильм", ""));
        godEditText.setText(preferences.getString("Год", ""));
        actorEditText.setText(preferences.getString("Актер", ""));
        reitingEditText.setText(preferences.getString("Рейтинг", ""));

        Button saveButton = view.findViewById(R.id.buttonSave);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("Film", filmEditText.getText().toString());
                editor.putString("Year", godEditText.getText().toString());
                editor.putString("Actor", actorEditText.getText().toString());
                editor.putString("Reiting", reitingEditText.getText().toString());
                editor.apply();
                Toast.makeText(getActivity(), "Данные сохранены", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
        }
}
