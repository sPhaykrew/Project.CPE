package com.example.projectld.My_Score;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.My_Score.Ranking.Vertical_Ranking_Adapter;
import com.example.projectld.My_Score.Ranking.Vertical_Ranking_Model;
import com.example.projectld.My_Score.Score.HorizontalModel;
import com.example.projectld.My_Score.Score.VerticalModel;
import com.example.projectld.My_Score.Score.VerticalRecyclerViewAdapter;
import com.example.projectld.R;
import com.example.projectld.recyclerView_Ranking.Ranking_Item;

import java.util.ArrayList;
import java.util.Objects;

public class My_Score_main_4 extends AppCompatActivity {

    //score
    RecyclerView ScoreRecyclerView;
    VerticalRecyclerViewAdapter verticalRecyclerViewAdapter;
    ArrayList<VerticalModel> verticalModels;

    //rank
    RecyclerView RankRecyclerView;
    Vertical_Ranking_Adapter vertical_ranking_adapter;
    ArrayList<Vertical_Ranking_Model> vertical_ranking_models;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.myscore_main);

        Toolbar toolbar = findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);

        TextView Title = toolbar.findViewById(R.id.title);
        Title.setText("คะแนนของฉัน");
        Title.setTextSize(20);

        ImageView back = toolbar.findViewById(R.id.back);
        ImageView show_menu = toolbar.findViewById(R.id.show_menu);
        show_menu.setVisibility(View.GONE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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

        TextView textHide = findViewById(R.id.noEX);

        if (verticalModels.size() != 0){
            textHide.setVisibility(View.INVISIBLE);
        }

    }

    public void setData_Score(){

        SharedPreferences user = getSharedPreferences("User", Context.MODE_PRIVATE);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList Groupname = databaseHelper.SearchGroupName_normal(user.getString("UserID",null));

        if (Groupname != null) {

            for (int i = 0; i < Groupname.size(); i++) {
                VerticalModel verticalModel = new VerticalModel();
                verticalModel.setTitle(String.valueOf(Groupname.get(i)));
                verticalModel.setAvgScore(String.valueOf(i));

                ArrayList<HorizontalModel> horizontalModels = databaseHelper.item_word_Ranking_normal
                        (user.getString("UserID", null), String.valueOf(Groupname.get(i)));

                verticalModel.setArrayList(horizontalModels);
                verticalModels.add(verticalModel);
            }
            verticalRecyclerViewAdapter.notifyDataSetChanged();
        }
    }

    public void setData_Rank(){

        SharedPreferences user = getSharedPreferences("User", Context.MODE_PRIVATE);
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        ArrayList Groupname = databaseHelper.SearchGroupName_normal(user.getString("UserID",null));

        if (Groupname != null) {

            for (int i = 0; i < Groupname.size(); i++) {
                Vertical_Ranking_Model verticalModel = new Vertical_Ranking_Model();
                ArrayList<Ranking_Item> horizontalModels = databaseHelper.rank_ex3_normal(String.valueOf(Groupname.get(i)));

                verticalModel.setArrayList(horizontalModels);
                vertical_ranking_models.add(verticalModel);
            }
            vertical_ranking_adapter.notifyDataSetChanged();
        }
    }

}
