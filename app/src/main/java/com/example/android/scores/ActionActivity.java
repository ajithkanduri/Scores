package com.example.android.scores;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

public class ActionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String parent = getIntent().getStringExtra("Parent");
        setContentView(R.layout.activity_action);
        RelativeLayout addMatch = findViewById(R.id.addmatch);
        RelativeLayout updateScore = findViewById(R.id.updatescore);
        RelativeLayout upcoming = findViewById(R.id.upcoming);
        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionActivity.this,AddMatch.class);
                intent.putExtra("Parent",parent);
                startActivity(intent);
            }
        });
        addMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionActivity.this,UpcomingMatches.class);
                intent.putExtra("Parent",parent);
                startActivity(intent);
            }
        });
    }
}
