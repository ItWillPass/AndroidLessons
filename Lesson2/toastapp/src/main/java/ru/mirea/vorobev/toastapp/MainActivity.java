package ru.mirea.vorobev.toastapp;

import androidx.appcompat.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;


public class MainActivity extends AppCompatActivity {
    private EditText editTextName;
    private TextView textKol;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editTextName = (EditText) findViewById(R.id.editTextTextPersonName);
        textKol = (TextView) findViewById(R.id.textViewCountSymbol);

        Button btnCount = (Button) findViewById(R.id.button);
        btnCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String fio = editTextName.getText().toString();
                int count = fio.length();
                Log.d(MainActivity.class.getSimpleName(), "FIO: "  + fio);
//                textKol.setText(count);
                Toast toast = Toast.makeText(getApplicationContext(),
                        String.format("СТУДЕНТ Воробьев И.А.  ГРУППА БСБО-17-20 Количество символов - %d", count),
                        Toast.LENGTH_SHORT);
                toast.show();
                textKol.setText("Count: " + count);
            }

        });
    }

}