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

public class LiveMatches extends AppCompatActivity {
    String parent,category;
    ArrayList<String> matcheNo = new ArrayList<>();
    ArrayList<String> teamA = new ArrayList<>();
    ArrayList<String> teamB = new ArrayList<>();
    ArrayList<String> time = new ArrayList<>();
    ArrayList<String> ts = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_matches);
        RecyclerView recyclerView = findViewById(R.id.liverecyler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        parent = getIntent().getStringExtra("Parent");
        category = getIntent().getStringExtra("category");
        loadLiveMatches(recyclerView,parent);
    }
    public void loadLiveMatches(final RecyclerView recyclerView, final String parent)
    {
        final ProgressDialog progressDialog = MyProgressDialog();
        progressDialog.show();
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Live Matches").child(parent);
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
                    matcheNo.add(dataSnapshot1.child("title").getValue().toString());
                    teamA.add(dataSnapshot1.child("teamA").getValue().toString());
                    teamB.add(dataSnapshot1.child("teamB").getValue().toString());
                    time.add(dataSnapshot1.child("time").getValue().toString());
                    ts.add(dataSnapshot1.getKey());
                }
                LiveMatchesAdapter liveMatchesAdapter = new LiveMatchesAdapter(LiveMatches.this,matcheNo,teamA,teamB,time,parent,category,ts);
                recyclerView.setAdapter(liveMatchesAdapter);
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
