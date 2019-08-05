package com.example.innerveapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Faq extends Fragment {



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("FAQ");

        ExpandableListView expandableListView = view.findViewById(R.id.faqListView);

        HashMap<String, List<String>> item = new HashMap<>();

        ArrayList<String> answers0 = new ArrayList<>();
        answers0.add("The event will be organized on 1st, 2nd and 3rd October. There are no registration fees for the event but Rs.100 per team has to be submitted while registering as an assurance amount which will be returned to the team on 1st October.");
        ArrayList<String> answers1 = new ArrayList<>();
        answers1.add("If you love to code, you are more than welcome to participate in our Hackathon.");
        ArrayList<String> answers2 = new ArrayList<>();
        answers2.add("Accommodation will be provided to all the teams coming from outside Pune. Travel reimbursement upto 3rd AC will be provided to the same (Max. upto ₹5000/- per team).");
        ArrayList<String> answers3 = new ArrayList<>();
        answers3.add("The hackathon will be conducted in our college itself - Army Institute of Technology");

        item.put("What are the dates and the registration fee for the Hackathon?", answers0);
        item.put("Do I need to have any specific qualifications to be a participant of the Hackathon?", answers1);
        item.put("What are the accomodation facilities and will travel allowance be provided?", answers2);
        item.put("Where will the hackathon be conducted?", answers3);

        FaqListAdapter faqListAdapter = new FaqListAdapter(item);
        expandableListView.setAdapter(faqListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.faq, container, false);

    }
}
