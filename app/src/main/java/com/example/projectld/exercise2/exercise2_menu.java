package com.example.projectld.exercise2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;

import com.example.projectld.R;
import com.example.projectld.exercise3.GridviewAdapter;

import java.util.ArrayList;
import java.util.Arrays;

public class exercise2_menu extends AppCompatActivity {

    GridView gridView;
    GridviewAdapter gridviewAdapter;
    ArrayList<String> arrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise2_menu);

        String[] Char = {"ก","ข","ฃ","ค","ฅ","ฆ","ง","จ","ฉ","ช"};
        arrayList.addAll(Arrays.asList(Char));

        //create Girdview
        gridView = findViewById(R.id.gridView);
        gridviewAdapter = new GridviewAdapter(arrayList,this,"exercise2_game",R.drawable.radius_button_color1);
        gridView.setAdapter(gridviewAdapter);

    }
}
