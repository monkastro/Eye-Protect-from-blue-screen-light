package com.eye.protect.bluelightfilter.eyeprotect;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;


public class SplashActivity extends Activity {

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_splash);
        try {

            new MyCountDownTimer(4000, 1000).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }






    public void goToNextLevel() {
        startActivity(new Intent(this, MainActivity.class));

    }

    private class MyCountDownTimer extends CountDownTimer {
        public void onTick(long j) {
        }

        MyCountDownTimer(long j, long j2) {
            super(j, j2);
        }

        public void onFinish() {

            goToNextLevel();

        }
    }
}
