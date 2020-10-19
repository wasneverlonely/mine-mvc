package com.was.core.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.module.LoadMoreModule;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 加载  adapter
 *
 * @param <T>
 * @param <K>
 */
public abstract class BaseRefreshAdapter<T, K extends BaseViewHolder> extends BaseQuickAdapter<T, K> implements LoadMoreModule {

    private int mLoadCount = 10; //默认加载数量

    public BaseRefreshAdapter(int layoutResId) {
        super(layoutResId);
    }

    public BaseRefreshAdapter(int layoutResId, List<T> data) {
        super(layoutResId, data);
    }

    public void setLoadCount(int loadCount) {
        this.mLoadCount = loadCount;
    }


    // 注意不用去判断 size = 0
    @Override
    public void addData(@NonNull Collection<? extends T> addData) {
        if (addData == null) {
            addData = new ArrayList<>();
        }
        super.addData(addData);
        getLoadMoreModule().loadMoreComplete();
        isLoadAll(addData);
    }

    @Override
    public void setList(@Nullable Collection<? extends T> list) {
        super.setList(list);
        getLoadMoreModule().setEnableLoadMore(true);//刷新的时候把下拉关闭  所以现在要打开
        isLoadAll(getData());// 直接把 adapter的数据去判断  不用去判断null了
    }

    /**
     * 是否加载全部
     *
     * @param data
     */
    public void isLoadAll(Collection<? extends T> data) {
        if (data.size() < mLoadCount) {
            getLoadMoreModule().loadMoreEnd();
        }
    }


}
