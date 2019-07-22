package com.example.projectld;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class st_ex3_easy extends AppCompatActivity implements MyRecyclerViewAdapter.ItemClickListener {

    MyRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.st_ex3_easy);

        ArrayList<String> st = new ArrayList<>();
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        // data to populate the RecyclerView with
        st = databaseHelper.queryst();

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
