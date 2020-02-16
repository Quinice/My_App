package com.example.myapp.ui.notifications;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myapp.R;


public class NotificationsFragment extends Fragment {

    private TextView mtv_id;
    private ImageView mIv;
    private SharedPreferences msharedpreferences;
    SharedPreferences.Editor meditor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_notifications, container, false);


        return view;
    }

    @Override
    public void onViewCreated(@NonNull final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mtv_id = view.findViewById(R.id.usersid_tv);
        mIv = view.findViewById(R.id.users_iv_icon);
        mtv_id.setOnClickListener(new onClick());
        mIv.setOnClickListener(new onClick());
        mtv_id.setText("加载中...");
        msharedpreferences = getActivity().getSharedPreferences("usersdata", Context.MODE_PRIVATE);

        //登陆用户名
        String name = msharedpreferences.getString("userid","");
        if(!name.equals("")){
            mtv_id.setText(name);
            if(!msharedpreferences.getString("pic","").equals("")){
                Bitmap bitmap = BitmapFactory.decodeFile(msharedpreferences.getString("pic",""));
                mIv.setImageBitmap(bitmap);
            }else{
                mIv.setImageResource(R.drawable.logo2);
            }

        }
        else{
            mtv_id.setText("未登陆");
            mIv.setImageResource(R.drawable.logo2);
        }
//        BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
//        if(bmobUser != null){
//            mtv_id.setText(name);
//        }
//        else {
//            mtv_id.setText("未登陆");
//        }
//        BmobUser user = BmobUser.getCurrentUser(User.class);
//        String id = user.getObjectId();
//        BmobQuery<User> myuser = new BmobQuery<User>();
//        myuser.getObject(id, new QueryListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if(e == null){
//                    mtv_id.setText(user.getUsername());
//                }
//            }
//        });
    }

    public class onClick implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            Intent intent = new Intent();

            switch (v.getId()){
                case R.id.usersid_tv:
                case R.id.users_iv_icon:
                    //跳转至用户信息界面
                    intent.setClass(v.getContext(), UserImformationActivity.class);
                    startActivityForResult(intent,0);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + v.getId());
            }
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null&&requestCode == 0){

                mtv_id.setText(data.getExtras().getString("userid"));

                if(data.getExtras().getString("pic").equals("")){
                    mIv.setImageResource(R.drawable.logo2);
                }
                else {
                    Bitmap bitmap = BitmapFactory.decodeFile(data.getExtras().getString("pic"));
                    mIv.setImageBitmap(bitmap);
                }


        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Toast.makeText(getActivity(),"sdasdasdasd",Toast.LENGTH_SHORT).show();
    }
}