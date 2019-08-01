package com.example.projectld.exercise3.st_easy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.exercise3.MyRecyclerViewAdapter;
import com.example.projectld.R;

import java.util.ArrayList;

public class st_ex3_easy_inMenu extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;
    Bundle group;
    String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_ex3_easy_inmenu);

        ArrayList<String> st = new ArrayList<>();
        group = getIntent().getExtras();

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        // data to populate the RecyclerView with
        position = String.valueOf(group.getInt("countarray")); //รับค่า position มาจากหน้า Gridviewadpater
        st = databaseHelper.group_st_easy(position);

        // set up the RecyclerView
        RecyclerView recyclerView = findViewById(R.id.rvAnimals);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyRecyclerViewAdapter(this, st);
        adapter.setClickListener(this);

        recyclerView.addItemDecoration(new DividerItemDecoration(getApplication(), // เส้นแบ่ง ------
                DividerItemDecoration.VERTICAL));

        recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(this, "You clicked " + adapter.getItem(position) + " on row number " + position, Toast.LENGTH_SHORT).show();
    }
}
