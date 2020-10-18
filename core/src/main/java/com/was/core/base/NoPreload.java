package com.was.core.base;


/**
 * 不预加载
 */
public interface NoPreload {

    void prepared();

    boolean isPrepared();

    void setPrepared(boolean isPrepared);

    void loadSuccess();

    void setLoadSuccess(boolean loadSuccess);

    boolean isLoadSuccess();

    void onInvisible();

    void onVisible();

    boolean isCanLoad();

    void lazyLoad();

}
