package com.example.android.scores;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventViewHolder> {

    private ArrayList<EventDetails> list = new ArrayList<>();
    private Context context;
    private ArrayList<String> type1 = new ArrayList<>();
    private ArrayList<String> type2 = new ArrayList<>();
    private ArrayList<String> type3 = new ArrayList<>();
    public EventsAdapter(ArrayList<EventDetails> list, Context context) {
        this.list = list;
        this.context = context;
        type1.add("Basketball Boys");
        type1.add("Basketball Girls");
        type1.add("Football Boys");
        type1.add("Football Girls");
        type1.add("Kabaddi");
        type1.add("Hockey");
        type3.add("Carroms");
        type3.add("Chess");
        type2.add("Badminton Boys");
        type2.add("Badminton Girls");
        type2.add("Badminton Mixed");
        type2.add("Volleyball Girls");
        type2.add("Volleyball Boys");
        type2.add("Squash");
        type2.add("Squash Singles");
        type2.add("Tennis Boys");
        type2.add("Tennis Girls");
        type2.add("Table Tennis Boys");
        type2.add("Table Tennis Girls");
        type2.add("Throwball");
        type3.add("Athletics");
        type3.add("Body-Building");
        type3.add("Duathlon");
        type3.add("Powerlifting");
        type3.add("Skating");
        type3.add("Snooker");
    }

    @NonNull
    @Override
    public EventsAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fragment_events_item, parent, false);
        return new EventViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.EventViewHolder holder, final int position) {

        holder.eventView.setVisibility(View.VISIBLE);
        holder.eventName.setText(list.get(position).getName());
        holder.eventTagLine.setVisibility(View.VISIBLE);
        holder.eventPrizemoney.setVisibility(View.VISIBLE);
        holder.eventFee.setVisibility(View.VISIBLE);
        if (list.get(position).getTagline() == null || list.get(position).getTagline().equals("")) {
            holder.eventTagLine.setVisibility(View.GONE);
        } else {
            holder.eventTagLine.setText(list.get(position).getTagline());
        }
        if (list.get(position).getPrice() == null || list.get(position).getPrice().equals("")) {
            holder.eventFee.setVisibility(View.GONE);
        } else {
            holder.eventFee.setText("Fee: " + list.get(position).getPrice());
        }
        if (list.get(position).getPrize() == null || list.get(position).getPrize().equals("")) {
            holder.eventPrizemoney.setVisibility(View.GONE);
        } else {
            holder.eventPrizemoney.setText("Prize: " + list.get(position).getPrize());
        }

        holder.eventView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i=0;i<type1.size();i++) {
                    if (list.get(position).getName().equals(type1.get(i))) {
                        Intent intent = new Intent(context, ActionActivity.class);
                        intent.putExtra("id", list.get(position).getId());
                        intent.putExtra("category","type1");
                        intent.putExtra("name",list.get(position).getName());
                        v.getContext().startActivity(intent);
                    }
                }
                for (int i=0;i<type2.size();i++)
                {
                    if(list.get(position).getName().equals(type2.get(i)))
                    {
                        Intent intent = new Intent(context, ActionActivity.class);
                        intent.putExtra("id", list.get(position).getId());
                        intent.putExtra("category","type2");
                        intent.putExtra("name",list.get(position).getName());
                        v.getContext().startActivity(intent);
                    }
                }
                if(list.get(position).getName().equals("Cricket"))
                {
                    Intent intent = new Intent(context, ActionActivity.class);
                    intent.putExtra("id", list.get(position).getId());
                    intent.putExtra("category","cricket");
                    intent.putExtra("name",list.get(position).getName());
                    v.getContext().startActivity(intent);
                }
                for (int i=0;i<type3.size();i++)
                {
                    if(list.get(position).getName().equals(type3.get(i)))
                    {
                        Intent intent = new Intent(context, SIngleEvents.class);
                        intent.putExtra("id", list.get(position).getId());
                        intent.putExtra("category","type3");
                        intent.putExtra("name",list.get(position).getName());
                        v.getContext().startActivity(intent);
                    }
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {
        TextView eventName, eventTagLine, eventFee, eventPrizemoney;
        CardView eventView;

        public EventViewHolder(View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.events_name);
            eventTagLine = itemView.findViewById(R.id.events_tagline);
            eventFee = itemView.findViewById(R.id.events_fee);
            eventPrizemoney = itemView.findViewById(R.id.events_prizemoney);
            eventView = itemView.findViewById(R.id.event_item_cardview);

        }

    }
}