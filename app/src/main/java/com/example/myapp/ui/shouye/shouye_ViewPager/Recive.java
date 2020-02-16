package com.example.myapp.ui.shouye.shouye_ViewPager;


import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myapp.R;

import java.util.Timer;
import java.util.TimerTask;

/**
 *
 */
public class Recive extends AppCompatActivity {

    private TextView username,time;
    private WebView content;
    private ImageView back;
    private WebSettings mwebSettings;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recive);

        username = findViewById(R.id.re_username);
        content = findViewById(R.id.re_content);
        time = findViewById(R.id.re_time);
        back = findViewById(R.id.back);
        mwebSettings = content.getSettings();

        mwebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        Bundle bundle = getIntent().getExtras();
        String usernamea = bundle.getString("username");
        String contenta = bundle.getString("content");
        String timea = bundle.getString("time");
        username.setText(usernamea);
        content.loadDataWithBaseURL(null,contenta, "text/html",  "utf-8", null);
        time.setText(timea);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        finish();
                    }
                };
                Timer timer = new Timer();
                timer.schedule(task,500);

            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if(hasFocus && Build.VERSION.SDK_INT>=19){
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        content.destroy();
    }

}
