package com.example.innerveapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

public class FaqListAdapter extends BaseExpandableListAdapter {

    private HashMap<String, List<String>> stringListHashMap;
    private String[] listHeaderGroup;

    public FaqListAdapter(HashMap<String, List<String>> stringListHashMap) {
        this.stringListHashMap = stringListHashMap;
        this.listHeaderGroup = stringListHashMap.keySet().toArray(new String[0]);
    }

    @Override
    public int getGroupCount() {
        return listHeaderGroup.length;
    }

    @Override
    public int getChildrenCount(int i) {
        return stringListHashMap.get(listHeaderGroup[i]).size();
    }

    @Override
    public Object getGroup(int i) {
        return listHeaderGroup[i];
    }

    @Override
    public Object getChild(int i, int i1) {
        return stringListHashMap.get(listHeaderGroup[i]).get(i1);
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
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faq_list_group, viewGroup, false);
        }

        TextView textView = view.findViewById(R.id.textView);
        textView.setText(String.valueOf(getGroup(i)));

        return view;
    }

    @Override
    public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
        if(view == null)
        {
            view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.faq_list_item, viewGroup, false);
        }

        TextView textView =  view.findViewById(R.id.textView);
        textView.setText(String.valueOf(getChild(i, i1)));

        return view;
    }

    @Override
    public boolean isChildSelectable(int i, int i1) {
        return false;
    }
}
