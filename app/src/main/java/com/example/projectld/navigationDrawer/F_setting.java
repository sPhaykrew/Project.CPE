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

import com.example.projectld.R;
import com.example.projectld.exercise3.st_easy.st_ex3_easy_menu;
import com.example.projectld.exercise3.st_hard.st_ex3_hard_menu;
import com.example.projectld.exercise3.st_nomal.st_ex3_normal_menu;

public class F_setting extends Fragment {

    Button ex2,ex3,ex4,ex5,Add_Word;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ex2 = (Button) view.findViewById(R.id.ex2);
        ex3 = (Button) view.findViewById(R.id.ex3);
        ex4 = (Button) view.findViewById(R.id.ex4);
        ex5 = (Button) view.findViewById(R.id.ex5);
        Add_Word = view.findViewById(R.id.Add_Word);

        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), st_ex3_easy_menu.class);
                startActivity(intent);
            }
        });

        ex4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), st_ex3_normal_menu.class);
                startActivity(intent);
            }
        });

        ex5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), st_ex3_hard_menu.class);
                startActivity(intent);
            }
        });

        Add_Word.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), com.example.projectld.Add_Word.class);
                startActivity(intent);
            }
        });

    }
}
