package com.example.myapp.ui.shouye.shouye_ViewPager;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapp.R;
import com.example.myapp.UsersLogin_Activity;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class TuijianAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private Context context;
    private List<Article> data;

    private final int N_TYPE = 0;
    private final int F_TYPE = 1;

    private int Max_num = 4;  //预加载的数据  一共15条

    private Boolean isfootview = true;  //是否有footview


    public TuijianAdapter(Context context, List<Article> data){

        this.context = context;

        this.data = data;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.old_item,parent,false);
        View footview = LayoutInflater.from(parent.getContext()).inflate(R.layout.foot_item,parent,false);
        if (viewType == F_TYPE){
            return new RecyclerViewHolder(footview,F_TYPE);
        }else {
            return new RecyclerViewHolder(view,N_TYPE);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int i) {
        if (isfootview && (getItemViewType(i))== F_TYPE){
            //底部加载提示
            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            recyclerViewHolder.Loading.setText("加载中...");
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Max_num += 3;
                    notifyDataSetChanged();
                }
            },2000);
        }else {
            //这是ord_item的内容

            final RecyclerViewHolder recyclerViewHolder = (RecyclerViewHolder) holder;
            final Article post = data.get(i);
            recyclerViewHolder.username.setText(post.getTitle());
            recyclerViewHolder.time.setText(post.getCreatedAt());
            recyclerViewHolder.click.setText(""+post.getClick());
            recyclerViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    int position = recyclerViewHolder.getAdapterPosition();
//                    Toast.makeText(context,"点击测试,位置："+position, Toast.LENGTH_SHORT).show();
//
                    if (BmobUser.getCurrentUser(BmobUser.class) != null){
                        //GUN跳转失败
                        try{
                            Intent in = new Intent(context,Recive.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("username",post.getTitle());
                            bundle.putString("content",post.getContent());
                            bundle.putString("time",post.getCreatedAt());
                            in.putExtras(bundle);
                            //点击数+1
                            post.setClick(post.getClick()+1);
                            post.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                }
                            });
                            //in.putExtra("id",data.get(position).getObjectId());
                            context.startActivity(in);
                        }catch(Exception e){
                            Toast.makeText(context, "跳转失败，请重试！", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(context, "请先登录！", Toast.LENGTH_SHORT).show();
                        context.startActivity(new Intent(context, UsersLogin_Activity.class));
                    }
                }
            });
        }



    }

    @Override
    public int getItemViewType(int position) {
        if (position == Max_num - 1){
            return F_TYPE;  //底部type
        }else {
            return N_TYPE;
        }
    }

    @Override
    public int getItemCount() {
        if (data.size() < Max_num){
            return data.size();
        }
        return Max_num;
    }

    private class RecyclerViewHolder extends RecyclerView.ViewHolder {

        public TextView username,time,click; //ord_item的TextView
        public TextView Loading;


        public RecyclerViewHolder(View itemview, int view_type) {
            super(itemview);
            if (view_type == N_TYPE){
                username = itemview.findViewById(R.id.username);
                time = itemview.findViewById(R.id.time);
                click = itemview.findViewById(R.id.tv_click);
            }else if(view_type == F_TYPE){
                Loading = itemview.findViewById(R.id.footText);
            }
        }
    }
}
