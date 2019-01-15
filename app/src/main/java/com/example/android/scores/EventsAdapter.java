package com.example.android.scores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsViewHolder> {
    Context context;
    ArrayList<String> events = new ArrayList<>();
    public EventsAdapter(Context context)
    {
        this.context = context;
        events.add("Cricket");
        events.add("Badminton Boys");
        events.add("Badminton Girls");
        events.add("Badmintion Mixed");
        events.add("BasketBall Boys");
    }
    @NonNull
    @Override
    public EventsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eventsitem,parent,false);
            EventsViewHolder vh = new EventsViewHolder(v);
            return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final EventsViewHolder holder, int position) {
        holder.textView.setText(events.get(position));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ActionActivity.class);
                intent.putExtra("Parent",holder.textView.getText());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class EventsViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public EventsViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.eventname);
        }
    }
}
