package ru.mirea.vorobev.notebook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    private String filename;

    private String citata;
    private EditText et1;
    private EditText et2;

    private TextView citataText;
    private TextView fileText;
    private SharedPreferences preferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.editTextTextPersonName);
        et2 = findViewById(R.id.editTextTextPersonName2);
        citataText = findViewById(R.id.textView2);
        fileText = findViewById(R.id.textView);
        preferences = getSharedPreferences("VorobevSave", MODE_PRIVATE);
    }

    public void SaveText(View view)
    {
        filename = et1.getText().toString();
        citata = et2.getText().toString();
        setTextToFile();
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("FileName", filename);
        editor.putString("Citata", citata);
        editor.apply();
    }
    public void LoadText(View view)
    {
        filename = preferences.getString("FileName", "Empty");
        citata = preferences.getString("Citata","Empty");
        if(filename!="Empty" || citata!="Empty")
        {
            et1.setText(getTextFromFile());
            et2.setText(getTextFromFile());
        }
        else
        {
            et1.setText("File is empty");
            et2.setText("File is empty");
        }
    }

    public void setTextToFile() {
        FileOutputStream fos = null;
        try {
            String text1 = et2.getText().toString();
            String text2 = et1.getText().toString();
            fos = openFileOutput(filename, MODE_PRIVATE);
            fos.write(text1.getBytes());
            fos.write(text2.getBytes());
            Toast.makeText(this, "Файл сохранен", Toast.LENGTH_SHORT).show();
        }
        catch(IOException ex) {

            fileText.setText(filename);
        }
        finally{
            try{
                if(fos!=null)
                    fos.close();
            }
            catch(IOException ex){

              fileText.setText(filename);
            }
        }
    }

    public String getTextFromFile() {
        FileInputStream fin = null;
        try {
            fin = openFileInput(filename);
            byte[] bytes = new byte[fin.available()];
            fin.read(bytes);
            String text = new String(bytes);
            citataText.setText(text);
            return text;
        } catch (IOException ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_SHORT).show();
        } finally {
            try {
                if (fin != null)
                    fin.close();
            } catch (IOException ex) {
               citataText.setText(citata);
            }
        }
        return null;
    }
}