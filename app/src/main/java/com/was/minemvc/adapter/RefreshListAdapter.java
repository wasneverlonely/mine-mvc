package com.was.minemvc.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.was.core.widget.recycler.BaseRefreshAdapter;
import com.was.minemvc.R;
import com.was.minemvc.bean.SchoolUniformBean;

import org.jetbrains.annotations.NotNull;

import java.util.List;


public class RefreshListAdapter extends BaseRefreshAdapter<SchoolUniformBean, BaseViewHolder> {

    public RefreshListAdapter(int layoutResId, List<SchoolUniformBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SchoolUniformBean bean) {
        TextView tvTitle = helper.getView(R.id.tv_title);
        tvTitle.setText(bean.getUniform_name());
    }
}