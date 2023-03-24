package ru.mirea.vorobev.dialog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.text.format.DateUtils;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView currentDateTime;
    Calendar dateAndTime=Calendar.getInstance();

    MyDateDialogFragment dateDialogFragment = new MyDateDialogFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onProgressDialog(View view)
    {
        MyProgressDialogFragment customProgress = MyProgressDialogFragment.getInstance();

        Context context = null;
        customProgress.showProgress(context, "Loading",false);

        customProgress.hideProgress();
    }

    public void onTimeDialog(View view)
    {
        MyTimeDialogFragment ddf = MyTimeDialogFragment.newInstance(this, 0, dateAndTime);

        ddf.setTimeDialogFragmentListener(new MyTimeDialogFragment.TimeDialogFragmentListener() {

            @Override
            public void timeDialogFragmentDateSet(Calendar date) {
                // update the fragment
                TimePicker mDateDetailFragment = null;

            }
        });

        ddf.show(getSupportFragmentManager(), "date picker dialog fragment");
    }

    public void onDateDialog(View view)
    {
        MyDateDialogFragment ddf = MyDateDialogFragment.newInstance(this, 0, dateAndTime);

        ddf.setDateDialogFragmentListener(new MyDateDialogFragment.DateDialogFragmentListener() {

            @Override
            public void dateDialogFragmentDateSet(Calendar date) {
                // update the fragment
                DatePicker mDateDetailFragment = null;
                mDateDetailFragment.updateDate(0,0,0);
            }
        });

        ddf.show(getSupportFragmentManager(), "date picker dialog fragment");
    }

    public void onClickShowDialog(View view) {
        MyDialogFragment dialogFragment = new MyDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), "mirea");
    }
    public void onOkClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Иду дальше\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onCancelClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"Нет\"!",
                Toast.LENGTH_LONG).show();
    }
    public void onNeutralClicked() {
        Toast.makeText(getApplicationContext(), "Вы выбрали кнопку \"На паузе\"!",
                Toast.LENGTH_LONG).show();
    }
}
