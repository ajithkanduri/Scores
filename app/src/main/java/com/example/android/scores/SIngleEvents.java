package com.example.android.scores;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;

public class SIngleEvents extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final String parent = getIntent().getStringExtra("id");
        setContentView(R.layout.activity_single_events);
        final EditText pos1 = findViewById(R.id.pos1);
        final EditText pos2 = findViewById(R.id.pos2);
        final EditText pos3 = findViewById(R.id.pos3);
        final EditText extra = findViewById(R.id.extra);
        Button button = findViewById(R.id.save);

        final HashMap<String,String> hashMap = new HashMap<>();
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(pos1.getText()==null)
                {
                    Toast.makeText(SIngleEvents.this,"Enter position 1",Toast.LENGTH_SHORT);
                }
                else {

                    hashMap.put("pos1",pos1.getText().toString());
                    if(pos2.getText()!= null)
                    {
                        hashMap.put("pos2",pos2.getText().toString());
                    }
                    if(pos3.getText()!=null)
                    {
                        hashMap.put("pos3",pos3.getText().toString());
                    }
                    if(extra.getText()!=null)
                    {
                        hashMap.put("msg",extra.getText().toString());
                    }
                }
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Scores").child("Results").child(parent).child(Calendar.getInstance().getTimeInMillis()+"");
                reference.setValue(hashMap);
                Intent startMain = new Intent(SIngleEvents.this,EventsActivity1.class);
                startMain.addCategory(Intent.CATEGORY_HOME);
                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startMain);
            }
        });
    }
}
