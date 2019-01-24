package com.example.android.scores.api;

import com.example.android.scores.EventDetails;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface EventsInterface {
    @GET("events")
    Call<ArrayList<EventDetails>> getEvents();

    @GET("events?fields=name,startTime,endTime,tagline,venue")
    Call<ArrayList<EventDetails>> getEventSchedule();

    @GET("events/{id}")
    Call<EventDetails> getEventDetails(@Path("id") String id);
}
