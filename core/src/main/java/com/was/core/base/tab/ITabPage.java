package com.was.core.base.tab;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;

import java.util.List;

/**
 *
 */
public interface ITabPage {


    int TYPE_COMMON = 0x001;  // tabLayout 普通模式
    int TYPE_CUSTOM = 0x002; // tabLayout 自定义模式

    int STATE_BACK = 1;  // 返回键 标识
    int STATE_PAGE_SWITCH = 2; // 页面切换
    int STATE_CHANGE = 3; // 页面改变
    int STATE_OTHER = 4; // 其他
    int STATE_OTHER2 = 5; // 其他


    /**
     * set  TabLayout  ViewPager
     */
    void setView(TabLayout tabLayout, ViewPager vpContent);

    /**
     * 初始化View
     */
    void initViewPager();


    /**
     * 添加fragment
     *
     * @param fgtList
     */
    void addFragments(List<Fragment> fgtList);


    /**
     * 页面tab文字
     *
     * @return
     */
    String[] getPageTitle();

}
