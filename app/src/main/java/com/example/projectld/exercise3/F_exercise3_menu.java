package com.example.projectld.exercise3;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.projectld.R;
import com.example.projectld.exercise3.easy.ex3_easy_menu;
import com.example.projectld.exercise3.hard.ex3_hard_menu;
import com.example.projectld.exercise3.nomal.ex3_nomal_menu;

public class F_exercise3_menu extends Fragment {
    Button easy,nomal,hard;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_exercise3_menu, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        easy = (Button) view.findViewById(R.id.easy);
        nomal = (Button) view.findViewById(R.id.nomal);
        hard = (Button) view.findViewById(R.id.hard);

        easy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ex3_easy_menu.class);
                startActivity(intent);
            }
        });

        nomal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ex3_nomal_menu.class);
                startActivity(intent);
            }
        });

        hard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ex3_hard_menu.class);
                startActivity(intent);
            }
        });
    }
}
