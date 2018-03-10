package com.example.mohan.ooad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Spinner spinner = findViewById(R.id.spinner);

        final EditText e = findViewById(R.id.num);
        Button b = findViewById(R.id.save);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (e.getText().toString().trim().length() != 10) {
                    Toast.makeText(MainActivity.this, "Invalid Number", Toast.LENGTH_SHORT).show();
                } else {
                    SaveData(e.getText().toString().trim(), spinner.getSelectedItem().toString());
                    SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = prefs.edit();
                    editor.putString("key", e.getText().toString().trim());
                    editor.putString("user", spinner.getSelectedItem().toString());
                    editor.apply();
                    if (Objects.equals(spinner.getSelectedItem().toString(), "child")) {
                        Intent ii=new Intent(MainActivity.this, InstalledApps.class);
                        ii.putExtra("user", e.getText().toString().trim());
                        startActivity(ii);
                        finish();
                    } else {
                        startActivity(new Intent(MainActivity.this, Main2Activity.class));
                        finish();
                    }
                }
            }
        });
    }

    private void SaveData(String key, String user) {
        String token = new tokenGenerator().getToken();
        Toast.makeText(this, token, Toast.LENGTH_SHORT).show();
        DatabaseReference myDatabaseReference = FirebaseDatabase.getInstance().getReference().child(key);
        myDatabaseReference.child(user).setValue(token);
    }
}
