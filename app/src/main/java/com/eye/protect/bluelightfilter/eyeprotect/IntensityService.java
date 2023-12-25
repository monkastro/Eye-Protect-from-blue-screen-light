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


public class IntensityService extends Service {
    static final  boolean $assertionsDisabled = false;
    public static final String TAG = "IntensityService";
    private int currentLevel = 0;
    SharedPreferences.Editor editor;
    private View mOverlayView;
    private WindowManager.LayoutParams params;
    SharedPreferences sharedPreferences;


    private WindowManager f70wm;

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
        layoutParams.alpha = 0.1f;
        layoutParams.dimAmount = 0.0f;
        this.f70wm = (WindowManager) getSystemService("window");
        this.mOverlayView = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.fiter_layout, (ViewGroup) null);
        if (Build.VERSION.SDK_INT < 23) {
            try {
                this.f70wm.addView(this.mOverlayView, this.params);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else if (Settings.canDrawOverlays(this)) {
            try {
                this.f70wm.addView(this.mOverlayView, this.params);
            } catch (Exception e2) {
                Log.e(TAG, "onCreate: " + e2.getMessage());
            }
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (Build.VERSION.SDK_INT < 23) {
            this.currentLevel = this.sharedPreferences.getInt("level", this.currentLevel);
            View view = this.mOverlayView;
            double d = (double) this.currentLevel;
            Double.isNaN(d);
            view.setBackgroundColor(Color.rgb(225, 225, (int) (255.0d - (Math.sqrt((d * 1.0d) / 100.0d) * 255.0d))));
            this.f70wm.updateViewLayout(this.mOverlayView, this.params);
            return 3;
        } else if (!Settings.canDrawOverlays(this)) {
            return 3;
        } else {
            this.currentLevel = this.sharedPreferences.getInt("level", this.currentLevel);
            View view2 = this.mOverlayView;
            double d2 = (double) this.currentLevel;
            Double.isNaN(d2);
            view2.setBackgroundColor(Color.rgb(225, 225, (int) (255.0d - (Math.sqrt((d2 * 1.0d) / 100.0d) * 255.0d))));
            this.f70wm.updateViewLayout(this.mOverlayView, this.params);
            return 3;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        this.f70wm = (WindowManager) getSystemService("window");
        this.f70wm.removeView(this.mOverlayView);
    }
}
