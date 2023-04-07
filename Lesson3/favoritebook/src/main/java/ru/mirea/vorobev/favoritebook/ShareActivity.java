package ru.mirea.vorobev.favoritebook;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ShareActivity extends AppCompatActivity {

    private EditText yourfavoritebookEditText;

    //@SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share);

        yourfavoritebookEditText = findViewById(R.id.favoritebookEditText);
        Button sendButton = findViewById(R.id.button2);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bookTitle = yourfavoritebookEditText.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("book_title", bookTitle);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

}