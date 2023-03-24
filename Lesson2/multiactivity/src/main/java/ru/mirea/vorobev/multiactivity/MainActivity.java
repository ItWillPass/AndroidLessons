package ru.mirea.vorobev.multiactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private EditText editTextName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = (EditText) findViewById(R.id.editTextTextPersonName2);

    }
    public void onClickNewActivity(View v){
        switch (v.getId()) {
            case R.id.button:
                // Говорим между какими Activity будет происходить связь
                Intent intent = new Intent(this, SecondActivity2.class);

                // указываем первым параметром ключ, а второе значение
                // по ключу мы будем получать значение с Intent
                intent.putExtra("name", editTextName.getText().toString());

                // показываем новое Activity
                startActivity(intent);
                break;
            default:
                break;
        }
    }


}