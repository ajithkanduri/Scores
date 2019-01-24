package com.example.android.scores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class cricketActivity extends AppCompatActivity {
    EditText scoreA;
    EditText scoreB;
    EditText wicketA;
    EditText wicketB;
    EditText overA;
    EditText overB;
    Button update;
    String status = "1";
    String matchnumber;
    String steamA;
    String steamB;
    String time;
    String parent;
    EditText description;
    String ts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cricket);
        matchnumber = getIntent().getStringExtra("MatchNumber");
        steamA = getIntent().getStringExtra("Team A");
        steamB = getIntent().getStringExtra("Team B");
        time = getIntent().getStringExtra("Time");
        parent = getIntent().getStringExtra("Parent");
        ts = getIntent().getStringExtra("ts");
        final Spinner spinner = findViewById(R.id.spinner);

        TextView mn = findViewById(R.id.matchnumber);
        mn.setText(matchnumber);
        TextView info = findViewById(R.id.info);
        info.setText("A = "+steamA+" B = "+steamB);
        description = findViewById(R.id.dess);
        scoreA = findViewById(R.id.scoreA);
        scoreB = findViewById(R.id.scoreB);
        wicketA = findViewById(R.id.wicketsA);
        wicketB = findViewById(R.id.wicketsB);
        overA = findViewById(R.id.wicketsA);
        overB = findViewById(R.id.oversB);
        final CheckBox checkBox = findViewById(R.id.checkBox);


        update = findViewById(R.id.update);
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Live Matches").child(parent).child(ts);

        Button finished = findViewById(R.id.finish);
        finished.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(cricketActivity.this,resultsActivity.class);
                intent.putExtra("teamA",steamA);
                intent.putExtra("teamB",steamB);
                if(spinner.getSelectedItem().toString().equals("A")) {
                    intent.putExtra("scoreA", scoreA.getText().toString() + "/" + wicketA.getText().toString() + " (" + overA.getText().toString() + ")");
                    intent.putExtra("scoreB", scoreB.getText().toString() + "/" + wicketB.getText().toString() + " (" + overB.getText().toString() + ")");
                }
                else
                {
                    intent.putExtra("scoreB", scoreA.getText().toString() + "/" + wicketA.getText().toString() + " (" + overA.getText().toString() + ")");
                    intent.putExtra("scoreA", scoreB.getText().toString() + "/" + wicketB.getText().toString() + " (" + overB.getText().toString() + ")");
                }
                intent.putExtra("parent",parent);
                intent.putExtra("ts",ts);
                intent.putExtra("title",matchnumber);
                intent.putExtra("category","cricket");
                intent.putExtra("t1",spinner.getSelectedItem().toString());
                startActivity(intent);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HashMap<String,String> hashMap = new HashMap<>();
                if(spinner.getSelectedItem().toString().equals("A")) {
                    hashMap.put("scoreA", scoreA.getText().toString() + "/" + wicketA.getText().toString() + " (" + overA.getText().toString() + ")");
                    hashMap.put("scoreB", scoreB.getText().toString() + "/" + wicketB.getText().toString() + " (" + overB.getText().toString() + ")");
                }
                else {

                    hashMap.put("scoreB", scoreA.getText().toString() + "/" + wicketA.getText().toString() + " (" + overA.getText().toString() + ")");
                    hashMap.put("scoreA", scoreB.getText().toString() + "/" + wicketB.getText().toString() + " (" + overB.getText().toString() + ")");
                }
                if(checkBox.isChecked())
                {
                    status = "2";
                }
                hashMap.put("inning",status);
                hashMap.put("t1",spinner.getSelectedItem().toString());
                if(description.getText()!=null) {
                    hashMap.put("desc", description.getText().toString());
                }
                else
                {hashMap.put("desc","No description");}

                databaseReference.updateChildren((Map)hashMap);
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
////                        databaseReference.child("scoreA").setValue(String.valueOf(teamAScore));
////                        databaseReference.child("scoreB").setValue(String.valueOf(teamBScore));
//                        if(teamA.getText().toString().equals("A"))
//                        {
//                            databaseReference.child("scoreA").setValue(scoreA.getText().toString() + "/" + wicketA.getText().toString() + " (" + overA.getText().toString() + ")");
//                            databaseReference.child("scoreB").setValue(scoreB.getText().toString() + "/" + wicketB.getText().toString() + " (" + overB.getText().toString() + ")");
//                            databaseReference.child("inning").setValue(status);
//                            databaseReference.child("t1").setValue(teamA.getText());
//                            databaseReference.child("desc").setValue(description.getText());
//                        }
//                        else
//                        {
//                            databaseReference.child("scoreA").setValue(scoreA.getText().toString() + "/" + wicketA.getText().toString() + " (" + overA.getText().toString() + ")");
//                            databaseReference.child("scoreB").setValue(scoreB.getText().toString() + "/" + wicketB.getText().toString() + " (" + overB.getText().toString() + ")");
//                            databaseReference.child("inning").setValue(status);
//                            databaseReference.child("t1").setValue(teamA.getText());
//                            databaseReference.child("desc").setValue(description.getText());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//
//                    }
//                });
            }
        });

    }
}
