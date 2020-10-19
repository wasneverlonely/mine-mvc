package com.was.minemvc.ui;


import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;
import com.was.minemvc.R;
import com.was.minemvc.adapter.ImageTitleAdapter;
import com.was.minemvc.data.bean.DataBean;
import com.was.minemvc.common.base.BaseActivity;
import com.youth.banner.Banner;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BannerActivity extends BaseActivity {

    @BindView(R.id.banner)
    Banner banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner);
        ButterKnife.bind(this);

        setBack();
        setTitleText("轮播图");
        initView();
    }


    public void initView() {
//        使用内置Indicator
//        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());
        ImageTitleAdapter adapter = new ImageTitleAdapter(DataBean.getTestData());

        banner.setAdapter(adapter)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setIndicator(new CircleIndicator(this))//设置指示器
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });
    }
}