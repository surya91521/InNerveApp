package com.example.innerveapp;

import android.content.ClipData;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class TimelineViewAdapter extends RecyclerView.Adapter<TimelineViewAdapter.TimelineViewHolder> {

    private static final int VIEW_TYPE_TOP = 0;
    private static final int VIEW_TYPE_MIDDLE = 1;
    private static final int VIEW_TYPE_BOTTOM = 2;
    private List<ClipData.Item> mItems;

    String[] titles;
    String[] subtitles;
    int[] icons;
    String[] dates;

    public TimelineViewAdapter(String[] titles, String[] subtitles, int[] icons, String[] dates)
    {
        this.titles = titles;
        this.subtitles = subtitles;
        this.icons = icons;
        this.dates = dates;
        mItems = new ArrayList<>();

        for(int i=0;i<titles.length;i++)
        {
            mItems.add(new ClipData.Item(titles[i]));
        }
    }

    @NonNull
    @Override
    public TimelineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.timeline_item, viewGroup, false);
        TimelineViewHolder holder = new TimelineViewHolder(v);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull TimelineViewHolder timelineViewHolder, int i) {
        ClipData.Item item = mItems.get(i);
        timelineViewHolder.itemTitle.setText(titles[i]);
        timelineViewHolder.itemSubtitle.setText(subtitles[i]);
        timelineViewHolder.icon.setImageResource(icons[i]);
        timelineViewHolder.itemDate.setText(dates[i]);

        switch(timelineViewHolder.getItemViewType())
        {
            case VIEW_TYPE_TOP:
                timelineViewHolder.itemLine.setBackgroundResource(R.drawable.line_bg_top);
                break;
            case VIEW_TYPE_MIDDLE:
                timelineViewHolder.itemLine.setBackgroundResource(R.drawable.line_bg_middle);
                break;
            case VIEW_TYPE_BOTTOM:
                timelineViewHolder.itemLine.setBackgroundResource(R.drawable.line_bg_bottom);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(position == 0)
        {
            return VIEW_TYPE_TOP;
        }else if(position == mItems.size() - 1)
        {
            return VIEW_TYPE_BOTTOM;
        }

        return VIEW_TYPE_MIDDLE;
    }

    public static class TimelineViewHolder extends RecyclerView.ViewHolder
    {
        TextView itemTitle;
        TextView itemSubtitle;
        TextView itemDate;
        FrameLayout itemLine;
        ImageView icon;

        public TimelineViewHolder(View itemView)
        {
            super(itemView);
            itemTitle = itemView.findViewById(R.id.item_title);
            itemSubtitle = itemView.findViewById(R.id.item_subtitle);
            itemDate = itemView.findViewById(R.id.item_date);
            itemLine = itemView.findViewById(R.id.item_line);
            icon = itemView.findViewById(R.id.icon_timeline);
        }
    }

}
