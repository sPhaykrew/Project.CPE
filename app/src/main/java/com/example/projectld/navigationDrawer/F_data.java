package com.example.projectld.navigationDrawer;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectld.My_Score.My_Score_main_2;
import com.example.projectld.My_Score.My_Score_main_3;
import com.example.projectld.My_Score.My_Score_main_4;
import com.example.projectld.My_Score.My_Score_main_5;
import com.example.projectld.R;

public class F_data extends Fragment {

    Button score1,score2,score3,score4,score5;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_data,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        score1 = view.findViewById(R.id.myscore1);
        score2 = view.findViewById(R.id.myscore2);
        score3 = view.findViewById(R.id.myscore3);
        score4 = view.findViewById(R.id.myscore4);
        score5 = view.findViewById(R.id.myscore5);

        score2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), My_Score_main_2.class);
                startActivity(intent);
            }
        });

        score3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), My_Score_main_3.class);
                startActivity(intent);
            }
        });

        score4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), My_Score_main_4.class);
                startActivity(intent);
            }
        });

        score5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), My_Score_main_5.class);
                startActivity(intent);
            }
        });

    }
}
