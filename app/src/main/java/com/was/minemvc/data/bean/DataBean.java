package com.was.minemvc.data.bean;


import java.util.ArrayList;
import java.util.List;

import com.was.minemvc.R;

public class DataBean {
    public Integer imageRes;
    public String imageUrl;
    public String title;
    public int viewType;

    public DataBean(Integer imageRes, String title, int viewType) {
        this.imageRes = imageRes;
        this.title = title;
        this.viewType = viewType;
    }

    public DataBean(String imageUrl, String title, int viewType) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.viewType = viewType;
    }

    public static List<DataBean> getTestData() {
        List<DataBean> list = new ArrayList<>();
        list.add(new DataBean(R.mipmap.image1, "相信自己,你努力的样子真的很美", 1));
        list.add(new DataBean(R.mipmap.image2, "极致简约,梦幻小屋", 3));
        list.add(new DataBean(R.mipmap.image3, "超级卖梦人", 3));
        list.add(new DataBean(R.mipmap.image4, "夏季新搭配", 1));
        list.add(new DataBean(R.mipmap.image5, "绝美风格搭配", 1));
        return list;
    }

//
}
