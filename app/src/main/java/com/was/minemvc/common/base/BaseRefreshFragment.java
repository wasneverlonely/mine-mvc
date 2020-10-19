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
 * 加载刷新
 */
public abstract class BaseRefreshFragment<T, K extends BaseViewHolder> extends BaseFragment implements IRefresh {

    RecyclerView mRecyclerView;
    SwipeRefreshLayout mSrhLayout;

    private int pageSize = 10;// 每页加载的条数
    private int pageIndex;// 加载下标
    public BaseRefreshAdapter mRefreshAdapter;//刷新adapter


    public boolean mLoadMoreEnable = true;

    public boolean isLoadMoreEnable() {
        return mLoadMoreEnable;
    }

    public void setLoadMoreEnable(boolean mLoadMoreEnable) {
        this.mLoadMoreEnable = mLoadMoreEnable;
    }

    boolean mHasItemDecoration = true;


    public boolean isHasItemDecoration() {
        return mHasItemDecoration;
    }

    public void setHasItemDecoration(boolean mHasItemDecoration) {
        this.mHasItemDecoration = mHasItemDecoration;
    }

    /**
     * 得到每页条数
     *
     * @param isRefresh
     * @return
     */
    public int getPageIndex(boolean isRefresh) {
        return isRefresh ? pageIndex = 1 : ++pageIndex;
    }

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


    private boolean enterLoad = true; //进入页面后加载

    public boolean isEnterLoad() {
        return enterLoad;
    }

    public void setEnterLoad(boolean enterLoad) {
        this.enterLoad = enterLoad;
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

    /**
     * 开启加载动画
     */
    public void requestRefreshing() {
        if (!mSrhLayout.isRefreshing()) {
            mSrhLayout.setRefreshing(true);
        }
        requestData(true);
    }


    public void start() {
        start(true);
    }

    public int colorSchemeResId = R.color.colorPrimary;// srhLayout 颜色主题id

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
        if (request && isEnterLoad()) {
            requestRefreshing();
        }
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
        } else
            mRefreshAdapter.getLoadMoreModule().setEnableLoadMore(false);

        mRecyclerView.setAdapter(mRefreshAdapter);

//        if (onItemTouchListeners != null && onItemTouchListeners.length != 0) {
//            for (RecyclerView.OnItemTouchListener onItemTouchListener : onItemTouchListeners)
//                mRecyclerView.addOnItemTouchListener(onItemTouchListener);
//        }
    }

    @Override
    public void requestFail( Throwable e,boolean isRefresh) {
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

    @Override
    public void requestSuccess(List datas, boolean isRefresh) {
        if (isRefresh) {
            mRefreshAdapter.setNewData(datas);
            mSrhLayout.setRefreshing(false);
            mRecyclerView.scrollToPosition(0);
        } else {
            mRefreshAdapter.addData(datas);
            mSrhLayout.setEnabled(true);
        }
    }

    /**
     * 下拉刷新监听
     */
    @Override
    public void onRefresh() {
        mRefreshAdapter.getLoadMoreModule().setEnableLoadMore(false);// 不能下啦加载
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

    /**
     * 设置linearLayoutManager
     */
    protected final void setLinearLayoutManager() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
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
        return new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
    }

    /**
     * 设置RecyclerView  的背景
     *
     * @param resid
     */
    public void setBackgroundResource(@DrawableRes int resid) {
        mRecyclerView.setBackgroundResource(resid);
    }
}
