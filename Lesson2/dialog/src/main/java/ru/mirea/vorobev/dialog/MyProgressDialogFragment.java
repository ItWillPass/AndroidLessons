package ru.mirea.vorobev.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyProgressDialogFragment {
    public static MyProgressDialogFragment customProgress = null;
    private Dialog mDialog;

    public static MyProgressDialogFragment getInstance() {
        if (customProgress == null) {
            customProgress = new MyProgressDialogFragment();
        }
        return customProgress;
    }

    public void showProgress(Context context, String message, boolean cancelable) {
        mDialog = new Dialog(context);
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        ProgressBar mProgressBar = (ProgressBar) mDialog.findViewById(R.id.progressBar);
        TextView progressText = (TextView) mDialog.findViewById(R.id.prog_text);
        progressText.setText("" + message);
        progressText.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mProgressBar.setIndeterminate(true);
        mDialog.setCancelable(cancelable);
        mDialog.setCanceledOnTouchOutside(cancelable);
        mDialog.show();
    }

    public void hideProgress() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }
}
