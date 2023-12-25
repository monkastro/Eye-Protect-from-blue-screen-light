package com.eye.protect.bluelightfilter.eyeprotect;

import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import androidx.recyclerview.widget.ItemTouchHelper;


public class SleepService extends Service {
    static final  boolean Disabled = false;
    public static final String TAG = "BlueLightFilterService";
    private int currentLevel = 0;
    private View mOverlayView;
    private WindowManager.LayoutParams params;

    /* renamed from: wm */
    private WindowManager f103wm;

    public IBinder onBind(Intent intent) {
        throw null;
    }

    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate");
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        edit.putString("check", "sleep");
        edit.apply();
        if (Build.VERSION.SDK_INT < 26) {
            this.params = new WindowManager.LayoutParams(-1, -1, 2038, 298, -3);
        } else {
            this.params = new WindowManager.LayoutParams(-1, -1, 2038, 314, -3);
        }
        WindowManager.LayoutParams layoutParams = this.params;
        layoutParams.alpha = 0.2f;
        layoutParams.dimAmount = 0.0f;
        this.f103wm = (WindowManager) getSystemService("window");
        this.mOverlayView = ((LayoutInflater) getSystemService("layout_inflater")).inflate(R.layout.fiter_layout, (ViewGroup) null);
        if (Build.VERSION.SDK_INT < 23) {
            try {
                this.f103wm.addView(this.mOverlayView, this.params);
            } catch (Exception e) {
                Log.e(TAG, "onCreate: " + e.getMessage());
            }
        } else if (Settings.canDrawOverlays(this)) {
            try {
                this.f103wm.addView(this.mOverlayView, this.params);
            } catch (Exception e2) {
                Log.e(TAG, "onCreate: " + e2.getMessage());
            }
        }
    }

    public int onStartCommand(Intent intent, int i, int i2) {
        if (Build.VERSION.SDK_INT < 23) {
            this.currentLevel = intent.getIntExtra("level", this.currentLevel);
            this.mOverlayView.setBackgroundColor(Color.rgb(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, 187));
            this.f103wm.updateViewLayout(this.mOverlayView, this.params);
            return 3;
        } else if (!Settings.canDrawOverlays(this)) {
            return 3;
        } else {
            this.currentLevel = intent.getIntExtra("level", this.currentLevel);
            this.mOverlayView.setBackgroundColor(Color.rgb(ItemTouchHelper.Callback.DEFAULT_DRAG_ANIMATION_DURATION, 100, 187));
            this.f103wm.updateViewLayout(this.mOverlayView, this.params);
            return 3;
        }
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        this.f103wm = (WindowManager) getSystemService("window");
        this.f103wm.removeView(this.mOverlayView);
    }
}
