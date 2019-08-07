package com.example.recyclerdemo.Controller;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.recyclerdemo.R;

public class Splash_screen extends AppCompatActivity {
    private ProgressBar pgsBar;
    private static int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        getSupportActionBar().hide(); // hide the title bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_splash_screen);
        ImageView simpleImageView=(ImageView) findViewById(R.id.imageView);
        simpleImageView.setImageResource(R.mipmap.ic_appicon);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent LoginIntent = new Intent(Splash_screen.this, MainActivity.class);
                startActivity(LoginIntent);
                finish();

            }
        },SPLASH_TIME_OUT);
    }
}
