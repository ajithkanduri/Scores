package com.example.android.scores;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class LiveMatchesAdapter extends RecyclerView.Adapter<LiveMatchesAdapter.LiveViewHolder>{
    ArrayList<String> matchnolist;
    ArrayList<String> teamAlist;
    ArrayList<String> teamBlist;
    ArrayList<String> timelist;
    Context context;
    String parent;
    public LiveMatchesAdapter(Context context,ArrayList<String> matchnolist,ArrayList<String> teamAlist,ArrayList<String> teamBlist,ArrayList<String> timelist,String parent)
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
    public LiveMatchesAdapter.LiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.upcoming_matches_item,parent,false);
        LiveViewHolder vh = new LiveViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveMatchesAdapter.LiveViewHolder holder, final int position) {
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

    public class LiveViewHolder extends RecyclerView.ViewHolder {
        TextView matchno;
        TextView teamA;
        TextView teamB;
        TextView time;
        public LiveViewHolder(View itemView) {
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
                        Intent intent = new Intent(context,UpdateScore.class);
                        intent.putExtra("MatchNumber",matchnumber);
                        intent.putExtra("Team A",teamA);
                        intent.putExtra("Team B",teamB);
                        intent.putExtra("Time",time);
                        intent.putExtra("Parent",parent);
                        context.startActivity(intent);
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
}
