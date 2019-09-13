package com.example.projectld.exercise2;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.projectld.DatabaseHelper;
import com.example.projectld.R;
import com.example.projectld.exercise2.st_ex2_adapter.Item_st_ex2;
import com.example.projectld.exercise2.st_ex2_adapter.adapter_ex2;

import java.util.ArrayList;

public class main_select_image extends AppCompatActivity {

    static main_select_image main_select_image;  //ปิด activity จากหน้าอื่น


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ex2_st_main_select_image);

        main_select_image = this;  //ปิด activity จากหน้าอื่น

        Button button = findViewById(R.id.cf_char);
        ArrayList<Item_st_ex2> set_image_char = new ArrayList<>();

        Bundle bundle = getIntent().getExtras();
        ArrayList<String> get_char = bundle.getStringArrayList("Character");
        String Groupname = bundle.getString("Groupname");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);

        for (int i=0;i<get_char.size();i++){
            set_image_char.add(databaseHelper.item_st_ex2(get_char.get(i)));
        }

        RecyclerView mRecyclerView = findViewById(R.id.selct_image_st2);
        mRecyclerView.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        RecyclerView.Adapter mAdapter = new adapter_ex2(set_image_char,this,button,Groupname);

        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public static main_select_image getInstance(){ //ปิด activity จากหน้าอื่น
        return   main_select_image;
    }
}
