package com.example.innerveapp;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class Theme extends Fragment {

    private ImageView imageView;
    private ImageView imageView1;
    private ImageView imageView2;
    private ImageView imageView3;
    private ImageView imageView4;
    private ImageView imageView5;
    private TextView textView;
    private TextView textView1;
    private TextView textView2;
    private TextView textView3;
    private TextView textView4;
    private TextView textView5;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imageView = view.findViewById(R.id.auto);
        textView = view.findViewById(R.id.Auto);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.VISIBLE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.GONE);
            }
        });

        imageView1 = view.findViewById(R.id.ai);
        textView1 = view.findViewById(R.id.Ai);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.GONE);
            }
        });

        imageView2 = view.findViewById(R.id.cyber);
        textView2 = view.findViewById(R.id.Cyber);
        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.VISIBLE);
                textView3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.GONE);
            }
        });

        imageView3 = view.findViewById(R.id.smart);
        textView3 = view.findViewById(R.id.Smart);
        imageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.VISIBLE);
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.GONE);
            }
        });

        imageView4 = view.findViewById(R.id.health);
        textView4 = view.findViewById(R.id.Health);
        imageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                textView4.setVisibility(View.VISIBLE);
                textView5.setVisibility(View.GONE);
            }
        });

        imageView5 = view.findViewById(R.id.block);
        textView5 = view.findViewById(R.id.Block);
        imageView5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                textView.setVisibility(View.GONE);
                textView1.setVisibility(View.GONE);
                textView2.setVisibility(View.GONE);
                textView3.setVisibility(View.GONE);
                textView4.setVisibility(View.GONE);
                textView5.setVisibility(View.VISIBLE);
            }
        });


        getActivity().setTitle("Themes");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

          return inflater.inflate(R.layout.theme, container, false);

    }



}
