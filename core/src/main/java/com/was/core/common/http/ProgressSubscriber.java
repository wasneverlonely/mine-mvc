package com.was.core.common.http;

import android.content.Context;

import com.was.core.R;
import com.was.core.utils.PrintUtils;
import com.was.core.utils.ToastUtils;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import rx.Subscriber;

/**
 * Created by Administrator on 2016/12/26.
 */
public abstract class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener {

    private ProgressDialogHandler mProgressDialogHandler;
    private Context mContext;
    private boolean isShowLoad;
    private boolean isToast;


    public ProgressSubscriber(Context context) {
        this(context, true);
    }

    // 设置是否显示加载条
    public ProgressSubscriber(Context context, boolean isShowLoad) {
        this(context, isShowLoad, true);
    }

    //设置取消加载
    public ProgressSubscriber(Context context, boolean isShowLoad, boolean isToast) {
        this(context, isShowLoad, isToast, false);
    }


    //设置取消加载
    public ProgressSubscriber(Context context, boolean isShowLoad, boolean isToast, boolean cancelable) {
        this.mContext = context;
        this.isToast = isToast;
        if (this.isShowLoad = isShowLoad) {
            mProgressDialogHandler = new ProgressDialogHandler(context, this, cancelable);
        }
    }


    /**
     * 显示  showProgressDialog
     */
    private void showProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    private void dismissProgressDialog() {
        if (mProgressDialogHandler != null) {
            mProgressDialogHandler.obtainMessage(ProgressDialogHandler.DISMISS_PROGRESS_DIALOG).sendToTarget();
            mProgressDialogHandler = null;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
    }

    @Override
    public void onError(Throwable e) {
        catchException(e);
        onFail(e);
        dismissProgressDialog();
    }


    /**
     * 页面处理异常
     *
     * @param e
     */
    public void onFail(Throwable e) {

    }


    /**
     * 捕捉异常
     */
    public void catchException(Throwable e) {

        if (e instanceof UnknownHostException || e instanceof ConnectException) {
            toast(R.string.str_no_network_link);
        } else if (e instanceof SocketTimeoutException) {
            toast(R.string.str_network_link_timeout);
        } else if (e instanceof ApiException) {
            toast(e.getMessage());
        } else {
            toast(R.string.str_unknown_exception + "\n" + e.getMessage());
        }
        PrintUtils.printNetRequestException(e);
    }

    /**
     * toast
     *
     * @param rid
     */
    public void toast(int rid) {
        if (mContext != null) {
            String content = mContext.getResources().getString(rid);
            toast(content);
        }
    }


    /**
     * toast
     *
     * @param toastContent
     */
    public void toast(String toastContent) {
        if (isToast) {
            ToastUtils.showShort(toastContent);
        }
    }


    /**
     * 取消加载
     */
    @Override
    public void onCancelProgress() {
        if (!this.isUnsubscribed())
            this.unsubscribe();
    }
}
