package com.example.mohan.ooad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.util.Objects;

import cz.msebera.android.httpclient.Header;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        Button open = findViewById(R.id.open);
        Button lock = findViewById(R.id.lock);

        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
        final String parentTOken = prefs.getString("parenttoken", "null");
        if (Objects.equals(parentTOken, "null")) {
            getToken();
        }

        open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(parentTOken, "open");
            }
        });

        lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request(parentTOken, "lock");
            }
        });

    }

    private void request(String parentTOken, String message) {
        String url = "http://random-apis.herokuapp.com/send.php";
        RequestParams params = new RequestParams();
        params.put("token", parentTOken);
        params.put("message", message);

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                Toast.makeText(quiz.this, "rereererererer", Toast.LENGTH_SHORT).show();
//                Toast.makeText(quiz.this, response.toString(), Toast.LENGTH_LONG).show();
                Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                startHomescreen.addCategory(Intent.CATEGORY_HOME);
                startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(startHomescreen);
                finish();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable t, JSONObject response) {
                Toast.makeText(Main2Activity.this, "Network error!!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getToken() {
        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        String key = prefs.getString("key", "null");
        DatabaseReference myDatabaseReference = FirebaseDatabase.getInstance().getReference(key).child("child");
        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String parentToken = dataSnapshot.getValue(String.class);
                editor.putString("parenttoken", parentToken);
                editor.apply();
                Toast.makeText(Main2Activity.this, "Token saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }
}