package com.example.myapp.ui.shouye.shouye_ViewPager;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;

import java.util.List;

public class ImageStaggerAdapter extends RecyclerView.Adapter<ImageStaggerAdapter.ViewHolder> {
    private List<ImageStagger> mFruitList;
    private Context mcontext;

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView fruitImage;


        public ViewHolder (View view)
        {
            super(view);
            fruitImage = view.findViewById(R.id.texie_image);
        }

    }

//    public getContextID(Context context){
//        this.mcontext=context;
//    }


    public ImageStaggerAdapter(List <ImageStagger> fruitList,Context context){
        mFruitList = fruitList;
        this.mcontext=context;
    }

    @Override

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.texie_item,parent,false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position){

        ImageStagger fruit = mFruitList.get(position);
        holder.fruitImage.setImageResource(fruit.getImageId());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast toastimage = new Toast(mcontext);
                LayoutInflater inflater = LayoutInflater.from(mcontext);
                View view=inflater.inflate(R.layout.texie_toast,null);
                ImageView imageView = view.findViewById(R.id.iv_image);
                imageView.setImageResource(mFruitList.get(position).getImageId());
                toastimage.setView(view);
                toastimage.setDuration(Toast.LENGTH_SHORT);
                toastimage.setGravity(Gravity.CENTER,0,0);
                toastimage.show();
                //Toast.makeText(mcontext,"position"+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount(){
        return mFruitList.size();
    }
}
