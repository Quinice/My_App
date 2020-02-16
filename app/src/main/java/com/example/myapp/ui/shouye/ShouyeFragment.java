package com.example.myapp.ui.shouye;


import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myapp.R;
import com.google.android.material.tabs.TabLayout;
import com.jude.rollviewpager.RollPagerView;
import com.jude.rollviewpager.adapter.StaticPagerAdapter;


public class ShouyeFragment extends Fragment{

    private RollPagerView mRollViewPager;//轮播图容器
    private TabLayout tabs;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_fragment_shouye,container,false);
        //选项卡
//        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(),getFragmentManager());
//        ViewPager viewPager = view.findViewById(R.id.view_pager);
//        viewPager.setAdapter(sectionsPagerAdapter);
//        TabLayout tabs = view.findViewById(R.id.tabs);
//        tabs.setupWithViewPager(viewPager);
        //轮播图
        mRollViewPager = view.findViewById(R.id.roll_view_pager);
        mRollViewPager.setPlayDelay(2500);//设置图片轮换时间间隔
        mRollViewPager.setAnimationDurtion(500);//设置透明度
        mRollViewPager.setAdapter(new TestNormalAdapter());
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


        //选项卡
        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(view.getContext(),getChildFragmentManager());
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        tabs = view.findViewById(R.id.tabs);

        viewPager.setAdapter(sectionsPagerAdapter);
        tabs.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(0);

        super.onViewCreated(view, savedInstanceState);
    }


    private class TestNormalAdapter extends StaticPagerAdapter { //轮播图适配器
        private int[] imgs = {
            R.drawable.rollpager_image1,
            R.drawable.rollpager_image2,
            R.drawable.rollpager_image3,
            R.drawable.rollpager_image4,

        };
        @Override
        public View getView(ViewGroup container, int position) {
            ImageView view = new ImageView(container.getContext());
            view.setImageResource(imgs[position]);
            view.setScaleType(ImageView.ScaleType.CENTER_CROP);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            return view;
        }

        @Override
        public int getCount() {
            return imgs.length;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
