package com.example.myapp.ui.notifications;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;
import com.example.myapp.ui.User;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends AppCompatActivity {

    private Button mbtn;
    private EditText mtv_userid;
    private EditText mtv_password;
    private EditText mtv_password_right;
    SharedPreferences msharedPreferences;
    SharedPreferences.Editor meditor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //注册界面
        setContentView(R.layout.activity_register);
        mbtn = findViewById(R.id.register_bt);
        mtv_userid = findViewById(R.id.register_id_et);
        mtv_password = findViewById(R.id.register_pw_et);
        mtv_password_right = findViewById(R.id.register_pwright_et);
        msharedPreferences = getSharedPreferences("usersdata",MODE_PRIVATE);//打开‘usersdata’数据文本
        meditor = msharedPreferences.edit();

        mtv_userid.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!checkUsername(s.toString())){
                    mtv_userid.setError("用户名只允许用字母和数字！");
                    mbtn.setClickable(false);
                }
                else if(s.length()<6){
                    mtv_userid.setError("长度必须大于或等于6！");
                    mbtn.setClickable(false);
                }
                else{
                    mbtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mtv_password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(!checkUsername(s.toString())){
                    mtv_password.setError("密码只允许用字母和数字！");
                    mbtn.setClickable(false);
                }
                else if(s.length()<6){
                    mtv_password.setError("长度必须大于或等于6！");
                    mbtn.setClickable(false);
                }
                else{
                    mbtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mtv_password_right.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(!s.toString().equals(mtv_password.getText().toString())){
                    mtv_password_right.setError("两次密码不一致！");
                    mbtn.setClickable(false);
                }
                else {
                    mbtn.setClickable(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mtv_userid.getText().toString().equals("")||mtv_password.getText().toString().equals("")||mtv_password_right.getText().toString().equals("")){
                    Toast toast = Toast.makeText(getApplicationContext(),"用户名或密码不能为空",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else if(msharedPreferences.getString("userid","").equals(mtv_userid.getText().toString())){
                    Toast toast = Toast.makeText(getApplicationContext(),"用户名已存在！",Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER,0,0);
                    toast.show();
                }
                else{

                    User user = new User();
                    user.setUsername(mtv_userid.getText().toString().trim());
                    user.setPassword(mtv_password.getText().toString().trim());
                    user.signUp(new SaveListener<User>() {
                        @Override
                        public void done(final User user, BmobException e) {
                            if(e == null){
                                Toast.makeText(getApplicationContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                //注册失败，对用户表进行查询，查找用户名是否存在
                                BmobQuery<User> categoryBmobQuery = new BmobQuery<>();
                                categoryBmobQuery.addWhereEqualTo("username", mtv_userid.getText().toString().trim());
                                categoryBmobQuery.findObjects(new FindListener<User>() {
                                    @Override
                                    public void done(List<User> object, BmobException e) {
                                        if (e == null) {
                                            Toast.makeText(getApplicationContext(), "用户名已存在！" + object.size(),Toast.LENGTH_LONG).show();
                                        } else {

                                            Toast.makeText(getApplicationContext(), "注册失败！"+e, Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });

                            }
                        }
                    });
                }
            }
        });
    }
    public boolean checkUsername(String str) {

        String regexp = "^[a-zA-Z0-9]+";
        Pattern pattern = Pattern.compile(regexp);
        Matcher matcher = pattern.matcher(str);
        return matcher.matches();

    }


}
