package com.example.projectld.exercise3.st_nomal;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise3.GridviewAdapter;
import com.example.projectld.exercise3.st_easy.grid_view_ex3_st_easy_selectWord;
import com.example.projectld.exercise3.st_easy.st_ex3_easy_menu;

import java.util.ArrayList;

public class st_ex3_normal_menu extends AppCompatActivity {
    Button add_st;
    GridView gridView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_ex3_easy_menu);

        dbHelper = new DatabaseHelper(getApplicationContext());
        add_st = (Button) findViewById(R.id.add_st);
        gridView = findViewById(R.id.gridViewnomal);
        ArrayList<String> Groupname = dbHelper.GetGroupname("Setting_ex3_normal","st_ex3_normal_id");

        //create Girdview
        GridviewAdapter gridviewAdapter = new GridviewAdapter(Groupname,this,"st_normal");
        gridView.setAdapter(gridviewAdapter);

        add_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(st_ex3_normal_menu.this, grid_view_ex3_st_normal_selectWord.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
