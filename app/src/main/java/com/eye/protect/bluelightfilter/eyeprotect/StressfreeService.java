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


public class StressfreeService extends Service {
    static final  boolean Disabled = false;
    public static final String TAG = "ButtonServiceStress";
    private int currentLevel = 0;
    SharedPreferences.Editor editor;
    private View mOverlayView;
    private WindowManager.LayoutParams params;
    SharedPreferences sharedPreferences;


    private WindowManager f104wm;

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
        this.f104wm = (WindowManager) getSystemService("window");
        this.mOverlayView = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.fiter_layout, (ViewGroup) null);
        if (Build.VERSION.SDK_INT < 23) {
            try {
                this.f104wm.addView(this.mOverlayView, this.params);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else if (Settings.canDrawOverlays(this)) {
            try {
                this.f104wm.addView(this.mOverlayView, this.params);
            } catch (Exception e2) {
                Log.e(TAG, "onCreate: " + e2.getMessage());
            }
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (Build.VERSION.SDK_INT < 23) {
            this.currentLevel = this.sharedPreferences.getInt("level", this.currentLevel);
            this.mOverlayView.setBackgroundColor(Color.rgb(170, 139, 112));
            this.f104wm.updateViewLayout(this.mOverlayView, this.params);
            return 1;
        } else if (!Settings.canDrawOverlays(this)) {
            return 1;
        } else {
            this.currentLevel = this.sharedPreferences.getInt("level", this.currentLevel);
            this.mOverlayView.setBackgroundColor(Color.rgb(170, 139, 112));
            this.f104wm.updateViewLayout(this.mOverlayView, this.params);
            return 1;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        this.f104wm = (WindowManager) getSystemService("window");
        this.f104wm.removeView(this.mOverlayView);
    }
}
