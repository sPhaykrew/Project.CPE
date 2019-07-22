package com.example.projectld.exercise3.easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.exercise3.GridviewAdapter;
import com.example.projectld.R;

import java.util.ArrayList;

public class ex3_easy_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3_easy_menu);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        ArrayList<String> words;
        words = dbHelper.queryword("word");
        GridView gridView = findViewById(R.id.gridView);

        //create Girdview
        GridviewAdapter gridviewAdapter = new GridviewAdapter(words,this,"easy");
        gridView.setAdapter(gridviewAdapter);

    }
}
