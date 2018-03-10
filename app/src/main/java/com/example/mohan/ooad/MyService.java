package com.example.mohan.ooad;

import android.app.ActivityManager;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.Timer;
import java.util.TimerTask;
import java.util.TreeMap;

public class MyService extends Service {

    public static final long NOTIFY_INTERVAL = 100;
    private Handler mHandler = new Handler();
    private Timer mTimer = null;
    SharedPreferences prefs;
    private boolean isOpen;
    private String app;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (mTimer != null) {
            mTimer.cancel();
        } else {
            mTimer = new Timer();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
        app = prefs.getString("app", "null");
        if (Objects.equals(app, "null")) {
            stopSelf();
            return START_NOT_STICKY;
        } else {
            mTimer.scheduleAtFixedRate(new checkCurrentApp(), 0, NOTIFY_INTERVAL);
            return START_STICKY;
        }
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        Intent intent = new Intent(getApplicationContext(), MyService.class);
        PendingIntent pendingIntent = PendingIntent.getService(this, 1, intent, PendingIntent.FLAG_ONE_SHOT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1000, pendingIntent);
        super.onTaskRemoved(rootIntent);
    }

    class checkCurrentApp extends TimerTask {
        @Override
        public void run() {
            // run on another thread
            mHandler.post(new Runnable() {

                @Override
                public void run() {

                    //Toast.makeText(MyService.this, presentApp(), Toast.LENGTH_SHORT).show();
                    String appPackage = presentApp();
//                    Log.d("=====", appPackage);
                    isOpen = prefs.getBoolean("open", false);
                    if (appPackage.equals(app) && !isOpen) {
                        Intent lockIntent = new Intent(getApplicationContext(), quiz.class);
                        lockIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        getApplicationContext().startActivity(lockIntent);
                    }
                }

            });
        }

        private String presentApp() {

            String currentApp = null;

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                UsageStatsManager usm = (UsageStatsManager) getSystemService("usagestats");
                long time = System.currentTimeMillis();
                List<UsageStats> appList = usm.queryUsageStats(UsageStatsManager.INTERVAL_DAILY,
                        time - 1000 * 1000, time);
                if (appList != null && appList.size() > 0) {
                    SortedMap<Long, UsageStats> mySortedMap = new TreeMap<Long, UsageStats>();
                    for (UsageStats usageStats : appList) {
                        mySortedMap.put(usageStats.getLastTimeUsed(),
                                usageStats);
                    }
                    if (mySortedMap != null && !mySortedMap.isEmpty()) {
                        currentApp = mySortedMap.get(
                                mySortedMap.lastKey()).getPackageName();
                    }
                }
            } else {
                ActivityManager am = (ActivityManager) getBaseContext().getSystemService(ACTIVITY_SERVICE);
                currentApp = am.getRunningTasks(1).get(0).topActivity.getPackageName();

            }

            return currentApp;
        }
    }
}