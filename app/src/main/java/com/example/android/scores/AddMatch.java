package com.example.android.scores;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class AddMatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        final String parent = getIntent().getStringExtra("Parent");
        final EditText matchNumber = findViewById(R.id.match_number);
        final EditText teamA = findViewById(R.id.teamA);
        final EditText teamB = findViewById(R.id.teamB);
        final EditText time = findViewById(R.id.time);
        Button button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(matchNumber.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMatch.this,"Enter Match Number",Toast.LENGTH_SHORT).show();
                }
                else if(teamA.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMatch.this,"Enter Team A",Toast.LENGTH_SHORT);

                }
                else if(teamB.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMatch.this,"Enter Team B",Toast.LENGTH_SHORT).show();
                }
                else if(time.getText().toString().isEmpty())
                {
                    Toast.makeText(AddMatch.this,"Enter Start Time",Toast.LENGTH_SHORT).show();
                }
                else {
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Scores").child("Upcoming Matches").child(parent).child(matchNumber.getText().toString());
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("Team A",teamA.getText().toString());
                    hashMap.put("Team B",teamB.getText().toString());
                    hashMap.put("Time",time.getText().toString());
                    database.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(AddMatch.this,"Match Has Been Added Successfully",Toast.LENGTH_SHORT);
                        }
                    });
                }
            }
        });
    }
}
