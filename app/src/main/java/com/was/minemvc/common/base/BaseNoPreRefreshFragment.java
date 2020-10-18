package com.was.minemvc.common.base;

import com.was.core.base.NoPreload;

/**
 * Created by Administrator on 2016/12/30.
 * 无预加载  刷新
 */
public abstract class BaseNoPreRefreshFragment extends BaseRefreshFragment implements NoPreload {


    private boolean mIsVisible = false;  // 是否显示
    private boolean mIsPrepared = false; // 是否已经准备   onCreatView 里面改成true
    private boolean mIsLoadSuccess = false; // 是否加载成功
    private boolean mRefreshLoad = false;


    @Override
    public void prepared() {
        setPrepared(true);
    }

    @Override
    public boolean isPrepared() {
        return mIsPrepared;
    }

    @Override
    public void setPrepared(boolean isPrepared) {
        this.mIsPrepared = isPrepared;
    }

    @Override
    public void loadSuccess() {
        setLoadSuccess(true);
        setRefreshLoad(true);
    }

    @Override
    public void setLoadSuccess(boolean loadSuccess) {
        this.mIsLoadSuccess = loadSuccess;
    }

    @Override
    public boolean isLoadSuccess() {
        return mIsLoadSuccess;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            mIsVisible = true;
            onVisible();
        } else {
            mIsVisible = false;
            onInvisible();
        }
    }

    @Override
    public void onInvisible() {

    }

    @Override
    public void onVisible() {
        lazyLoad();
    }

    /**
     * 是否能加载
     *
     * @return
     */
    @Override
    public boolean isCanLoad() {
        return mIsVisible && isPrepared() && !isLoadSuccess();
    }

    @Override
    public void lazyLoad() {
        if (isCanLoad()) {
            if (isRefreshLoad())
                requestRefreshing();
            else
                start();
        }
    }

    //是否刷新

    public boolean isRefreshLoad() {
        return mRefreshLoad;
    }

    public void setRefreshLoad(boolean refreshLoad) {
        this.mRefreshLoad = refreshLoad;
    }

    public void refreshLoad() {
        setRefreshLoad(true);
        setLoadSuccess(false);
    }
}
