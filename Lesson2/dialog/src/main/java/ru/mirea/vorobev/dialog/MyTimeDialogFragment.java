package ru.mirea.vorobev.dialog;


import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.fragment.app.DialogFragment;

import java.sql.Time;
import java.util.Calendar;

public class MyTimeDialogFragment extends DialogFragment{
    static Context sContext;
    static Calendar sTime;
    static MyTimeDialogFragment.TimeDialogFragmentListener sListener;

    public static MyTimeDialogFragment newInstance(Context context, int titleResource, Calendar time){
        MyTimeDialogFragment dialog  = new MyTimeDialogFragment();

        sContext = context;
        sTime = time;

        Bundle args = new Bundle();
        args.putInt("title", titleResource);
        dialog.setArguments(args);
        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new TimePickerDialog(sContext, timeSetListener, sTime.get(Calendar.HOUR), sTime.get(Calendar.MINUTE), true);
    }
    public interface TimeDialogFragmentListener{
        public void timeDialogFragmentDateSet(Calendar time);

    }
    public void setTimeDialogFragmentListener(MyTimeDialogFragment.TimeDialogFragmentListener listener){
        sListener = listener;
    }


    private TimePickerDialog.OnTimeSetListener timeSetListener =
            new TimePickerDialog.OnTimeSetListener() {

                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    Time newDate =new Time(hour, minute,0);//.getInstance();
                    newDate.setTime(hour);
                }


            };
}