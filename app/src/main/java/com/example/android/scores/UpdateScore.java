package com.example.android.scores;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateScore extends AppCompatActivity {
    String matchnumber;
    String teamA;
    String teamB;
    String time;
    String parent;
    int teamAScore;
    int teamBScore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_score);
        matchnumber = getIntent().getStringExtra("MatchNumber");
        teamA = getIntent().getStringExtra("Team A");
        teamB = getIntent().getStringExtra("Team B");
        time = getIntent().getStringExtra("Time");
        parent = getIntent().getStringExtra("Parent");
        TextView m = findViewById(R.id.textView);
        m.setText(matchnumber);
        TextView ta = findViewById(R.id.teamA);
        ta.setText(teamA);
        TextView tb = findViewById(R.id.teamB);
        tb.setText(teamB);
        ElegantNumberButton tascore = findViewById(R.id.tascore);
        final ElegantNumberButton tbscore = findViewById(R.id.tbscore);
        tascore.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                    teamAScore = newValue;
            }
        });
        tbscore.setOnValueChangeListener(new ElegantNumberButton.OnValueChangeListener() {
            @Override
            public void onValueChange(ElegantNumberButton view, int oldValue, int newValue) {
                teamBScore = newValue;
            }
        });
        Button button = findViewById(R.id.updatescore);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("LiveMatches").child(parent).child(matchnumber);
                databaseReference.child("TAScore").setValue(String.valueOf(teamAScore));
                databaseReference.child("TBScore").setValue(String.valueOf(teamBScore));
            }
        });
    }
}
