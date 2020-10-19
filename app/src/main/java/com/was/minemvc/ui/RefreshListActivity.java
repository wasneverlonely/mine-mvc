package com.was.minemvc.ui;


import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.was.core.common.http.ProgressSubscriber;
import com.was.core.utils.ToastUtils;
import com.was.minemvc.R;
import com.was.minemvc.adapter.RefreshListAdapter;
import com.was.minemvc.data.HttpResult;
import com.was.minemvc.data.bean.SchoolUniformBean;
import com.was.minemvc.common.HttpHelper;
import com.was.minemvc.common.base.BaseRefreshActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class RefreshListActivity extends BaseRefreshActivity {

    @BindView(R.id.srhLayout)
    SwipeRefreshLayout srhLayout;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh_list);
        ButterKnife.bind(this);

        setBack();
        setTitleText("刷新列表");

        setView(srhLayout, recyclerView);
        start();
    }

    RefreshListAdapter mAdapter;

    @Override
    public void initView() {
        mAdapter = new RefreshListAdapter(R.layout.item_refresh_list, null);
        initRecyclerView(mAdapter);


        mAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                ToastUtils.showShort(mAdapter.getData().get(position).getUniform_name());
            }
        });
    }

    @Override
    public void requestData(boolean isRefresh) {

        Observable<HttpResult<List<SchoolUniformBean>>> observable = HttpHelper.getApi().lookSchoolUniform("13", getPageIndex(isRefresh), getPageSize());

        HttpHelper.toSubscribe(observable, new ProgressSubscriber<List<SchoolUniformBean>>(this, false) {
            @Override
            public void onNext(List<SchoolUniformBean> data) {
                requestSuccess(data, isRefresh);
            }

            @Override
            public void onFail(Throwable e) {
                super.onFail(e);
                requestFail(e, isRefresh);
            }
        });
    }
}