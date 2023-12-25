package com.eye.protect.bluelightfilter.eyeprotect;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    static final  boolean Disabled = false;
    public static String name;
    private final int FLOAT_WINDOW_REQUEST_CODE = 1;

    ImageButton app;

    int counter;
    ImageButton darkMode;
    SharedPreferences.Editor editor;
    private boolean floatWindowPermission = false;
    private Intent intent;

    ImageButton more_btn;
    Switch myswitch;

    ImageButton normalMode;
    ImageButton readMode;

    public Button refresh;
    private SeekBar seekBar;
    SharedPreferences sharedPreferences;
    ImageButton sleepMode;
    private CheckBox startVideoAdsMuted;
    ImageButton stress;


    private ActionBarDrawerToggle f71t;

    public TextView videoStatus;


    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView((int) R.layout.activity_main);
        this.myswitch = (Switch) findViewById(R.id.myswitch);


        this.myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton compoundButton, boolean z) {
                if (z) {
                    MainActivity.this.stopAllServices();
                    Toast.makeText(MainActivity.this, "Filters Disabled", 0).show();
                }
            }
        });
        final DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.activity_main);
        ((ImageView) findViewById(R.id.actionBarButton)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                drawerLayout.openDrawer((int) GravityCompat.START);
            }
        });
        this.f71t = new ActionBarDrawerToggle(this, drawerLayout, R.string.Open, R.string.Close);
        this.sharedPreferences = getSharedPreferences("lightDimmer", 0);
        this.editor = this.sharedPreferences.edit();
        checkPermission();

        drawerLayout.addDrawerListener(this.f71t);
        this.f71t.syncState();
        ((NavigationView) findViewById(R.id.nv)).setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.moreapps :
                        MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/developer?id=Bharat+Studios")));
                        return true;
                    case R.id.policy :
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setMessage("Visit this Privacy Policy");
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://bharatstudiospolicy.blogspot.com/2020/06/privacy-policy-body-font-family.html")));
                            }
                        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        });
                        builder.create().show();
                        return true;
                    case R.id.quit :
                        MainActivity.this.OpenExitDialog();
                        return true;
                    case R.id.rate_us :
                        MainActivity.this.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + MainActivity.this.getApplicationContext().getPackageName())));
                        return true;
                    case R.id.share :
                        try {
                            Intent intent = new Intent("android.intent.action.SEND");
                            intent.setType("text/plain");
                            intent.putExtra("android.intent.extra.SUBJECT", "Share app");
                            intent.putExtra("android.intent.extra.TEXT", "https://play.google.com/store/apps/details?id=" + MainActivity.this.getPackageName());
                            MainActivity.this.startActivity(Intent.createChooser(intent, "Recover app"));
                            return true;
                        } catch (Exception e) {
                            e.getMessage();
                            return true;
                        }
                    case R.id.stop :
                        MainActivity.this.stopAllServices();
                        Toast.makeText(MainActivity.this, "Filters Disabled", 0).show();
                        return true;
                    default:
                        return true;
                }
            }
        });
        this.normalMode = (ImageButton) findViewById(R.id.filterOne);
        this.more_btn = (ImageButton) findViewById(R.id.more_btn);
        this.readMode = (ImageButton) findViewById(R.id.filterTwo);
        this.darkMode = (ImageButton) findViewById(R.id.filterThree);
        this.stress = (ImageButton) findViewById(R.id.filterFour);
        this.sleepMode = (ImageButton) findViewById(R.id.filterFive);
        this.more_btn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse("https://play.google.com/store/apps/developer?id=Bharat+Studios"));
                MainActivity.this.startActivity(intent);
            }
        });
        this.normalMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), NormalMode_Activity.class));

            }
        });
        this.darkMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), DarkMode_Activity.class));
            }
        });
        this.stress.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), StressfreeActivity.class));
            }
        });
        this.sleepMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), SleepMode_Activity.class));

            }
        });
        this.readMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                MainActivity.this.startActivity(new Intent(MainActivity.this.getApplicationContext(), ReadMode_Activity.class));

            }
        });
    }


    public void stopAllServices() {
        this.editor.putBoolean("NameOfThingToSaveR", false).apply();
        this.editor.putBoolean("NameOfThingToSaveN", false).apply();
        this.editor.putBoolean("NameOfThingToSaveD", false).apply();
        this.editor.putBoolean("NameOfThingToSaveStress", false).apply();
        this.editor.putBoolean("NameOfThingToSaveSleep", false).apply();
        stopService(new Intent(getApplicationContext(), IntensityService.class));
        stopService(new Intent(getApplicationContext(), DarkService.class));
        stopService(new Intent(getApplicationContext(), NormalService.class));
        stopService(new Intent(getApplicationContext(), ReadingService.class));
        stopService(new Intent(getApplicationContext(), SleepService.class));
        stopService(new Intent(getApplicationContext(), StressfreeService.class));
    }


    /*private boolean isAnyServiceRunning() {
        }*/

    private void checkPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            this.floatWindowPermission = true;
            startIntensityService();
        } else if (Settings.canDrawOverlays(this)) {
            this.floatWindowPermission = true;
            startIntensityService();
        } else {
            Intent intent2 = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent2.setData(Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent2, 1);
        }
    }


    public void onActivityResult(int i, int i2, Intent intent2) {
        super.onActivityResult(i, i2, intent2);
        if (i != 1 || Build.VERSION.SDK_INT < 23) {
            return;
        }
        if (Settings.canDrawOverlays(this)) {
            this.floatWindowPermission = true;
            startIntensityService();
            Toast.makeText(this, "Permission Granted", 0).show();
            return;
        }
        this.floatWindowPermission = false;
        Toast.makeText(this, "Permission Denied", 0).show();
    }

    private void startIntensityService() {
        this.intent = new Intent(this, IntensityService.class);
        startService(this.intent);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (this.f71t.onOptionsItemSelected(menuItem)) {
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onBackPressed() {
        OpenExitDialog();
    }


    public void OpenExitDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(false);
        builder.setTitle((CharSequence) "Exit");
        builder.setMessage((CharSequence) " Do you really want to quit?");
        builder.setPositiveButton((CharSequence) "YES", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent("android.intent.action.MAIN");
                intent.addCategory("android.intent.category.HOME");
                intent.setFlags(268435456);
                MainActivity.this.startActivity(intent);
                MainActivity.this.finish();
            }
        });
        builder.setNegativeButton((CharSequence) "NO", (DialogInterface.OnClickListener) new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.create().show();
    }


}
