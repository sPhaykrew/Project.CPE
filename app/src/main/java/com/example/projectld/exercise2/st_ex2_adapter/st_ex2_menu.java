package com.example.projectld.exercise2.st_ex2_adapter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.exercise3.GridviewAdapter;
import com.example.projectld.R;

import java.util.ArrayList;

public class st_ex2_menu extends AppCompatActivity {

    GridView gridView;
    DatabaseHelper dbHelper;

    public static Activity close_activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_ex3_menu);

        close_activity = this;

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("สร้างแบบฝึก");
        Title.setTextSize(20);

        ImageView show_menu = toolbar.findViewById(R.id.show_menu);
        show_menu.setVisibility(View.GONE);

        ImageView back = toolbar.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        dbHelper = new DatabaseHelper(getApplicationContext());
        gridView = findViewById(R.id.GridViewnomal);
        ArrayList<String> Groupname = dbHelper.GetGroupname("Setting_ex2","st_ex2_id");

        //create Girdview
        GridviewAdapter gridviewAdapter = new GridviewAdapter(Groupname,this,"st_ex2",R.drawable.radius_button_color_ex2,null);
        gridView.setAdapter(gridviewAdapter);

        TextView textHide = findViewById(R.id.noEX);

        if (Groupname.size() != 0){
            textHide.setVisibility(View.INVISIBLE);
        }

        FloatingActionButton Add = findViewById(R.id.Add);
        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(st_ex2_menu.this, gridview_ex2_select_char.class);
                startActivity(intent);
            }
        });

    }
}
