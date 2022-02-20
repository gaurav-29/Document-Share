package com.oceanmtech.documentshare.Utils;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;

import com.oceanmtech.documentshare.R;

public class ProgressHUD extends Dialog {

    public ProgressHUD(Context context) {
        super(context);
    }

    public ProgressHUD(Context context, int theme) {
        super(context, theme);
    }

    //Dialog show
    public static ProgressHUD show(Context context, boolean isShow, boolean indeterminate, boolean cancelable, OnCancelListener cancelListener) {
        ProgressHUD dialog = new ProgressHUD(context, R.style.ProgressHUD);
        if (isShow) {

//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setTitle("");
            dialog.setContentView(R.layout.progress_hud);
            dialog.setCancelable(cancelable);
            dialog.setOnCancelListener(cancelListener);
            dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
//            Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            dialog.show();
        } else {
            dialog.dismiss();
        }

        return dialog;
    }

    // Dismiss dialog
    public void dismissProgressDialog(ProgressHUD progressHUD) {
        try {
            if (progressHUD != null)
                progressHUD.dismiss();
        } catch (Exception e) {

        }
    }
}
