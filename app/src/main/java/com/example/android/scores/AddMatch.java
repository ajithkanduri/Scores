package com.example.android.scores;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class AddMatch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_match);
        final String parent = getIntent().getStringExtra("Parent");
        final EditText matchNumber = findViewById(R.id.match_number);
        final EditText teamA = findViewById(R.id.teamA);
        final EditText teamB = findViewById(R.id.teamB);
        final TextView time = findViewById(R.id.time);
        final TextView datetext = findViewById(R.id.date);
        final EditText desc = findViewById(R.id.desc);
        final Calendar myCalendar = Calendar.getInstance();
        datetext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        String myFormat = "yyyy-MM-dd"; //In which you need put here
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                        datetext.setText(sdf.format(myCalendar.getTime()));
                    }

                };
                new DatePickerDialog(AddMatch.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            final Calendar calendar = Calendar.getInstance();
            @Override
            public void onClick(View view) {

                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(AddMatch.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        time.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }


        });
        Button button= findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findViewById(R.id.overlay).setVisibility(View.VISIBLE);
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
                    String ts = String.valueOf(TimeUnit.MILLISECONDS.toSeconds(System.currentTimeMillis()));
                    DatabaseReference database = FirebaseDatabase.getInstance().getReference("Scores").child("Upcoming Matches").child(parent).child(ts);
                    HashMap<String,String> hashMap = new HashMap<>();
                    hashMap.put("title",matchNumber.getText().toString());
                    hashMap.put("teamA",teamA.getText().toString());
                    hashMap.put("teamB",teamB.getText().toString());
                    hashMap.put("time",time.getText().toString());
                    hashMap.put("date",datetext.getText().toString());
                    if(desc.getText()!=null) {
                        hashMap.put("description", desc.getText().toString());
                    }
                    else
                    {
                        hashMap.put("description", null);
                    }
                    database.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            findViewById(R.id.overlay).setVisibility(View.GONE);
                            Toast.makeText(AddMatch.this,"Match Has Been Added Successfully",Toast.LENGTH_SHORT);
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            findViewById(R.id.overlay).setVisibility(View.GONE);
                        }
                    });
                }
            }
        });
    }

}
