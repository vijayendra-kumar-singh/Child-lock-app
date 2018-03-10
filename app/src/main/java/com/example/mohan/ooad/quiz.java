package com.example.mohan.ooad;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class quiz extends AppCompatActivity {

    private QuestionLibrary mQuestionLibrary = new QuestionLibrary();

    private TextView mScoreView;
    private TextView mQuestionView;
    private Button mButtonChoice1;
    private Button mButtonChoice2;
    private Button mButtonChoice3;
    private Button mButtonSkip;
    private Button mButtonQuit;

    private String mAnswer;
    private int mScore = 0;
    private int mQuestionNumber = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mScoreView = findViewById(R.id.score);
        mQuestionView = findViewById(R.id.question);
        mButtonChoice1 = findViewById(R.id.choice1);
        mButtonChoice2 = findViewById(R.id.choice2);
        mButtonChoice3 = findViewById(R.id.choice3);
        mButtonSkip = findViewById(R.id.skip);
        mButtonQuit = findViewById(R.id.quit);

        updateQuestion();

        mButtonChoice1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonChoice1.getText() == mAnswer) {
                    mScore = mScore + 1;
                    updateScore();
                }

                if (mQuestionNumber != 5) {
                    updateQuestion();
                } else {
                    if (mScore >= 3) {
                        Toast.makeText(quiz.this, "unlocked", Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("open", true);
                        editor.apply();
                        finish();
                    } else {
                        Toast.makeText(quiz.this, "You need to score at least 3 to unlock", Toast.LENGTH_LONG).show();
                        Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                        startHomescreen.addCategory(Intent.CATEGORY_HOME);
                        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(startHomescreen);
                        finish();
                    }
                }
            }
        });

        mButtonChoice2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonChoice2.getText() == mAnswer) {
                    mScore = mScore + 1;
                    updateScore();
                }

                if (mQuestionNumber != 5) {
                    updateQuestion();
                } else {
                    if (mScore >= 3) {
                        Toast.makeText(quiz.this, "unlocked", Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("open", true);
                        editor.apply();
                        finish();
                    } else {
                        Toast.makeText(quiz.this, "You need to score at least 3 to unlock", Toast.LENGTH_LONG).show();
                        Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                        startHomescreen.addCategory(Intent.CATEGORY_HOME);
                        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(startHomescreen);
                        finish();
                    }
                }
            }
        });

        mButtonChoice3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mButtonChoice3.getText() == mAnswer) {
                    mScore = mScore + 1;
                    updateScore();
                }

                if (mQuestionNumber != 5) {
                    updateQuestion();
                } else {
                    if (mScore >= 3) {
//                        Toast.makeText(quiz.this, "unlocked", Toast.LENGTH_SHORT).show();
                        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("open", true);
                        editor.apply();
                        finish();
                    } else {
                        Toast.makeText(quiz.this, "You need to score at least 3 to unlock", Toast.LENGTH_LONG).show();
                        Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                        startHomescreen.addCategory(Intent.CATEGORY_HOME);
                        startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                        startActivity(startHomescreen);
                        finish();
                    }
                }
            }
        });

        mButtonSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
                String parentTOken = prefs.getString("parenttoken", "null");
                if (!Objects.equals(parentTOken, "null")) {
                    request(parentTOken);
                } else {
                    getToken();
                }

            }
        });

        mButtonQuit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startHomescreen = new Intent(Intent.ACTION_MAIN);
                startHomescreen.addCategory(Intent.CATEGORY_HOME);
                startHomescreen.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                startActivity(startHomescreen);
                finish();
            }
        });
    }

    private void request(String parentTOken) {
        String url = "http://random-apis.herokuapp.com/send.php";
        RequestParams params = new RequestParams();
        params.put("token", parentTOken);
        params.put("message", "request");

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
                Toast.makeText(quiz.this, "Network error!!", Toast.LENGTH_LONG).show();
            }
        });
    }


    private void getToken() {
        SharedPreferences prefs = getSharedPreferences("lockedApps", Context.MODE_PRIVATE);
        final SharedPreferences.Editor editor = prefs.edit();
        String key = prefs.getString("key", "null");
        DatabaseReference myDatabaseReference = FirebaseDatabase.getInstance().getReference(key).child("parent");
        myDatabaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String parentToken = dataSnapshot.getValue(String.class);
                editor.putString("parenttoken", parentToken);
                editor.apply();
                Toast.makeText(quiz.this, "Token saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void updateQuestion() {
        mQuestionView.setText(mQuestionLibrary.getQuestion(mQuestionNumber));
        mButtonChoice1.setText(mQuestionLibrary.getChoice1(mQuestionNumber));
        mButtonChoice2.setText(mQuestionLibrary.getChoice2(mQuestionNumber));
        mButtonChoice3.setText(mQuestionLibrary.getChoice3(mQuestionNumber));
        mAnswer = mQuestionLibrary.getCorrectAnswer(mQuestionNumber);
        mQuestionNumber++;
    }


    private void updateScore() {
        mScoreView.setText("" + mScore);
    }
}
