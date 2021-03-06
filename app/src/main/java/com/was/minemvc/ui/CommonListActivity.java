package com.was.minemvc.ui;

import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.was.core.common.http.ProgressSubscriber;
import com.was.core.utils.ToastUtils;
import com.was.minemvc.R;
import com.was.minemvc.adapter.CommonListAdapter;
import com.was.minemvc.data.HttpResult;
import com.was.minemvc.data.bean.ProvinceBean;
import com.was.minemvc.common.HttpHelper;
import com.was.minemvc.common.base.BaseActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;

public class CommonListActivity extends BaseActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_list);

        ButterKnife.bind(this);
        setBack();
        setTitleText("普通列表");

        initView();
        requestData();
    }

    CommonListAdapter mAdapter;

    private void initView() {

        mAdapter = new CommonListAdapter(R.layout.item_common_list);
        recyclerView.setAdapter(mAdapter);

        mAdapter.setOnItemClickListener((adapter, view, position) -> {
            ToastUtils.showShort(mAdapter.getData().get(position).getName());
        });
    }

    private void requestData() {

        Observable<HttpResult<List<ProvinceBean>>> observable = HttpHelper.getApi().lookProvince();

        HttpHelper.toSubscribe(observable, new ProgressSubscriber<List<ProvinceBean>>(this, false) {
            @Override
            public void onNext(List<ProvinceBean> data) {
                mAdapter.setList(data);
            }

            @Override
            public void onFail(Throwable e) {
                super.onFail(e);
            }
        });
    }
}