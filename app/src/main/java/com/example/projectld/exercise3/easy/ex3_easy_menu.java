package com.example.projectld.exercise3.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.exercise3.GridviewAdapter;
import com.example.projectld.R;

import java.util.ArrayList;

public class ex3_easy_menu extends AppCompatActivity {

    DatabaseHelper dbHelper;
    ArrayList<String> words,st;
    GridView gridView,gridView_admin;
    GridviewAdapter gridviewAdapter,gridviewAdapter_admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3_easy_menu);

        dbHelper = new DatabaseHelper(getApplicationContext());

        words = dbHelper.queryword("word");
        gridView = findViewById(R.id.gridView);

        //create Girdview
        gridviewAdapter = new GridviewAdapter(words,this,"easy");
        gridView.setAdapter(gridviewAdapter);

        //gridview admin setting
        gridView_admin = findViewById(R.id.gridView_admin);
        st = dbHelper.GetGroupname("Setting_ex3_easy","st_ex3_easy_id");
        gridviewAdapter_admin = new GridviewAdapter(st,this,"ex3_easy_game_st");
        gridView_admin.setAdapter(gridviewAdapter_admin);


    }
}
