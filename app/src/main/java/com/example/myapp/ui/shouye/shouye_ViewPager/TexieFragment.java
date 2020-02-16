package com.example.myapp.ui.shouye.shouye_ViewPager;

import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.myapp.R;

import java.util.ArrayList;
import java.util.List;

public class TexieFragment extends Fragment {

    private List<ImageStagger> fruitList = new ArrayList<>();
    private RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_tab2,container,false);
        return view;


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initFruits();
        recyclerView = view.findViewById(R.id.recycler_view2);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        ImageStaggerAdapter adapter = new ImageStaggerAdapter(fruitList,view.getContext());
        recyclerView.addItemDecoration(new MyDecoration());

        recyclerView.setAdapter(adapter);



    }

    class MyDecoration extends RecyclerView.ItemDecoration{
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int gap = getResources().getDimensionPixelSize(R.dimen.dividerHeight);
            outRect.set(gap,gap,gap,gap);
        }
    }

    private void initFruits() {
        for (int i = 0; i < 2; i++) {
            ImageStagger apple = new ImageStagger(R.drawable.texieimage_item2);
            fruitList.add(apple);
            ImageStagger banana = new ImageStagger(R.drawable.texieimage_item);
            fruitList.add(banana);
            ImageStagger orange = new ImageStagger(R.drawable.rollpager_image4);
            fruitList.add(orange);
            ImageStagger orange2 = new ImageStagger(R.drawable.texieimage_item3);
            fruitList.add(orange2);

        }
    }


}
