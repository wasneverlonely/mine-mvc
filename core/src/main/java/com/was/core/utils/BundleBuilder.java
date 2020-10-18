package com.was.core.utils;

import android.os.Bundle;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/12/12.
 * bundle 创建者
 */
public class BundleBuilder {

    public static final String ID = "id";// id
    public static final String ID2 = "id2";// id2
    public static final String IDS = "ids";// id2

    public static final String BOOL = "bool";// 布尔类型

    public static final String TITLE = "title";// 标题
    public static final String TYPE = "type";// 类型
    public static final String SEARCH = "search";

    public static final String CONTENT = "content";//内容
    public static final String CONTENT2 = "content2";//内容2

    public static final String RESULT = "result";// 返回
    public static final String RESULT2 = "result2";// 返回

    private Bundle bundle;

    /**
     * 输入一个id
     *
     * @param id
     * @return
     */
    public static Bundle creatBundle(int id) {
        return new BundleBuilder().putId(id).build();
    }


    public BundleBuilder() {
        bundle = new Bundle();
    }

    public BundleBuilder putId(int id) {
        bundle.putInt(ID, id);
        return this;
    }

    public BundleBuilder putId2(int id2) {
        bundle.putInt(ID2, id2);
        return this;
    }

    public BundleBuilder putArrayId(int... id) {
        bundle.putIntArray(IDS, id);
        return this;
    }


    public BundleBuilder putBool(boolean b) {
        bundle.putBoolean(BOOL, b);
        return this;
    }

    public BundleBuilder putType(int type) {
        bundle.putInt(TYPE, type);
        return this;
    }

    public BundleBuilder putTitle(CharSequence title) {
        bundle.putString(TITLE, title.toString());
        return this;
    }

    public BundleBuilder putContent(String value) {
        bundle.putString(CONTENT, value);
        return this;
    }

    public BundleBuilder putContent(Parcelable value) {
        bundle.putParcelable(CONTENT, value);
        return this;
    }

    public BundleBuilder putParcelableArray(Parcelable[] value) {
        bundle.putParcelableArray(CONTENT, value);
        return this;
    }

    public BundleBuilder putParcelableArrayList(ArrayList<? extends Parcelable> value) {
        bundle.putParcelableArrayList(CONTENT, value);
        return this;
    }


    public BundleBuilder putContent2(String content2) {
        bundle.putString(CONTENT2, content2);
        return this;
    }

    public BundleBuilder putContent2(Parcelable p) {
        bundle.putParcelable(CONTENT2, p);
        return this;
    }


    public BundleBuilder putResult(int position) {
        bundle.putInt(RESULT, position);
        return this;
    }

    public BundleBuilder putResult(String result) {
        bundle.putString(RESULT, result);
        return this;
    }

    public BundleBuilder putResult(Parcelable p) {
        bundle.putParcelable(RESULT, p);
        return this;
    }


    public BundleBuilder putResult2(Parcelable p) {
        bundle.putParcelable(RESULT2, p);
        return this;
    }

    public Bundle build() {
        return bundle;
    }
}
