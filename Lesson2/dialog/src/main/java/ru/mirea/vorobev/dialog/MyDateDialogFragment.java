package ru.mirea.vorobev.dialog;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class MyDateDialogFragment extends DialogFragment {
    static Context sContext;
    static Calendar sDate;
    static DateDialogFragmentListener sListener;

    public static MyDateDialogFragment newInstance(Context context, int titleResource, Calendar date){
        MyDateDialogFragment dialog  = new MyDateDialogFragment();

        sContext = context;
        sDate = date;

        Bundle args = new Bundle();
        args.putInt("title", titleResource);
        dialog.setArguments(args);
        return dialog;
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        return new DatePickerDialog(sContext, dateSetListener, sDate.get(Calendar.YEAR), sDate.get(Calendar.MONTH), sDate.get(Calendar.DAY_OF_MONTH));
    }
    public interface DateDialogFragmentListener {
        public void dateDialogFragmentDateSet(Calendar date);

    }
    public void setDateDialogFragmentListener(DateDialogFragmentListener listener){
        sListener = listener;
    }
    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {

                    Calendar newDate = Calendar.getInstance();
                    newDate.set(year, monthOfYear, dayOfMonth);
                    sListener.dateDialogFragmentDateSet(newDate);
                }
            };
}

