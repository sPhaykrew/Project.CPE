package com.example.projectld;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class F_menu extends Fragment {

    Button exercise1,exercise2,exercise3;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_menu,container,false);


    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        exercise1 = (Button) view.findViewById(R.id.Exercise1);
        exercise2 = (Button) view.findViewById(R.id.Exercise2);
        exercise3 = (Button) view.findViewById(R.id.Exercise3);

        exercise3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),drag.class);
                startActivity(intent);
            }
        });
    }
}
