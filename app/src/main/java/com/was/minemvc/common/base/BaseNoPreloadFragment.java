package com.was.minemvc.common.base;


import com.was.core.common.base.NoPreload;

/**
 * Created by Administrator on 2016/12/29.
 * 没有预加载
 */
public abstract class BaseNoPreloadFragment extends BaseFragment implements NoPreload {

    private boolean mIsVisible;  // 是否显示
    private boolean mIsPrepared; // 是否已经准备   onCreatView 里面改成true
    private boolean mIsLoadSuccess; // 是否加载成功


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
            onInvisible();
            mIsVisible = false;
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
        return isPrepared() && mIsVisible && !isLoadSuccess();
    }
}
