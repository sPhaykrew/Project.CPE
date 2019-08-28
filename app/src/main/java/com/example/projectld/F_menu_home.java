package com.example.projectld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.projectld.Score_Ranking_User.test;
import com.example.projectld.exercise3.exercise3_menu;

public class F_menu_home extends Fragment {

    CardView exercise1,exercise2,exercise3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.main_menu,container,false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        exercise1 =  view.findViewById(R.id.Exercise1);
        exercise2 =  view.findViewById(R.id.Exercise2);
        exercise3 =  view.findViewById(R.id.Exercise3);

        exercise2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), test.class);
                startActivity(intent);
            }
        });


        exercise3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), exercise3_menu.class);
                startActivity(intent);
            }
        });
    }
}