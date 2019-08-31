package com.example.projectld.Edit_User_From_Admin;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.My_Score.Ranking.Vertical_Ranking_Adapter;
import com.example.projectld.My_Score.Ranking.Vertical_Ranking_Model;
import com.example.projectld.My_Score.Score.HorizontalModel;
import com.example.projectld.My_Score.Score.VerticalModel;
import com.example.projectld.My_Score.Score.VerticalRecyclerViewAdapter;
import com.example.projectld.R;
import com.example.projectld.recyclerView_Ranking.Ranking_Item;

import java.util.ArrayList;

public class Admin_score_exercise4 extends AppCompatActivity {

    //score
    RecyclerView ScoreRecyclerView;
    VerticalRecyclerViewAdapter verticalRecyclerViewAdapter;
    ArrayList<VerticalModel> verticalModels;

    //rank
    RecyclerView RankRecyclerView;
    Vertical_Ranking_Adapter vertical_ranking_adapter;
    ArrayList<Vertical_Ranking_Model> vertical_ranking_models;

    String Get_UserID;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_score_main);

        Get_UserID = getIntent().getExtras().getString("UserID");

        //Score

        verticalModels = new ArrayList<>();
        ScoreRecyclerView = findViewById(R.id.recyclerview);
        ScoreRecyclerView.setHasFixedSize(true);
        ScoreRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        verticalRecyclerViewAdapter = new VerticalRecyclerViewAdapter(this,verticalModels);
        ScoreRecyclerView.setAdapter(verticalRecyclerViewAdapter);
        setData_Score();

        // Rank
        vertical_ranking_models = new ArrayList<>();
        RankRecyclerView = findViewById(R.id.recyclerview_rank);
        RankRecyclerView.setHasFixedSize(true);
        RankRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        vertical_ranking_adapter = new Vertical_Ranking_Adapter(this,vertical_ranking_models);
        RankRecyclerView.setAdapter(vertical_ranking_adapter);
        setData_Rank();

    }

    public void setData_Score(){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList Groupname = databaseHelper.SearchGroupName_normal(Get_UserID);

        for (int i=0;i<Groupname.size();i++){
            VerticalModel verticalModel = new VerticalModel();
            verticalModel.setTitle(String.valueOf(Groupname.get(i)));
            verticalModel.setAvgScore(String.valueOf(i));

            ArrayList<HorizontalModel> horizontalModels = databaseHelper.item_word_Ranking_normal
                    (Get_UserID, String.valueOf(Groupname.get(i)));

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

    public void setData_Rank(){

        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList Groupname = databaseHelper.SearchGroupName_normal(Get_UserID);

        for (int i=0;i<Groupname.size();i++){
            Vertical_Ranking_Model verticalModel = new Vertical_Ranking_Model();
            ArrayList<Ranking_Item> horizontalModels = databaseHelper.rank_ex3_normal(String.valueOf(Groupname.get(i)));

            Log.d("Horizon", horizontalModels.toString());

//            for (int j=0;j<5;j++){
//                HorizontalModel horizontalModel = new HorizontalModel();
//                horizontalModel.setWord("name " + j);
//                horizontalModel.setScore("score " + j);
//
//                horizontalModels.add(horizontalModel);
//            }
            verticalModel.setArrayList(horizontalModels);
            vertical_ranking_models.add(verticalModel);
        }
        vertical_ranking_adapter.notifyDataSetChanged();
    }

}
