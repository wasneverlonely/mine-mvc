package com.was.minemvc.common.base;

import androidx.annotation.DrawableRes;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.was.core.common.base.IRefresh;
import com.was.core.adapter.BaseRefreshAdapter;
import com.was.minemvc.R;

import java.util.List;


/**
 * Created by Administrator on 2016/12/29.
 * 上拉加载  下拉刷新 基类
 */
public abstract class BaseRefreshActivity<T, K extends BaseViewHolder> extends BaseActivity implements IRefresh {

    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSrhLayout;
    private BaseRefreshAdapter mRefreshAdapter;//刷新adapter

    private int pageSize = 10;// 每页加载的条数
    private int pageIndex;// 加载下标

    public boolean mLoadMoreEnable = true;//上拉开关

    public boolean isLoadMoreEnable() {
        return mLoadMoreEnable;
    }

    public void setLoadMoreEnable(boolean mLoadMoreEnable) {
        this.mLoadMoreEnable = mLoadMoreEnable;
    }

    /**
     * 获取页下标
     *
     * @param isRefresh
     * @return
     */
    public int getPageIndex(boolean isRefresh) {
        return isRefresh ? pageIndex = 1 : ++pageIndex;
    }

    /**
     * 一页的个数
     *
     * @return
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 设置每页条数
     *
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }


    /**
     * 设置refreshAdapter
     *
     * @param refreshAdapter
     */
    public void setRefreshAdapter(BaseRefreshAdapter<T, K> refreshAdapter) {
        this.mRefreshAdapter = refreshAdapter;
    }

    public BaseRefreshAdapter<T, K> getRefreshAdapter() {
        return mRefreshAdapter;
    }

    @Override
    public void setView(SwipeRefreshLayout srhLayout, RecyclerView recyclerView) {
        checkNullException(recyclerView);
        checkNullException(srhLayout);

        this.mRecyclerView = recyclerView;
        this.mSrhLayout = srhLayout;
    }

    protected void start() {
        start(true);
    }

    /**
     * 开始加载数据和动画
     */
    @Override
    public void start(boolean request) {
        checkNullException(mRecyclerView);
        checkNullException(mSrhLayout);
        mSrhLayout.setColorSchemeResources(colorSchemeResId);
        mSrhLayout.setOnRefreshListener(this);
        initView();
        if (request) {
            if (!mSrhLayout.isRefreshing()) {
                mSrhLayout.setRefreshing(true);
            }
            requestData(true);
        }
    }


    /**
     * 下拉刷新监听
     */
    @Override
    public void onRefresh() {
        mRefreshAdapter.getLoadMoreModule().setEnableLoadMore(false);// 禁止上拉加载
        requestData(true);
    }

    /**
     * 上拉加载监听
     */
    @Override
    public void onLoadMore() {
        if (mSrhLayout.isRefreshing())
            mSrhLayout.setRefreshing(false);
        mSrhLayout.setEnabled(false);
        requestData(false);
    }


    public int colorSchemeResId = R.color.colorPrimary;// srhLayout 颜色主题id
    boolean mHasItemDecoration = true;

    public boolean isHasItemDecoration() {
        return mHasItemDecoration;
    }

    public void setHasItemDecoration(boolean mHasItemDecoration) {
        this.mHasItemDecoration = mHasItemDecoration;
    }

    /**
     * @param refreshAdapter
     */
    public void initRecyclerView(BaseRefreshAdapter<T, K> refreshAdapter) {
        initRecyclerView(refreshAdapter, getDefaultItemDecoration(), new RecyclerView.OnItemTouchListener[]{});
    }

    /**
     * @param refreshAdapter
     * @param onItemTouchListeners
     */
    public void initRecyclerView(BaseRefreshAdapter<T, K> refreshAdapter, RecyclerView.OnItemTouchListener... onItemTouchListeners) {
        initRecyclerView(refreshAdapter, getDefaultItemDecoration(), onItemTouchListeners);
    }

    @Override
    public void initRecyclerView(BaseRefreshAdapter refreshAdapter, RecyclerView.ItemDecoration itemDecoration,
                                 RecyclerView.OnItemTouchListener... onItemTouchListeners) {

        if (refreshAdapter == null && mRefreshAdapter == null) {
            throw new IllegalArgumentException("refreshAdapter  is not  null");
        }
        setRefreshAdapter(refreshAdapter);
        setLinearLayoutManager();

        if (itemDecoration != null && isHasItemDecoration()) {
            addItemDecoration(itemDecoration);
        }


        if (isLoadMoreEnable()) {
            mRefreshAdapter.getLoadMoreModule().setEnableLoadMore(true);
            mRefreshAdapter.getLoadMoreModule().setOnLoadMoreListener(this);
        } else {
            mRefreshAdapter.getLoadMoreModule().setEnableLoadMore(false);
        }

        mRecyclerView.setAdapter(mRefreshAdapter);

        if (onItemTouchListeners != null && onItemTouchListeners.length != 0) {
            for (RecyclerView.OnItemTouchListener onItemTouchListener : onItemTouchListeners)
                mRecyclerView.addOnItemTouchListener(onItemTouchListener);
        }
    }


    /**
     * 请求成功
     */
    @Override
    public void requestSuccess(List datas, boolean isRefresh) {
        if (isRefresh) {
            mRefreshAdapter.setList(datas);
            mSrhLayout.setRefreshing(false);
            mRecyclerView.scrollToPosition(0);
        } else {
            mRefreshAdapter.addData(datas);
            mSrhLayout.setEnabled(true);
        }
    }


    /**
     * 请求失败
     *
     * @param e
     * @param isRefresh
     */
    @Override
    public void requestFail(Throwable e, boolean isRefresh) {
        if (isRefresh) {
            mSrhLayout.setRefreshing(false);
            if (mRefreshAdapter != null) {
                mRefreshAdapter.getLoadMoreModule().setEnableLoadMore(true);
            }
        } else {
            mRefreshAdapter.getLoadMoreModule().loadMoreFail();
            mSrhLayout.setEnabled(true);
        }
    }


    /**
     * 设置linearLayoutManager
     */
    protected final void setLinearLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * 添加分隔线
     */
    protected final void addItemDecoration() {
        addItemDecoration(getDefaultItemDecoration());
    }

    /**
     * 添加分隔线
     *
     * @param itemDecoration
     */
    protected final void addItemDecoration(RecyclerView.ItemDecoration itemDecoration) {
        mRecyclerView.addItemDecoration(itemDecoration);
    }


    /**
     * 得到默认的分割条
     *
     * @return
     */
    public RecyclerView.ItemDecoration getDefaultItemDecoration() {
        return new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
    }

    /**
     * 设置背景
     *
     * @param resid
     */
    public void setBackgroundResource(@DrawableRes int resid) {
        mRecyclerView.setBackgroundResource(resid);
    }

}
