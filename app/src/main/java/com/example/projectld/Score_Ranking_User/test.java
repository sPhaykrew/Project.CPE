package com.example.projectld.Score_Ranking_User;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;

import java.util.ArrayList;

public class test extends AppCompatActivity {

    RecyclerView verticalRecyclerView;
    VerticalRecyclerViewAdapter verticalRecyclerViewAdapter;
    ArrayList<VerticalModel> verticalModels;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_main);

        verticalModels = new ArrayList<>();

        verticalRecyclerView = findViewById(R.id.recyclerview);
        verticalRecyclerView.setHasFixedSize(true);

        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));

        verticalRecyclerViewAdapter = new VerticalRecyclerViewAdapter(this,verticalModels);

        verticalRecyclerView.setAdapter(verticalRecyclerViewAdapter);

        setData();

        // make vertical adapter for recyclerview

    }

    public void setData(){

        SharedPreferences user = getSharedPreferences("User", Context.MODE_PRIVATE);


        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList Groupname = databaseHelper.SearchGroupName();

        for (int i=0;i<Groupname.size();i++){
            VerticalModel verticalModel = new VerticalModel();
            verticalModel.setTitle(String.valueOf(Groupname.get(i)));
            verticalModel.setAvgScore(String.valueOf(i));

            ArrayList<HorizontalModel> horizontalModels = databaseHelper.item_word_Ranking
                    (user.getString("UserID",null), String.valueOf(Groupname.get(i)));

            Log.d("Horizon", horizontalModels.toString());

//            for (int j=0;j<5;j++){
//                HorizontalModel horizontalModel = new HorizontalModel();
//                horizontalModel.setWord("name " + j);
//                horizontalModel.setScore("score " + j);
//
//                horizontalModels.add(horizontalModel);
//            }
            verticalModel.setArrayList(horizontalModels);
            verticalModels.add(verticalModel);
        }
        verticalRecyclerViewAdapter.notifyDataSetChanged();
    }

}
