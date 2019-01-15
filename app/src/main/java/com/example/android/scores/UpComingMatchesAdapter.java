package com.example.android.scores;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class UpComingMatchesAdapter extends RecyclerView.Adapter<UpComingMatchesAdapter.UpComingMatchesViewHolder> {
    ArrayList<String> matchnolist;
    ArrayList<String> teamAlist;
    ArrayList<String> teamBlist;
    ArrayList<String> timelist;
    Context context;
    String parent;
    public UpComingMatchesAdapter(Context context,ArrayList<String> matchnolist,ArrayList<String> teamAlist,ArrayList<String> teamBlist,ArrayList<String> timelist,String parent)
    {
        this.context = context;
        this.matchnolist = matchnolist;
        this.teamAlist = teamAlist;
        this.teamBlist = teamBlist;
        this.timelist = timelist;
        this.parent = parent;
    }
    @NonNull
    @Override
    public UpComingMatchesAdapter.UpComingMatchesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_matches_item,parent,false);
        UpComingMatchesViewHolder vh = new UpComingMatchesViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull UpComingMatchesAdapter.UpComingMatchesViewHolder holder, final int position) {
        holder.matchno.setText(matchnolist.get(position));
        holder.teamA.setText(teamAlist.get(position));
        holder.teamB.setText(teamBlist.get(position));
        holder.time.setText(timelist.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAlertDialog(matchnolist.get(position),teamAlist.get(position),teamBlist.get(position),timelist.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return matchnolist.size();
    }

    public class UpComingMatchesViewHolder extends RecyclerView.ViewHolder {
        TextView matchno;
        TextView teamA;
        TextView teamB;
        TextView time;
        public UpComingMatchesViewHolder(View itemView) {
            super(itemView);
            matchno = itemView.findViewById(R.id.match_number);
            teamA = itemView.findViewById(R.id.teamA);
            teamB = itemView.findViewById(R.id.teamB);
            time = itemView.findViewById(R.id.time);
        }
    }
    private void createAlertDialog(final String matchnumber, final String teamA, final String teamB, final String time)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(context)
                //set title
                .setTitle("Are you sure to Exit")
                //set message
                .setMessage("Exiting will call finish() method")
                //set positive button
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what would happen when positive button is clicked
                        ProgressDialog progressDialog = MyProgressDialog();
                        progressDialog.show();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Scores").child("Upcoming Matches").child(parent);
                        DatabaseReference reference = databaseReference.child(matchnumber);
                        reference.removeValue();
                        progressDialog.dismiss();
//                        databaseReference.addValueEventListener(new ValueEventListener() {
//                            @Override
//                            public void onDataChange(DataSnapshot dataSnapshot) {
//                                for (DataSnapshot dataSnapshot1:dataSnapshot.getChildren())
//                                {
//                                    if(dataSnapshot1.getKey().equals(matchnumber))
//                                    {
//                                        DatabaseReference reference = databaseReference.child(matchnumber);
//                                        reference.removeValue();
//                                    }
//                                }
//                            }
//
//                            @Override
//                            public void onCancelled(DatabaseError databaseError) {
//
//                            }
//                        });
                        addtoLive(matchnumber,teamA,teamB,time);
                    }

                })
                //set negative button
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //set what should happen when negative button is clicked
                        Toast.makeText(context,"Nothing Happened",Toast.LENGTH_LONG).show();
                    }
                })
                .show();
    }

    private void addtoLive(String matchnumber, String teamA, String teamB, String time) {
        ProgressDialog progressDialog = MyProgressDialog();
        progressDialog.show();
        DatabaseReference database = FirebaseDatabase.getInstance().getReference("Scores").child("Live Matches").child(parent).child(matchnumber);
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("Team A",teamA);
        hashMap.put("Team B",teamB);
        hashMap.put("Time",time);
        hashMap.put("TAScore","0");
        hashMap.put("TBScore","0");
        database.setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(context,"Match Has Been Added Successfully",Toast.LENGTH_SHORT);
            }
        });
        progressDialog.dismiss();
    }
    private ProgressDialog MyProgressDialog() {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("loading ...");
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        return progressDialog;
    }
}
