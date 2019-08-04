package com.example.innerveapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
        answers0.add("Answer 1!");
        ArrayList<String> answers1 = new ArrayList<>();
        answers1.add("Answer 2!");

        item.put("Question 1?", answers0);
        item.put("Question 2?", answers1);

        FaqListAdapter faqListAdapter = new FaqListAdapter(item);
        expandableListView.setAdapter(faqListAdapter);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.faq, container, false);

    }
}
