package com.example.myapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobUser;

public class SplashActivity extends AppCompatActivity implements Animation.AnimationListener {

    private static final long SPLASH_TIME_OUT = 3500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        ImageView splashImage = findViewById(R.id.Splash_image);
        Animation animation = new ScaleAnimation(1.0f, 1.15f, 1.0f, 1.15f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f); // 将图片放大1.2倍，从中心开始缩放
        animation.setDuration(SPLASH_TIME_OUT); // 动画持续时间
        animation.setFillAfter(true); // 动画结束后停留在结束的位置
        animation.setAnimationListener(this);
        splashImage.startAnimation(animation);

        //Bmob初始化
        Bmob.initialize(this,"057133cf44081d73fd879c921f077aca");
    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    public void onAnimationEnd(Animation animation) {
        BmobUser bmobUser = BmobUser.getCurrentUser(BmobUser.class);
        if(bmobUser != null){
            //用户登陆过，则直接跳到主界面
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }
        else {
            //用户未登陆，则跳到登陆界面
            Intent i = new Intent(SplashActivity.this, UsersLogin_Activity.class);
            startActivity(i);
            finish();
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN    //顶部标题栏
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            );
        }
    }

}
