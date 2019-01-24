package com.example.android.scores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class BadmintionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.common);
        final HashMap<String,Object> hashMap = new HashMap<>();
        final String matchnumber = getIntent().getStringExtra("MatchNumber");
        final String steamA = getIntent().getStringExtra("Team A");
        final String steamB = getIntent().getStringExtra("Team B");
        String time = getIntent().getStringExtra("Time");
        final String parent = getIntent().getStringExtra("Parent");
        final String ts = getIntent().getStringExtra("ts");
        TextView textView = findViewById(R.id.match);
        final Spinner spinner = findViewById(R.id.singles1);
        TextView teamA = findViewById(R.id.teamA);
        TextView teamB = findViewById(R.id.teamB);
        teamA.setText(steamA);
        teamB.setText(steamB);
        textView.setText(steamA+" v/s "+steamB);
        final ElegantNumberButton s1a = findViewById(R.id.scoreA);
        final ElegantNumberButton s1b = findViewById(R.id.scoreB);
        final Spinner set = findViewById(R.id.set);
        Button button = findViewById(R.id.update);
        Button results = findViewById(R.id.results);
        Button delete = findViewById(R.id.delete);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Live Matches").child(parent).child(ts);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                hashMap.put("a",s1a.getNumber());
                hashMap.put("b",s1b.getNumber());


                databaseReference.child("matches").child(spinner.getSelectedItem().toString()).child(set.getSelectedItem().toString()).setValue(hashMap);
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.child("matches").child(spinner.getSelectedItem().toString()).child(set.getSelectedItem().toString()).setValue(null);
            }
        });
        results.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(BadmintionActivity.this,resultsActivity.class);
                intent.putExtra("category","type2");
                intent.putExtra("teamA",steamA);
                intent.putExtra("teamB",steamB);
                intent.putExtra("parent",parent);
                intent.putExtra("title",matchnumber);
                intent.putExtra("ts",ts);
                intent.putExtra("hashMap",hashMap);
                intent.putExtra("type",spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });

    }
}
