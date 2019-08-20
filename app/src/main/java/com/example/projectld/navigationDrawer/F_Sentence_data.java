package com.example.projectld.navigationDrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;

public class F_Sentence_data extends Fragment {

    DatabaseHelper databaseHelper;
    GridView gridView;
    GridviewAdapter gridviewAdapter;
    DrawerLayout drawerLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sentence_data,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getActivity());
        gridView = view.findViewById(R.id.GridView_Sentence);
        ArrayList Sentence = databaseHelper.queryword("Sentence");

        gridviewAdapter = new GridviewAdapter(Sentence,getActivity(),"Sentence_Data",R.drawable.radius_button_color2);
        gridView.setAdapter(gridviewAdapter);


    }
}
