package com.was.minemvc.adapter;


import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.was.minemvc.R;
import com.was.minemvc.bean.ProvinceBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CommonListAdapter extends BaseQuickAdapter<ProvinceBean, BaseViewHolder> {

    public CommonListAdapter(int layoutResId, List<ProvinceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder helper, ProvinceBean bean) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        tvTitle.setText(bean.getName());
    }
}

