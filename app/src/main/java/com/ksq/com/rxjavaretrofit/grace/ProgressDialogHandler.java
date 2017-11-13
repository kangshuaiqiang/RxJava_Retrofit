package com.ksq.com.rxjavaretrofit.grace;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.os.RecoverySystem;

/**
 * Created by 黑白 on 2017/11/10.
 */

public class ProgressDialogHandler extends Handler {
    public static final int SHOW_PROGRESS = 1;
    public static final int DISMISS_PROGRESS = 2;

    private ProgressDialog pd;

    private Context context;
    private boolean cancelable;
    private ProgressCancelListener listener;

    public ProgressDialogHandler(Context context, ProgressCancelListener listener, boolean cancelable) {
        super();
        this.context = context;
        this.listener = listener;
        this.cancelable = cancelable;
    }

    private void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(context);
            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        listener.onCancelPregress();
                    }
                });
            }
            if (!pd.isShowing()) {
                pd.show();
            }
        }

    }


    private void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        switch (msg.what) {
            case SHOW_PROGRESS:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS:
                dismissProgressDialog();
                break;
        }

    }
}
