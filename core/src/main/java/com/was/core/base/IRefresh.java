package com.was.core.base;

import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.listener.OnLoadMoreListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.was.core.widget.recycler.BaseRefreshAdapter;

import java.util.List;

/**
 * 刷新接口
 *
 * @param <T>
 * @param <K> BaseQuickAdapter.RequestLoadMoreListener
 */ //
public interface IRefresh<T, K extends BaseViewHolder> extends SwipeRefreshLayout.OnRefreshListener, OnLoadMoreListener {

    void setView(RecyclerView recyclerView, SwipeRefreshLayout srhLayout);

    void start(boolean refresh);

    void initRecyclerView(BaseRefreshAdapter<T, K> refreshAdapter, RecyclerView.ItemDecoration itemDecoration,
                          RecyclerView.OnItemTouchListener... onItemTouchListeners);


    void initView();  //初始化 adapter

    void requestData(boolean isRefresh); //请求数据

    void requestFail(boolean isRefresh, Throwable e); //数据加载失败

    void requestSuccess(List<T> datas, boolean isRefresh); //数据加载成功

}
