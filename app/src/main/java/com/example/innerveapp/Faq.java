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
        ArrayList<String> answers4 = new ArrayList<>();
        answers4.add("This is a very hands-on and grassroots event. Bring your laptops, mobile phones, Kinects, Oculus Rifts, Leap Motions, wearable computing devices, to whatever inspires you but above all, your energy and your brain! Don’t worry if you forget something, there will be tons of stuff to do.");
        ArrayList<String> answers5 = new ArrayList<>();
        answers5.add("No! In order to design, build, and pitch an app you’ll need more than just technical skills on a team. Each team will have to identify who is covering the following skill sets: Software Development, Marketing, Business, and Graphic Design/Creative.");
        ArrayList<String> answers6 = new ArrayList<>();
        answers6.add("Anyone is welcome to participate in this event. While experience coding and programming is a huge plus, teams will also need people with strong presentation skills and brilliant ideas.");
        ArrayList<String> answers7 = new ArrayList<>();
        answers7.add("Other than finding fellow brilliant minds with complementary skills to team up with, there is nothing that you need to prepare in advance.");
        item.put("What are the dates and the registration fee for the Hackathon?", answers0);
        item.put("Do I need to have any specific qualifications to be a participant of the Hackathon?", answers1);
        item.put("What are the accomodation facilities and will travel allowance be provided?", answers2);
        item.put("Where will the hackathon be conducted?", answers3);
        item.put(" What do I bring?",answers4);
        item.put("Are hackathons just for techies?",answers5);
        item.put("Who can participate? Are there any prerequisites or required skills?",answers6);
        item.put("Is there anything that I need to prep?",answers7);

        FaqListAdapter faqListAdapter = new FaqListAdapter(item);
        expandableListView.setAdapter(faqListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.faq, container, false);

    }
}
