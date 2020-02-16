package com.example.myapp.ui.notifications;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.myapp.R;
import com.example.myapp.UsersLogin_Activity;
import com.example.myapp.ui.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class UserImformationActivity extends AppCompatActivity {

    private Button mbt_loginout;
    private TextView mtv_sex;
    private TextView mtv_nickname1;
    private LinearLayout mlayout_imform;

    //相机
    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};
    public static final int REQUEST_PICK_IMAGE = 11101;
    private mine_ImageViewPlus mShowImg;

    SharedPreferences msharedPreferences;
    SharedPreferences.Editor meditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_imformation);
        mbt_loginout = findViewById(R.id.login_out_bt);
        ImageView mbt_imform_back = findViewById(R.id.imform_back_iv);
        mlayout_imform = findViewById(R.id.imform_list);

        mtv_nickname1 = findViewById(R.id.imform_nickname1_tv);
        TextView mtv_userid = findViewById(R.id.imform_userid_tv);
        final TextView mtv_nickname = findViewById(R.id.imform_nickname_tv);
        TextView mtv_name = findViewById(R.id.imform_name_tv);
        mtv_sex = findViewById(R.id.imform_sex_tv);
        final TextView mtv_age = findViewById(R.id.imform_age_tv);
        TextView mtv_phone = findViewById(R.id.imform_phone_tv);
        TextView mtv_emeil = findViewById(R.id.imform_emeil_tv);

        ImageView miv_nickname = findViewById(R.id.imform_nickname_iv);
        ImageView miv_sex = findViewById(R.id.imform_sex_iv);
        ImageView miv_age = findViewById(R.id.imform_age_iv);
        ImageView miv_phone = findViewById(R.id.imform_phone_iv);
        ImageView miv_emeil = findViewById(R.id.imform_emeil_iv);
        mShowImg = findViewById(R.id.user_iv_icon);


        msharedPreferences = getSharedPreferences("usersdata",MODE_PRIVATE);
        meditor = msharedPreferences.edit();


        //判断用户是否登陆
        final BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        if(bmobUser != null){
            mlayout_imform.setVisibility(View.VISIBLE);
            mbt_loginout.setText("退出登陆");
            mtv_userid.setText(msharedPreferences.getString("userid",""));
            mtv_nickname.setText(msharedPreferences.getString("nickname","未设置").trim());
            mtv_nickname1.setText(msharedPreferences.getString("nickname","未设置").trim());
            mtv_name.setText(msharedPreferences.getString("name","未实名认证"));
            mtv_sex.setText(msharedPreferences.getString("sex","未设置"));
            mtv_age.setText(""+msharedPreferences.getInt("age",0));
            mtv_phone.setText(msharedPreferences.getString("phone","未设置"));
            mtv_emeil.setText(msharedPreferences.getString("emeil","未设置"));
            if(!msharedPreferences.getString("pic","").equals("")) {
                Bitmap bitmap = BitmapFactory.decodeFile(msharedPreferences.getString("pic", ""));
                mShowImg.setImageBitmap(bitmap);
            }
        }
        else {
            mlayout_imform.setVisibility(View.GONE);
            mbt_loginout.setText("请先登陆");
        }
        mbt_loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mbt_loginout.getText().toString().equals("退出登陆")){

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserImformationActivity.this);
                    builder.setTitle("提示").setMessage("是否确认退出？")
                            .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    BmobUser.logOut();
                                    //数据存储
                                    if(!msharedPreferences.getString("userid","").equals(""));
                                    {
                                        meditor.putString("userid","");
                                        meditor.putString("pic","");
                                        meditor.apply();
                                    }
                                    mShowImg.setImageResource(R.drawable.logo2);
                                    mbt_loginout.setText("请先登陆");
                                    //退出时隐藏个人信息列表
                                    mlayout_imform.setVisibility(View.GONE);
                                    mtv_nickname1.setText("未登录");
                                }
                            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).show();

                }
                else if(mbt_loginout.getText().toString().equals("请先登陆")){
                    Intent intent = new Intent(UserImformationActivity.this, UsersLogin_Activity.class);
                    startActivityForResult(intent,0);
                }
            }
        });

        //返回按键
        mbt_imform_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(msharedPreferences.getString("userid","").equals("")){
                    String user_pic = msharedPreferences.getString("pic","");
                    String user_id = "未登录";
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("pic",user_pic);
                    bundle1.putString("userid",user_id);
                    intent.putExtras(bundle1);
                    setResult(1,intent);
                }
                else {
                    String user_pic = msharedPreferences.getString("pic","");
                    String user_id = msharedPreferences.getString("userid","");
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("pic",user_pic);
                    bundle1.putString("userid",user_id);
                    intent.putExtras(bundle1);
                    setResult(1,intent);
                }
                finish();
            }
        });
        //修改昵称
        miv_nickname.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //
                final AlertDialog builder1 = new AlertDialog.Builder(UserImformationActivity.this).create();
                final View view=LayoutInflater.from(UserImformationActivity.this).inflate(R.layout.updata_nickname,null);
                final EditText editText = view.findViewById(R.id.update_nickname);
                final Button button = view.findViewById(R.id.update_bt);
                final Button button_false = view.findViewById(R.id.update_bt_false);
                editText.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        if(s.length()>10){
                            editText.setError("昵称长度必须小于等于10！");
                            button.setClickable(false);
                        }
                        else if(!checkUsername(s.toString())){
                            if(s.length()!=0){
                                editText.setError("昵称不能含有特殊字符！");

                            }else{
                                editText.setError("昵称不能为空！");
                            }
                            button.setClickable(false);
                        }
                        else {
                            button.setClickable(true);
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }
                });
                builder1.setView(view);
                builder1.setTitle("修改昵称：");
                builder1.setCancelable(false);
                builder1.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    //
                        User user = BmobUser.getCurrentUser(User.class);
                        final String nickname = editText.getText().toString();
                        user.setNickname(nickname);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    mtv_nickname.setText(nickname);
                                    mtv_nickname1.setText(nickname);
                                    meditor.putString("nickname",nickname);
                                    meditor.apply();
                                    Toast.makeText(UserImformationActivity.this,"昵称修改成功！",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(UserImformationActivity.this,"修改失败"+e,Toast.LENGTH_LONG).show();
                                }
                            }
                        });

                        builder1.cancel();
                        //view.setSystemUiVisibility(View.GONE);
                    }
                });
                button_false.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder1.cancel();
                    }
                });
            }
        });
        //修改性别
        miv_sex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] array = new String[]{"男","女"};
                AlertDialog.Builder builder = new AlertDialog.Builder(UserImformationActivity.this);
                builder.setTitle("选择性别：").setItems(array, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        User user = BmobUser.getCurrentUser(User.class);
                        user.setSex(array[which]);
                        user.update(new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    mtv_sex.setText(array[which]);
                                    meditor.putString("sex",array[which]);
                                    meditor.apply();
                                    Toast.makeText(UserImformationActivity.this,"性别修改成功！",Toast.LENGTH_SHORT).show();
                                }
                                else {
                                    Toast.makeText(UserImformationActivity.this,"修改失败"+e,Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                }).show();
            }
        });
        //修改年龄
        miv_age.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog builder1 = new AlertDialog.Builder(UserImformationActivity.this).create();
                final View view=LayoutInflater.from(UserImformationActivity.this).inflate(R.layout.updata_age,null);
                final EditText editText = view.findViewById(R.id.update_age);
                final Button button = view.findViewById(R.id.update_age_bt);
                final Button button_false = view.findViewById(R.id.update_age_bt_false);
                builder1.setView(view);
                builder1.setTitle("修改年龄：");
                builder1.setCancelable(false);
                builder1.show();
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String age = editText.getText().toString();
                        final int x = Integer.parseInt(age);
                        if(x>=0&&x<=100){
                            User user = BmobUser.getCurrentUser(User.class);
                            user.setAge(x);
                            user.update(new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if(e == null){
                                        mtv_age.setText(age);
                                        meditor.putInt("age",x);
                                        meditor.apply();
                                        Toast.makeText(UserImformationActivity.this,"年龄修改成功！",Toast.LENGTH_SHORT).show();
                                    }
                                    else {
                                        Toast.makeText(UserImformationActivity.this,"修改失败"+e,Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                            builder1.cancel();
                        }
                        else{
                            Toast.makeText(UserImformationActivity.this,"请输入0-100间的数字！",Toast.LENGTH_SHORT).show();
                        }
                        //view.setSystemUiVisibility(View.GONE);
                    }
                });

                button_false.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        builder1.cancel();
                    }
                });
            }
        });


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                boolean writeExternalStorage = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                boolean readExternalStorage = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                if (grantResults.length > 0 && writeExternalStorage && readExternalStorage) {
                    getImage();
                } else {
                    Toast.makeText(this, "请设置必要权限", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    private void getImage() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQUEST_PICK_IMAGE:
                    if (data != null) {
                        String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(this, data.getData());
                        Toast.makeText(UserImformationActivity.this,"获取成功",Toast.LENGTH_SHORT).show();
                        showImg(realPathFromUri);
                    } else {
                        Toast.makeText(this, "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
        }

    }
    //打开相机
    public void openCamera(View view) {
         AlertDialog.Builder buildercamera = new AlertDialog.Builder(UserImformationActivity.this);
         buildercamera.setTitle("修改头像").setMessage("确认修改头像？").setPositiveButton("确定", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 ActivityCompat.requestPermissions(UserImformationActivity.this, mPermissionList, 100);
             }
         }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
             @Override
             public void onClick(DialogInterface dialog, int which) {
                 dialog.cancel();
             }
         }).show();

    }


    public void showImg(String path){
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        User user = BmobUser.getCurrentUser(User.class);
        user.setPic(path);
        user.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                //
            }
        });
        meditor.putString("pic",path);
        meditor.apply();
        mShowImg.setImageBitmap(bitmap);
    }
    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE   //顶部标题栏
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

    public boolean checkUsername(String str) {
        String regexp = "^[\\u4E00-\\u9FA5A-Za-z0-9_]+$";//字母或数字
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }
}
