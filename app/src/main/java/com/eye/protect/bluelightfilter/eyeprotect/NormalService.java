package com.eye.protect.bluelightfilter.eyeprotect;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

public class NormalService extends Service {
    //static final  boolean $assertionsDisabled = false;
    public static final String TAG = "ButtonServiceNormal";
    private int currentLevel = 0;
    SharedPreferences.Editor editor;
    private View mOverlayView;
    private WindowManager.LayoutParams params;
    SharedPreferences sharedPreferences;

    private WindowManager f72wm;

    public IBinder onBind(Intent intent) {
        throw null;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        this.sharedPreferences = getSharedPreferences("lightDimmer", 0);
        this.editor = this.sharedPreferences.edit();
        if (Build.VERSION.SDK_INT < 26) {
            this.params = new WindowManager.LayoutParams(-1, -1, 2038, 298, -3);
        } else {
            this.params = new WindowManager.LayoutParams(-1, -1, 2038, 314, -3);
        }
        WindowManager.LayoutParams layoutParams = this.params;
        layoutParams.alpha = 0.2f;
        layoutParams.dimAmount = 0.0f;
        this.f72wm = (WindowManager) getSystemService("window");
        this.mOverlayView = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.fiter_layout, (ViewGroup) null);
        if (Build.VERSION.SDK_INT < 23) {
            try {
                this.f72wm.addView(this.mOverlayView, this.params);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else if (Settings.canDrawOverlays(this)) {
            try {
                this.f72wm.addView(this.mOverlayView, this.params);
            } catch (Exception e2) {
                Log.e(TAG, "onCreate: " + e2.getMessage());
            }
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (Build.VERSION.SDK_INT < 23) {
            this.currentLevel = this.sharedPreferences.getInt("level", this.currentLevel);
            this.mOverlayView.setBackgroundColor(Color.rgb(255, 197, 143));
            this.f72wm.updateViewLayout(this.mOverlayView, this.params);
            return 1;
        } else if (!Settings.canDrawOverlays(this)) {
            return 1;
        } else {
            this.currentLevel = this.sharedPreferences.getInt("level", this.currentLevel);
            this.mOverlayView.setBackgroundColor(Color.rgb(255, 197, 143));
            this.f72wm.updateViewLayout(this.mOverlayView, this.params);
            return 1;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        this.f72wm = (WindowManager) getSystemService("window");
        this.f72wm.removeView(this.mOverlayView);
    }
}
