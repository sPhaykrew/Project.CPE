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

import com.example.projectld.Grid_ex3_st_easy;
import com.example.projectld.R;

public class F_setting extends Fragment {

    Button ex2,ex3,ex4,ex5;

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

        ex3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Grid_ex3_st_easy.class);
                startActivity(intent);
            }
        });

    }
}
