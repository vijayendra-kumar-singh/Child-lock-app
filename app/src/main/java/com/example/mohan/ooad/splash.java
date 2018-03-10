package com.example.mohan.ooad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

public class splash extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;

        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
        if (Objects.equals(prefs.getString("user", "null"), "child")) {
            if (prefs.getBoolean("first", true)) {
                intent = new Intent(splash.this, MainActivity.class);
            } else {
                intent = new Intent(this, FullscreenActivity.class);
            }
        } else if (Objects.equals(prefs.getString("user", "null"), "parent")) {
            intent = new Intent(this, Main2Activity.class);
        } else {
            intent = new Intent(this, MainActivity.class);
        }
        startActivity(intent);
        finish();
    }
}