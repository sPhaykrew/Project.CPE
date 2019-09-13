package com.example.projectld.exercise2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class exercise2_menu extends AppCompatActivity {

    GridView gridView,gridView_admin;
    GridviewAdapter gridviewAdapter,gridviewAdapter_admin;
    ArrayList<String> arrayList,st;
    DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise2_menu);

        String[] Char = {"ก","ข","ฃ","ค","ฅ","ฆ","ง","จ","ฉ","ช"};
        arrayList = new ArrayList<>();
        arrayList.addAll(Arrays.asList(Char));

        databaseHelper = new DatabaseHelper(this);

        //create Girdview
        gridView = findViewById(R.id.gridView);
        gridviewAdapter = new GridviewAdapter(arrayList,this,"exercise2_game",R.drawable.radius_button_color1);
        gridView.setAdapter(gridviewAdapter);

        //gridview admin setting
        gridView_admin = findViewById(R.id.gridView_admin);
        st = databaseHelper.GetGroupname("Setting_ex2","st_ex2_id");
        gridviewAdapter_admin = new GridviewAdapter(st,this,"exercise2_game_st",R.drawable.radius_button_color1);
        gridView_admin.setAdapter(gridviewAdapter_admin);

    }
}