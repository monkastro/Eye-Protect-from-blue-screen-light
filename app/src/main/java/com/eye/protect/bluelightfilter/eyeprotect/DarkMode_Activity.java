package com.eye.protect.bluelightfilter.eyeprotect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class DarkMode_Activity extends Activity {

    RadioButton btnOff;
    RadioButton btnOn;
    SharedPreferences.Editor editor;


    public Button refresh;
    SharedPreferences sharedPreferences;
    private CheckBox startVideoAdsMuted;
    RadioGroup toggle;

    public TextView videoStatus;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_dark);

        this.toggle = (RadioGroup) findViewById(R.id.toggle);
        this.btnOff = (RadioButton) findViewById(R.id.off);
        this.btnOn = (RadioButton) findViewById(R.id.on);
        this.sharedPreferences = getSharedPreferences("lightDimmer", 0);
        this.editor = this.sharedPreferences.edit();
        if (this.sharedPreferences.getBoolean("NameOfThingToSaveD", false)) {
            this.btnOn.setChecked(true);
        }
        this.toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.off :
                        DarkMode_Activity darkMode_Activity = DarkMode_Activity.this;
                        darkMode_Activity.stopService(new Intent(darkMode_Activity.getApplicationContext(), DarkService.class));
                        DarkMode_Activity.this.editor.putBoolean("NameOfThingToSaveD", false).apply();
                        return;
                    case R.id.on :
                        DarkMode_Activity.this.stopAllServices();
                        DarkMode_Activity.this.startService(new Intent(DarkMode_Activity.this.getApplicationContext(), DarkService.class));
                        DarkMode_Activity.this.editor.putBoolean("NameOfThingToSaveD", true).apply();
                        return;
                    default:
                        return;
                }
            }
        });
    }


    public void stopAllServices() {
        this.editor.putBoolean("NameOfThingToSaveR", false).apply();
        this.editor.putBoolean("NameOfThingToSaveStress", false).apply();
        this.editor.putBoolean("NameOfThingToSaveN", false).apply();
        this.editor.putBoolean("NameOfThingToSaveSleep", false).apply();
        stopService(new Intent(getApplicationContext(), IntensityService.class));
        stopService(new Intent(getApplicationContext(), DarkService.class));
        stopService(new Intent(getApplicationContext(), ReadingService.class));
        stopService(new Intent(getApplicationContext(), SleepService.class));
        stopService(new Intent(getApplicationContext(), StressfreeService.class));
    }

    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
    }





}
