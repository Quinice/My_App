package com.example.myapp;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.ui.User;
import com.example.myapp.ui.notifications.RegisterActivity;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class UsersLogin_Activity extends AppCompatActivity {

    private TextView mTv_register;
    private EditText mTv_login;
    private EditText mTv_login_password;
    private Button mBtn;
    SharedPreferences msharedPreferences;
    SharedPreferences.Editor meditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //登陆界面
        setContentView(R.layout.activity_users_login_);
        mTv_register = findViewById(R.id.register_tv);
        mTv_login = findViewById(R.id.login_id_et);
        mBtn = findViewById(R.id.login_users_bt);
        mTv_login_password = findViewById(R.id.login_password_et);
        msharedPreferences = getSharedPreferences("usersdata",MODE_PRIVATE);
        meditor = msharedPreferences.edit();

        //注册界面按钮 填下划线
        mTv_register.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
        mTv_register.getPaint().setAntiAlias(true);
        //由登陆界面-->注册界面
        mTv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UsersLogin_Activity.this, RegisterActivity.class);
                startActivityForResult(intent,0);
            }
        });
        //登陆按钮事件
        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userid = msharedPreferences.getString("userid","");
                String userpassword = msharedPreferences.getString("userpassword","");
                Log.d("id",userid);
                Log.d("password",userpassword);
                if(mTv_login.getText().toString().equals("")||mTv_login_password.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(),"用户或密码不能为空！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
//                else if(!userpassword.equals(mTv_login_password.getText().toString())){
//                    Toast toast = Toast.makeText(getApplicationContext(),"密码不正确！",Toast.LENGTH_SHORT);
//                    toast.setGravity(Gravity.CENTER,0,0);
//                    toast.show();
//                }
                else{
                    //登陆成功后，返回用户名
                    final User user = new User();
                    user.setUsername(mTv_login.getText().toString().trim());
                    user.setPassword(mTv_login_password.getText().toString().trim());
                    Intent intent = new Intent();
                    Bundle bundle1 = new Bundle();
                    final String user_id = user.getUsername();
                    bundle1.putString("userid",user_id);
                    intent.putExtras(bundle1);
                    setResult(Activity.RESULT_OK,intent);

                    user.login(new SaveListener<User>() {
                        @Override
                        public void done(User u, BmobException e) {
                            if(e == null){
                                final String nickname = u.getNickname();
                                final String name = u.getRName();
                                final String sex = u.getSex();
                                final String phone = u.getMobilePhoneNumber();
                                final String emeil = u.getEmail();
                                final String image_icon = u.getPic();
                                final int age = u.getAge();
                                Toast.makeText(getApplicationContext(),"登陆成功！",Toast.LENGTH_LONG).show();
                                //数据存储
                                if(!msharedPreferences.getString("userid","").equals(""));
                                {
                                    meditor.putString("userid",user_id);
                                    meditor.putString("nickname",nickname);
                                    meditor.putString("name",name);
                                    meditor.putString("sex",sex);
                                    meditor.putInt("age",age);
                                    meditor.putString("phone",phone);
                                    meditor.putString("emeil",emeil);
                                    meditor.putString("pic",image_icon);
                                    meditor.apply();
                                }

                                ////延时2秒跳转，否则出错（主界面状态栏问题）
                                TimerTask task = new TimerTask() {
                                    @Override
                                    public void run() {
                                        Intent intent = new Intent(UsersLogin_Activity.this, MainActivity.class);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                };
                                Timer timer = new Timer();
                                timer.schedule(task, 2000);

                            }else{
                                Toast.makeText(getApplicationContext(),"登陆失败！（用户名或密码不正确）"+e,Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });

        //用户名的判断
        mTv_login.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!checkUsername(s.toString())){
                    mTv_login.setError("密码只允许用字母和数字！");
                    mBtn.setClickable(false);
                }
                else if(s.length()<6){
                    mTv_login.setError("长度必须大于或等于6！");
                    mBtn.setClickable(false);
                }
                else{
                    mBtn.setClickable(true);
                }
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //密码的判断
        mTv_login_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!checkUsername(s.toString())){
                    mTv_login_password.setError("密码只允许用字母和数字！");
                    mBtn.setClickable(false);
                }
                else if(s.length()<6){
                    mTv_login_password.setError("长度必须大于或等于6！");
                    mBtn.setClickable(false);
                }
                else{
                    mBtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //接收由 注册界面 传来的userid->mTv
        if(data!=null) {
            mTv_login.setText(Objects.requireNonNull(data.getExtras()).getString("userid"));
        }
    }

    //字符判断
    public boolean checkUsername(String str) {
        String regexp = "^[a-zA-Z0-9]+";//字母或数字
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();
    }

}
