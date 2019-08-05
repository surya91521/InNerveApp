package com.example.innerveapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Timeline extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("Timeline");

        recyclerView = view.findViewById(R.id.timeline_recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        int n = 6;

        String[] titles = new String[n];
        String[] subtitles = new String[n];

        for(int i=0;i<n;i++)
        {
            String titleName = "timelinet" + (i+1);
            String subName = "timelines" + (i+1);
            titles[i] = getResources().getString(getResources().getIdentifier(titleName, "string", getActivity().getPackageName()));
            subtitles[i] = getResources().getString(getResources().getIdentifier(subName, "string", getActivity().getPackageName()));
        }

        adapter = new TimelineViewAdapter(titles, subtitles);
        recyclerView.setAdapter(adapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.timeline, container, false);

    }
}
