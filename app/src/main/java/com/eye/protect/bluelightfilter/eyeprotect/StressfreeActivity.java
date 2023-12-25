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

public class StressfreeActivity extends Activity {

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
        setContentView(R.layout.activity_stressfree);

        this.toggle = (RadioGroup) findViewById(R.id.toggle);
        this.btnOff = (RadioButton) findViewById(R.id.off);
        this.btnOn = (RadioButton) findViewById(R.id.on);
        this.sharedPreferences = getSharedPreferences("lightDimmer", 0);
        this.editor = this.sharedPreferences.edit();
        if (this.sharedPreferences.getBoolean("NameOfThingToSaveStress", false)) {
            this.btnOn.setChecked(true);
        }
        this.toggle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.off :
                        StressfreeActivity stressfreeActivity = StressfreeActivity.this;
                        stressfreeActivity.stopService(new Intent(stressfreeActivity.getApplicationContext(), StressfreeService.class));
                        StressfreeActivity.this.editor.putBoolean("NameOfThingToSaveStress", false).apply();
                        return;
                    case R.id.on :
                        StressfreeActivity.this.stopAllServices();
                        StressfreeActivity.this.startService(new Intent(StressfreeActivity.this.getApplicationContext(), StressfreeService.class));
                        StressfreeActivity.this.editor.putBoolean("NameOfThingToSaveStress", true).apply();
                        return;
                    default:
                        return;
                }
            }
        });
    }


    public void stopAllServices() {
        this.editor.putBoolean("NameOfThingToSaveR", false).apply();
        this.editor.putBoolean("NameOfThingToSaveN", false).apply();
        this.editor.putBoolean("NameOfThingToSaveD", false).apply();
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
