package com.example.android.scores;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UpcomingMatches extends AppCompatActivity {
    String parent;
    ArrayList<String> matcheNo = new ArrayList<>();
    ArrayList<String> teamA = new ArrayList<>();
    ArrayList<String> teamB = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> ts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upcoming_matches);
        RecyclerView recyclerView = findViewById(R.id.upcomingrecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        parent = getIntent().getStringExtra("Parent");
        loadUpcomingMatches(recyclerView,parent);
    }
    public void loadUpcomingMatches(final RecyclerView recyclerView, final String parent)
    {
        final ProgressDialog progressDialog = MyProgressDialog();
        progressDialog.show();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Upcoming Matches").child(parent);
        databaseReference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                matcheNo.clear();
                teamA.clear();
                teamB.clear();
                time.clear();
                ts.clear();
                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
                {
                    ts.add(dataSnapshot1.getKey());
                    matcheNo.add(dataSnapshot1.child("title").getValue().toString());
                    teamA.add(dataSnapshot1.child("teamA").getValue().toString());
                    teamB.add(dataSnapshot1.child("teamB").getValue().toString());
                    time.add(dataSnapshot1.child("time").getValue().toString());
                }
                UpComingMatchesAdapter upComingMatchesAdapter = new UpComingMatchesAdapter(UpcomingMatches.this,matcheNo,teamA,teamB,time,parent,ts);
                recyclerView.setAdapter(upComingMatchesAdapter);
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });

    }
    private ProgressDialog MyProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("loading ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;
    }
}
