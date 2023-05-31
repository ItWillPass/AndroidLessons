package ru.mirea.vorobev.mireaproject;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class FilmFragment extends Fragment {
    private EditText filmEditText;
    private EditText godEditText;
    private EditText actorEditText;
    private EditText reitingEditText;
    private EditText nameFile;
    private Button shifrButton;
    private Button desButton;
    private SharedPreferences preferences;
    private TextView infoText;
    public FilmFragment(){};
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
        nameFile=view.findViewById(R.id.fileNameEditText);
        infoText = view.findViewById(R.id.infoTextView);

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
        shifrButton = view.findViewById(R.id.buttonPref);
        shifrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDataToFile();
            }
        });

        desButton = view.findViewById(R.id.buttonDes);
       desButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadDataFromFile();
            }
        });
        return view;
        }
    private void saveDataToFile() {
        String filename = nameFile.getText().toString();
        String filmText = filmEditText.getText().toString();
        String yearText = godEditText.getText().toString();
        String actorText = actorEditText.getText().toString();
        String reitingText = reitingEditText.getText().toString();

        if (!filename.isEmpty() && !filmText.isEmpty()&& !yearText.isEmpty()&& !actorText.isEmpty() && !reitingText.isEmpty()) {
            File directory = getActivity().getExternalFilesDir("Directory_Documents");
            if (directory != null) {
                File file = new File(directory, filename);

                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    fos.write(filmText.getBytes());
                    fos.write(yearText.getBytes());
                    fos.write(actorText.getBytes());
                    fos.write(reitingText.getBytes());
                    fos.close();
                    Toast.makeText(getContext(), "Данные сохранены в файл", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Ошибка при сохранении данных", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), "Пожалуйста, заполните все поля", Toast.LENGTH_SHORT).show();
        }
    }
    private void loadDataFromFile() {
        String filename = nameFile.getText().toString();

        if (!filename.isEmpty()) {
            File directory = getActivity().getExternalFilesDir("Directory_Documents");
            if (directory != null) {
                File file = new File(directory, filename);

                if (file.exists()) {
                    try {
                        BufferedReader reader = new BufferedReader(new FileReader(file));
                        StringBuilder sb = new StringBuilder();
                        String line;

                        while ((line = reader.readLine()) != null) {
                            sb.append(line);
                            sb.append("\n");
                        }
                        reader.close();
                        infoText.setText(sb.toString().trim());
                        Toast.makeText(getContext(), "Данные загружены из файла", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Ошибка при загрузке данных", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getContext(), "Файл не существует", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            Toast.makeText(getContext(), "Пожалуйста, введите название файла", Toast.LENGTH_SHORT).show();
        }
    }
}

