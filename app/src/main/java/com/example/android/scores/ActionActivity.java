package com.example.android.scores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;

import java.util.ArrayList;

public class ActionActivity extends AppCompatActivity {
    private ArrayList<String> type1 = new ArrayList<>();
    private ArrayList<String> type2 = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String parent = getIntent().getStringExtra("id");
        final String category = getIntent().getStringExtra("category");
        final String name = getIntent().getStringExtra("name");
        type1.add("Basketball Boys");
        type1.add("Basketball Girls");
        type1.add("Football Boys");
        type1.add("Football Girls");
        type1.add("Kabaddi");
        type1.add("Hockey");
        type2.add("Badminton Boys");
        type2.add("Badmintion Girls");
        type2.add("Badmintion Mixed");
        setContentView(R.layout.activity_action);
        RelativeLayout addMatch = findViewById(R.id.addmatch);
        RelativeLayout updateScore = findViewById(R.id.updatescore);
        RelativeLayout upcoming = findViewById(R.id.upcoming);

        upcoming.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionActivity.this,UpcomingMatches.class);
                intent.putExtra("Parent",parent);
                startActivity(intent);
            }
        });

        addMatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActionActivity.this,AddMatch.class);
                intent.putExtra("Parent",parent);
                startActivity(intent);
            }
        });

        updateScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                        Intent intent = new Intent(ActionActivity.this, LiveMatches.class);
                        intent.putExtra("Parent", parent);
                        intent.putExtra("category",category);
                        startActivity(intent);

            }
        });
    }
}
