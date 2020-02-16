package com.example.myapp.ui.shouye.shouye_ViewPager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.List;

public class FruitAdapter extends RecyclerView.Adapter<FruitAdapter.ViewHolder> {
    private List<Fruit> mFruitList;
    private Context mcontext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fruitImage;
        TextView fruitName;


        public ViewHolder (View view)
        {
            super(view);
            fruitImage = view.findViewById(R.id.fruit_image);
            fruitName = view.findViewById(R.id.fruitname);
        }

    }

    public  FruitAdapter (List <Fruit> fruitList,Context context){
        mFruitList = fruitList;
        mcontext = context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.tuijian_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){

        Fruit fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());
        holder.fruitName.setText(fruit.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mcontext,"点击测试,位置："+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return mFruitList.size();
    }
}
