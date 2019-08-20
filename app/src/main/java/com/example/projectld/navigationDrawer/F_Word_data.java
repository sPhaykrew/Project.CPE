package com.example.projectld.navigationDrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;

public class F_Word_data extends Fragment {

    DatabaseHelper databaseHelper;
    GridView gridView;
    GridviewAdapter gridviewAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_word_data,container,false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        databaseHelper = new DatabaseHelper(getActivity());
        gridView = view.findViewById(R.id.GridView_Sentence);
        ArrayList Word = databaseHelper.queryword("Word");

        gridviewAdapter = new GridviewAdapter(Word,getActivity(),"Word_data",R.drawable.radius_button_color1);
        gridView.setAdapter(gridviewAdapter);


    }
}
