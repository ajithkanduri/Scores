package com.example.android.scores;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class resultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        TextView textView = findViewById(R.id.text);
        final Spinner spinner = findViewById(R.id.spinner);
        final String teamA = getIntent().getStringExtra("teamA");
        final String teamB = getIntent().getStringExtra("teamB");
        final String scoreA = getIntent().getStringExtra("scoreA");
        final String scoreB = getIntent().getStringExtra("scoreB");
        final String title = getIntent().getStringExtra("title");
        final String t1 = getIntent().getStringExtra("t1");
        final String category = getIntent().getStringExtra("category");
        final EditText text = findViewById(R.id.msg);
        String parent = getIntent().getStringExtra("parent");
        String ts = getIntent().getStringExtra("ts");
        textView.setText("Select 0 for Tie, 1 if "+teamA+"Wins,2 if "+teamB+" Wins");
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Results").child(parent).child(ts);
        final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scores").child("Live Matches").child(parent).child(ts);
        Button button = findViewById(R.id.results);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final ProgressDialog progressDialog = MyProgressDialog();
                progressDialog.show();
                if(category.equals("type2"))
                {
                    final HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("teamA", teamA);
                    hashMap.put("teamB", teamB);
                    hashMap.put("status", spinner.getSelectedItem().toString());
                    hashMap.put("title", title);
                    if (text.getText() != null) {
                        hashMap.put("message", text.getText().toString());
                    }
                    reference.child("matches").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            hashMap.put("matches",dataSnapshot.getValue());
                            databaseReference.setValue(hashMap);
                           // databaseReference.child("matches").child(getIntent().getStringExtra("type")).setValue(map);
                            reference.setValue(null);

                            progressDialog.dismiss();
                            Intent startMain = new Intent(resultsActivity.this,EventsActivity1.class);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(startMain);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                }
                else {
                    HashMap<String, String> hashMap = new HashMap<>();
                    hashMap.put("teamA", teamA);
                    hashMap.put("teamB", teamB);
                    hashMap.put("scoreA", scoreA);
                    hashMap.put("scoreB", scoreB);
                    hashMap.put("status", spinner.getSelectedItem().toString());
                    hashMap.put("title", title);
                    if (text.getText() != null) {
                        hashMap.put("message", text.getText().toString());
                    } else {
                        hashMap.put("message", null);
                    }
                    if (t1 != null) {
                        hashMap.put("t1", t1);
                    }
                    databaseReference.setValue(hashMap);
                    reference.setValue(null);
                    progressDialog.dismiss();
                    finish();
                }
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
