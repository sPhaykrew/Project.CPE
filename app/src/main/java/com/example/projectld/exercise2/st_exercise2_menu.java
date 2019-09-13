package com.example.projectld.exercise2;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

public class st_exercise2_menu extends AppCompatActivity {

    Button add_st;
    GridView gridView;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_exercise2_menu);

        dbHelper = new DatabaseHelper(getApplicationContext());
        add_st = (Button) findViewById(R.id.add_st);
        gridView = findViewById(R.id.GridViewnomal);
        ArrayList<String> Groupname = dbHelper.GetGroupname("Setting_ex2","st_ex2_id");

        //create Girdview
        GridviewAdapter gridviewAdapter = new GridviewAdapter(Groupname,this,"st_easy",R.drawable.radius_button_color2);
        gridView.setAdapter(gridviewAdapter);

        add_st.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(st_exercise2_menu.this, gridview_ex2_select_char.class);
                startActivity(intent);
                finish();
            }
        });

    }
}
