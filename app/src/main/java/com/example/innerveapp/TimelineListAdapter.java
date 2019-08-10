package com.example.innerveapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TimelineListAdapter extends BaseExpandableListAdapter {

    private HashMap<String, List<String>> itemMap;
    private String[] timelineTitles;

    public TimelineListAdapter(HashMap<String, List<String>> itemMapi)
    {
        this.itemMap = itemMapi;
        this.timelineTitles = itemMapi.keySet().toArray(new String[0]);
    }

    @Override
    public int getGroupCount() {
        return timelineTitles.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return itemMap.get(timelineTitles[i]).size();
    }

    @Override
    public Object getGroup(int i) {
        return timelineTitles[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return itemMap.get(timelineTitles[i]).get(i1);
    }

    @Override
    public long getGroupId(int i) {
        return i;
    }

    @Override
    public long getChildId(int i, int i1) {
        return i * i1;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_list_group, viewGroup, false);
        }

        TextView textView = viewGroup.findViewById(R.id.textView);
        textView.setText(String.valueOf(getGroup(i)));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {

        if(view == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_list_item, viewGroup, false);
        }

        TextView textView = viewGroup.findViewById(R.id.textView);
        textView.setText(String.valueOf(getChild(i, i1)));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
