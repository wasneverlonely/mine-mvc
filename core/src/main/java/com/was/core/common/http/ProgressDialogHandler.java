package com.was.core.common.http;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;


/**
 * Created by Administrator on 2016/11/23.
 * <p>
 * 处理显示
 */
public class ProgressDialogHandler extends Handler {


    public static final int SHOW_PROGRESS_DIALOG = 1;
    public static final int DISMISS_PROGRESS_DIALOG = 2;

    private ProgressDialog pd;

    private Context mContext;

    private boolean cancelable;

    private ProgressCancelListener mProgressCancelListener;


    public ProgressDialogHandler(Context context) {
        super();
        this.mContext = context;
    }

    public ProgressDialogHandler(Context mContext, ProgressCancelListener mProgressCancelListener, boolean cancelable) {
        super();
        this.mContext = mContext;
        this.cancelable = cancelable;
        this.mProgressCancelListener = mProgressCancelListener;
    }

    public void initProgressDialog() {
        if (pd == null) {
            pd = new ProgressDialog(mContext);
            pd.setCancelable(cancelable);
//            pd.setTitle("标题");
            pd.setMessage("正在拼命加载中~");

            if (cancelable) {
                pd.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        mProgressCancelListener.onCancelProgress();
                    }
                });
            }
        }
        try {
            if (!pd.isShowing()) {
                pd.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 展示加载条
     */
    public void showProgressDialog() {

        if (pd == null) {
            pd = new ProgressDialog(mContext);
            pd.setCancelable(cancelable);
        }
        if (!pd.isShowing()) {
            pd.show();
        }
    }


    public void dismissProgressDialog() {
        if (pd != null) {
            pd.dismiss();
            pd = null;
        }
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                initProgressDialog();
                break;
            case DISMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
            default:
                break;
        }
    }
}
