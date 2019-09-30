package com.example.projectld.navigationDrawer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;

public class F_Sentence_data extends AppCompatActivity {

    DatabaseHelper databaseHelper;
    GridView gridView;
    GridviewAdapter gridviewAdapter;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_sentence_data);


        databaseHelper = new DatabaseHelper(getApplicationContext());
        gridView = findViewById(R.id.GridView_Sentence);
        ArrayList Sentence = databaseHelper.queryword("Sentence");

        gridviewAdapter = new GridviewAdapter(Sentence,getApplicationContext(),"Sentence_Data",R.drawable.radius_button_color2);
        gridView.setAdapter(gridviewAdapter);
    }

}
