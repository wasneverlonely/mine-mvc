package com.was.core.adapter;

import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;

/**
 * 普通的fragment adapter
 */
public class FgtPagerAdapter extends FragmentPagerAdapter {

    protected List<Fragment> frgList;

    protected boolean destroy;

    public List<Fragment> getFrgList() {
        return frgList;
    }

    public void setFrgList(List<Fragment> frgList) {
        this.frgList = frgList;
        notifyDataSetChanged();
    }

    public FgtPagerAdapter(FragmentManager fm, List<Fragment> fgtList) {
        this(fm, fgtList, true);
    }

    public FgtPagerAdapter(FragmentManager fm, List<Fragment> frgList, boolean destroy) {
        super(fm);
        this.frgList = frgList;
    }


    @Override
    public Fragment getItem(int position) {
        return frgList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (destroy) {
            super.destroyItem(container, position, object);
        }
    }

    @Override
    public int getCount() {
        if (frgList != null && !frgList.isEmpty()) {
            return frgList.size();
        }
        return 0;
    }


//    private int mChildCount = 0;
//
//    @Override
//    public void notifyDataSetChanged() {
//        mChildCount = getCount();
//        super.notifyDataSetChanged();
//    }
//
//
//    @Override
//    public int getItemPosition(@NonNull Object object) {
//        if (mChildCount > 0) {
//            mChildCount--;
//            return POSITION_NONE;
//        }
//        return super.getItemPosition(object);
//    }
}
