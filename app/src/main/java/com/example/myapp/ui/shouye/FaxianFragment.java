package com.example.myapp.ui.shouye;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.ui.shouye.shouye_ViewPager.Fruit;
import com.example.myapp.ui.shouye.shouye_ViewPager.FruitAdapter;

import java.util.ArrayList;
import java.util.List;

public class FaxianFragment extends Fragment {
    private List<Fruit> fruitList = new ArrayList<>();
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shouye_fragment_faxian,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFruits();
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view3);
        LinearLayoutManager layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        FruitAdapter adapter = new FruitAdapter(fruitList,view.getContext());
        recyclerView.setAdapter(adapter);
    }

    private void initFruits(){
        for(int i=0;i<2;i++){
            Fruit a = new Fruit("测试item1",R.drawable.logo2);
            fruitList.add(a);
            Fruit b = new Fruit("测试item2",R.drawable.logo2);
            fruitList.add(b);
            Fruit c = new Fruit("测试item3",R.drawable.logo2);
            fruitList.add(c);
            Fruit d = new Fruit("测试item4",R.drawable.logo2);
            fruitList.add(d);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
