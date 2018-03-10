package com.example.mohan.ooad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InstalledApps extends AppCompatActivity {

    List<AppList> installedApps;
    ListView userInstalledApps;
    AppAdapter installedAppAdapter;
    ArrayList<String> selectedItems;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_installed_apps);

        Toast.makeText(this, getIntent().getStringExtra("user"), Toast.LENGTH_SHORT).show();

        Button lock = findViewById(R.id.lock);
        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lock();
            }
        });

        prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("open", false);
        editor.apply();

        userInstalledApps = findViewById(R.id.installed_app_list);
        userInstalledApps.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

        installedApps = getInstalledApps();
        installedAppAdapter = new AppAdapter(this, installedApps);

        userInstalledApps.setAdapter(installedAppAdapter);
    }

    private List<AppList> getInstalledApps() {
        List<AppList> res = new ArrayList<>();
        List<PackageInfo> packs = getPackageManager().getInstalledPackages(0);
        for (int i = 0; i < packs.size(); i++) {
            PackageInfo p = packs.get(i);
            if (!isSystemPackage(p)) {
                String appName = p.applicationInfo.loadLabel(getPackageManager()).toString();
                Drawable icon = p.applicationInfo.loadIcon(getPackageManager());
                String appPack = p.applicationInfo.packageName;
                res.add(new AppList(appName, icon, appPack));
            }
        }
        return res;
    }

    private boolean isSystemPackage(PackageInfo pkgInfo) {
        return ((pkgInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    private void lock() {

        boolean isSelected[] = installedAppAdapter.getSelectedFlags();
        selectedItems = new ArrayList<>();
        for (int i = 0; i < isSelected.length; i++) {
            if (isSelected[i]) {
                selectedItems.add(installedApps.get(i).getPack());
            }
        }

        if (selectedItems.size() > 0) {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app", selectedItems.get(0));
            editor.putBoolean("first", false);
            editor.apply();
            startService(new Intent(InstalledApps.this, MyService.class));

            Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
            startHomescreen.addCategory(Intent.CATEGORY_HOME);
            startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
            startActivity(startHomescreen);
            finish();

//            JSONObject lockedApps = new JSONObject();
//            for (int o = 0; o < selectedItems.size(); o++) {
//                try {
//                    lockedApps.put("app" + o, selectedItems.get(o));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            addTask(lockedApps);

        } else {
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("app", "null");
            editor.apply();
            Toast.makeText(this, "No app selected!!", Toast.LENGTH_LONG).show();
            Toast.makeText(this, "Previous apps removed!!", Toast.LENGTH_LONG).show();
        }
    }

//    private void addTask(JSONObject lockedApps) {
//
//        SharedPreferences.Editor editor = prefs.edit();
//
//        editor.putString("key", lockedApps.toString());
//        editor.apply();
//
//        //startService(new Intent(InstalledApps.this, MyService.class));
//
//    }
}
