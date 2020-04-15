package com.LilBro.LitBro.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.LilBro.LitBro.R;

public class SplashActivity extends AppCompatActivity {


    private ProgressBar mProgressBar;
    private TextView mTV;

    private int mProgressStatus = 0;

    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mProgressBar = findViewById(R.id.progressBar);
        mTV = findViewById(R.id.helloInv);

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (mProgressStatus < 100){
                    mProgressStatus++;
                    android.os.SystemClock.sleep(22);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mProgressBar.setProgress(mProgressStatus);
                            if(mProgressStatus==50){
                                mTV.setVisibility(View.VISIBLE);
                            }
                        }
                    });
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        Intent mySuperIntent = new Intent(SplashActivity.this, ConnextionActivity.class);
                        startActivity(mySuperIntent);
                        finish();
                    }
                });
            }
        }).start();
    }

}