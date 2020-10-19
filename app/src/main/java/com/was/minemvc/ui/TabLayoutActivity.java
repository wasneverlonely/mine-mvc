package com.was.minemvc.ui;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;
import com.was.minemvc.R;
import com.was.minemvc.common.base.BaseTabPageActivity;
import com.was.minemvc.fragment.CinemaFragment;
import com.was.minemvc.fragment.CommunityFragment;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//tabLayout
public class TabLayoutActivity extends BaseTabPageActivity {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vp_content)
    ViewPager vpContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab_layout);
        ButterKnife.bind(this);
        setBack();
        setTitleText("tabLayou");
        setView(tabLayout, vpContent);

        initViewPager();
    }

    CinemaFragment cinemaFragment;
    CommunityFragment communityFragment;

    @Override
    public void addFragments(List<Fragment> fgtList) {
        fgtList.add(cinemaFragment = new CinemaFragment());
        fgtList.add(communityFragment = new CommunityFragment());
    }

    @Override
    public String[] getPageTitle() {
        return new String[]{"影院", "社区"};
    }
}