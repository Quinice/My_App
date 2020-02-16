package com.example.myapp.ui.shouye;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.myapp.R;
import com.example.myapp.ui.shouye.shouye_ViewPager.TexieFragment;
import com.example.myapp.ui.shouye.shouye_ViewPager.TuijianFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {


    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
    private final Context mContext;

    public SectionsPagerAdapter(Context context,FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0)
        {   //getTabTitles(0);
            TuijianFragment tuijianfragment = new TuijianFragment();
            return tuijianfragment;
        }
        if (position == 1)
        {
            //getTabTitles(1);
            TexieFragment texieFragment1 = new TexieFragment();
            return texieFragment1;
        }
        if (position == 2)
        {
            TexieFragment texieFragment1 = new TexieFragment();
            return texieFragment1;
        }
        if (position == 3)
        {
            TexieFragment texieFragment = new TexieFragment();
            return texieFragment;
        }

        return null;
    }

    @Override
    public int getCount() {
        return TAB_TITLES.length;
    }
}
