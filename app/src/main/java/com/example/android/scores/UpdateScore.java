package com.example.android.scores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        final String ts = getIntent().getStringExtra("ts");
        final DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList<String> list = databaseHelper.getScore(parent,matchnumber);
        TextView m = findViewById(R.id.textView);
        m.setText(matchnumber);
        final TextView ta = findViewById(R.id.teamA);
        ta.setText(teamA);
        final TextView tb = findViewById(R.id.teamB);
        tb.setText(teamB);
        final ElegantNumberButton tascore = findViewById(R.id.tascore);

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
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Live Matches").child(parent).child(ts);
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        ta.setText(dataSnapshot.child("scoreA").getValue().toString());
                        tb.setText(dataSnapshot.child("scoreB").getValue().toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseHelper.upDateScore(parent,matchnumber,String.valueOf(teamAScore),String.valueOf(teamBScore));
                ProgressDialog dialog = MyProgressDialog();
                dialog.show();
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Live Matches").child(parent).child(ts);
                HashMap<String,String> hashMap = new HashMap<>();
                hashMap.put("scoreA",String.valueOf(teamAScore));
                hashMap.put("scoreB",String.valueOf(teamBScore));
                databaseReference.updateChildren((Map) (hashMap));dialog.dismiss();
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        databaseReference.child("scoreA").setValue(String.valueOf(teamAScore));
//                        databaseReference.child("scoreB").setValue(String.valueOf(teamBScore));
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });

            }
        });
        Button button1 = findViewById(R.id.results);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateScore.this,resultsActivity.class);
                intent.putExtra("teamA",teamA);
                intent.putExtra("teamB",teamB);
                intent.putExtra("scoreA",String.valueOf(teamAScore));
                intent.putExtra("scoreB",String.valueOf(teamBScore));
                intent.putExtra("parent",parent);
                intent.putExtra("ts",ts);
                intent.putExtra("category","type1");
                intent.putExtra("title",matchnumber);
                startActivity(intent);
            }
        });
    }
    public ProgressDialog MyProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;
    }
}
