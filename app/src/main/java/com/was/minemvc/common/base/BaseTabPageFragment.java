package com.was.minemvc.common.base;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.was.core.adapter.FgtTabPagerAdapter;
import com.was.core.base.tab.ITabPage;
import com.was.core.base.tab.PageStateChangeListener;
import com.was.core.base.tab.PageStateChangeObservable;

import java.util.ArrayList;
import java.util.List;
import java.util.Observer;


/**
 * base  tab
 */
public abstract class BaseTabPageFragment extends BaseFragment implements ITabPage {

    private TabLayout mTabLayout;
    private ViewPager mVpContent;

    ITabPage mTabPage; //实现类t

    //状态改变通知
    PageStateChangeObservable mPageStateChangeObservable;
    // fragment 集合
    private List<Fragment> fgtList;
    FgtTabPagerAdapter adapter;
    //页面切换单一通知 开关
    private boolean pageSwitchEnable;
    //返回通知 开关
    private boolean backEnabled;
    private ITabPage f;

    public List<Fragment> getFragmentList() {
        return fgtList;
    }

    public int getFragmentSize() {
        return getFragmentList().size();
    }

    public boolean isPageSwitchEnable() {
        return pageSwitchEnable;
    }

    public void setPageSwitchEnable(boolean enable) {
        if (isNulls(mPageStateChangeObservable)) {
            throw new IllegalArgumentException("mPageStateChangeObservable  is  null");
        }
        this.pageSwitchEnable = enable;
        mVpContent.clearOnPageChangeListeners();
        if (enable)
            mVpContent.addOnPageChangeListener(new PageStateChangeListener(mPageStateChangeObservable));
    }

    @Override
    public void setView(TabLayout tabLayout, ViewPager vpContent) {
        this.mTabLayout = tabLayout;
        this.mVpContent = vpContent;
    }

    @Override
    public void initViewPager() {
//        findView(view);

        if (isNulls(mTabLayout, mVpContent)) {
            throw new IllegalArgumentException("tabLayout  vpContent is null");
        }

        fgtList = new ArrayList<>();
        addFragments(fgtList);

        adapter = new FgtTabPagerAdapter(getChildFragmentManager(), fgtList, getPageTitle());
        mVpContent.setAdapter(adapter);

        int type = initTabLayout(); // 返回自定义tablayout 还是 原始tabLayout
        if (type == TYPE_CUSTOM) { //自定义tablayout样式
            mTabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mVpContent));
        } else {
            mTabLayout.setupWithViewPager(mVpContent);
        }
        mTabLayout.getTabAt(0).select();//默认选中第一个
    }

    /**
     * fragment
     *
     * @param fragment
     */
    public void addFragment(Fragment fragment) {
        if (fgtList == null) {
            fgtList = new ArrayList<>();
        }
        fgtList.add(fragment);
    }


    /**
     * 调整tabLayout的一些样式
     * 提高灵活性
     */
    protected int initTabLayout() {
        return TYPE_COMMON;
    }


    /**
     * 添加 一个监听
     *
     * @param o
     */
    public void addStateObserver(Observer o) {
        if (isNull(mPageStateChangeObservable)) {
            mPageStateChangeObservable = new PageStateChangeObservable();
        }
        mPageStateChangeObservable.addObserver(o);
    }

    /**
     * 添加 多个监听
     *
     * @param os
     */
    public void addStateObservers(Observer... os) {
        for (Observer o : os) {
            addStateObserver(o);
        }
    }

    /**
     * 去除全部监听
     */
    public void deleteStateObservers() {
        if (!isNull(mPageStateChangeObservable))
            mPageStateChangeObservable.deleteObservers();
    }

    /**
     * 去除监听
     *
     * @param o
     */
    public void deleteStateObservers(Observer o) {
        if (!isNull(mPageStateChangeObservable))
            mPageStateChangeObservable.deleteObserver(o);
    }

    /**
     * 通知改变
     *
     * @param o
     */
    public void notifyStateChange(Object o) {
        if (!isNull(mPageStateChangeObservable))
            mPageStateChangeObservable.notifyObservers(o);
    }


    //是否可以返回
    public boolean isBackEnabled() {
        return backEnabled;
    }

    public void setBackEnabled(boolean backEnabled) {
        this.backEnabled = backEnabled;
    }


    /**
     * 设置tabLayout 左右间距
     *
     * @param leftDip
     * @param rightDip
     */
//    public void setIndicatorSpacing(int leftDip, int rightDip) {
//        ViewUtils.setIndicatorSpacing(mTabLayout, leftDip, rightDip);
//    }
}
