package com.example.projectld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import java.util.ArrayList;

public class ex3_hard_menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex3_nomal_menu);

        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());

        ArrayList<String> words;
        words = dbHelper.queryword("Sentence");
        GridView gridView = findViewById(R.id.gridViewnomal);

        //create Girdview
        GridviewAdapter gridviewAdapter = new GridviewAdapter(words,this,"hard");
        gridView.setAdapter(gridviewAdapter);

    }
}
