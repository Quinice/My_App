package com.example.myapp.ui.shouye.shouye_ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class TuijianFragment extends Fragment {

    private RecyclerView recyclerView;
    //private FruitAdapter adapter;
    private TuijianAdapter homeAdapter;
    private TextView mtv_loading;
    private List<Article> data = new ArrayList<>();
    private SwipeRefreshLayout srlayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab3,container,false);
        srlayout = view.findViewById(R.id.swipe);
        mtv_loading = view.findViewById(R.id.tv_loading);
//        adapter = new FruitAdapter(fruitList,view.getContext());
//        recyclerView.addItemDecoration(new MyDecoration());
//        recyclerView.setAdapter(adapter);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recycler_view);
        Bmob.initialize(getActivity(),"057133cf44081d73fd879c921f077aca");
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(homeAdapter);

        //刷新获得初始数据
        Refresh();

        //下滑时  刷新圈圈的颜色
        srlayout.setColorSchemeResources(android.R.color.holo_green_light,android.R.color.holo_red_light,android.R.color.holo_blue_light);
        //下滑事件
        srlayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                //刷新；
                Refresh();
            }
        });
    }


    private void Refresh() {

        BmobQuery<Article> Po = new BmobQuery<Article>();
        Po.order("-createdAt");
        Po.setLimit(1000);
        Po.findObjects(new FindListener<Article>() {
            @Override
            public void done(List<Article> list, BmobException e) {
                srlayout.setRefreshing(false);
                if (e == null){
                    data = list;
                    homeAdapter = new TuijianAdapter(getActivity(),data);
                    LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                    recyclerView.setLayoutManager(layoutManager);
                    //recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                    recyclerView.setAdapter(homeAdapter);
                    //mtv_loading.setText("");
                    mtv_loading.setTextSize(0);//获得数据后，“加载中”字样去除
                }else {
                    mtv_loading.setText("");
                    mtv_loading.setTextSize(0);
                    mtv_loading.setVisibility(View.INVISIBLE);
                    Toast.makeText(getActivity(), "获取数据失败"+e, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

}
