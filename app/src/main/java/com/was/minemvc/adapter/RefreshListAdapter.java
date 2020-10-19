package com.was.minemvc.adapter;


import android.widget.TextView;

import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.was.core.adapter.BaseRefreshAdapter;
import com.was.minemvc.R;
import com.was.minemvc.data.bean.SchoolUniformBean;

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